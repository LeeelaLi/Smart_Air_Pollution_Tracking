import com.chuntao.service.*;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AirPollutionServer {
    // Sensor service implement
    private Server server;
    private static int pollution_level;
    private static HVACCommand.Action action;
    private static String status = null;
    private static String if_hvac_switch = null;
    private void registerToConsul() {
        System.out.println("Registering server to Consul...");

        // Load Consul configuration from consul.properties file
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/consul.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Extract Consul configuration properties
        String consulHost = props.getProperty("consul.host");
        int consulPort = Integer.parseInt(props.getProperty("consul.port"));
        String serviceName = props.getProperty("consul.service.name");
        int servicePort = Integer.parseInt(props.getProperty("consul.service.port"));
        String healthCheckInterval = props.getProperty("consul.service.healthCheckInterval");

        // Get host address
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        // Create a Consul client
        ConsulClient consulClient = new ConsulClient(consulHost, consulPort);

        // Define service details
        NewService newService = new NewService();
        newService.setName(serviceName);
        newService.setPort(servicePort);
        newService.setAddress(hostAddress); // Set host address

        // Register service with Consul
        consulClient.agentServiceRegister(newService);

        // Print registration success message
        System.out.println("Server registered to Consul successfully. Host: " + hostAddress);
    }

    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new SensorImpl())
                .addService(new HVACImpl())
                .addService(new NotifyImpl())
                .build()
                .start();
        System.out.println("Sensor server started, listening on port " + port);

        // Register server to Consul
        registerToConsul();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            try {
                AirPollutionServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static class SensorImpl extends SensorGrpc.SensorImplBase {
        private SensorResponse sensorData;

        @Override
        public void getSensorData(SensorRequest request, StreamObserver<SensorResponse> responseObserver) {
            int sensor_id = request.getSensorId();
            SensorResponse.Builder response = SensorResponse.newBuilder();
//            Random random = new Random();
//            float pm25 = random.nextFloat() * 12;
//            float temp = random.nextFloat() * 38;
//            float VOC = random.nextFloat() * 10;
//            float Humidity = random.nextFloat() * 78;
//            float CO = random.nextFloat() * 15;
            switch (sensor_id) {
                case 1:
                    response.setLocation("Home").setPM25(13).setTemperature(19).setVOC(0.2F).setHumidity(33).setCO(3);
                    break;
                case 2:
                    response.setLocation("Garden").setPM25(16).setTemperature(29).setVOC(0.3F).setHumidity(44).setCO(5);
                    break;
                case 3:
                    response.setLocation("Car").setPM25(29).setTemperature(35).setVOC(1).setHumidity(37).setCO(9);
                    break;
                default:
                    response.setLocation("Unknown");
                    break;
            }

            // Store the sensor data for analysis
            sensorData = response.build();

            // Send response to client
            responseObserver.onNext(sensorData);
            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<SensorResponse> analyseSensorData(StreamObserver<AnalyseResponse> responseObserver) {
            return new StreamObserver<>() {

                @Override
                public void onNext(SensorResponse sensorResponse) {
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle error
                }

                @Override
                public void onCompleted() {

                    // Perform analysis based on the stored sensor data
                    float sumPM25 = sensorData.getPM25();
                    float sumTemp = sensorData.getTemperature();
                    float sumVOC = sensorData.getVOC();
                    float sumHumidity = sensorData.getHumidity();
                    float sumCO = sensorData.getCO();
                    int pollutionItem = 0;

                    StringBuilder analyse = new StringBuilder();
                    StringBuilder message = new StringBuilder();
                    if (sumPM25 > 12) {
                        pollutionItem++;
                        analyse.append("\n· PM2.5 is over 12μg/m3.");
                    }
                    if (sumTemp < 18 || sumTemp > 22) {
                        pollutionItem++;
                        analyse.append("\n· Temperature is exceed 18-22°C.");
                    }
                    if (sumVOC < 0 || sumVOC > 0.5) {
                        pollutionItem++;
                        analyse.append("\n· VOC is exceed 0-0.5mg/m3.");
                    }
                    if (sumHumidity < 30 || sumHumidity > 50) {
                        pollutionItem++;
                        analyse.append("\n· Humidity is exceed 30-50%.");
                    }
                    if (sumCO < 0 || sumCO > 15) {
                        pollutionItem++;
                        analyse.append("\n· CO is exceed 0-15ppm.");
                    }

                    if (pollutionItem < 2) {
                        message.append("Low pollution.");
                    } else if (pollutionItem == 2) {
                        message.append("Moderate pollution, you could turn on the HVAC manually.");
                    } else {
                        message.append("High pollution, strongly recommend to turn on the HVAC.");
                        status = "ON";
                    }
                    // Create AnalyseResponse
                    AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                            .setLocation(sensorData.getLocation())
                            .setAnalyse(analyse.toString())
                            .setPollutionLevel(pollutionItem)
                            .setMessage(message.toString())
                            .setTimestamp(timestampNow())
                            .build();
                    pollution_level = pollutionItem;

                    // Send response to client
                    responseObserver.onNext(analyseResponse);
                    responseObserver.onCompleted();
                }
            };
        }
    }

    private static class HVACImpl extends HVACGrpc.HVACImplBase {

        public StreamObserver<HVACRequest> hvacControl(StreamObserver<HVACCommand> hvacCommandObserver) {
            return new StreamObserver<HVACRequest>() {
                @Override
                public void onNext(HVACRequest hvacRequest) {
                    if (if_hvac_switch == null) {
                        if (pollution_level > 2) {
                            action = HVACCommand.Action.START;
                        } else {
                            action = HVACCommand.Action.STOP;
                        }
                    } else if (status.equalsIgnoreCase("ON")) {
                        action = HVACCommand.Action.START;
                        status = "ON";
                    } else if (status.equalsIgnoreCase("OFF")) {
                        action = HVACCommand.Action.STOP;
                        status = "OFF";
                    }

                    HVACCommand hvacCommand = HVACCommand.newBuilder()
                            .setAction(action)
                            .build();
                    action = hvacCommand.getAction();
                    hvacCommandObserver.onNext(hvacCommand);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC switch: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    hvacCommandObserver.onCompleted();
                }
            };
        }

        public StreamObserver<HVACCommand> hvacSwitch(StreamObserver<HVACResponse> responseObserver) {
            return new StreamObserver<>() {

                @Override
                public void onNext(HVACCommand hvacCommand) {
                    System.out.println("HVAC command: " + hvacCommand.getAction());
                    if (hvacCommand.getAction().equals(HVACCommand.Action.START)) {
                        status = "ON";
                    } else {
                        status = "OFF";
                    }

                    HVACResponse hvacResponse = HVACResponse.newBuilder()
                            .setStatus(status)
                            .setPollutionLevel(pollution_level)
                            .setTimestamp(timestampNow())
                            .build();
                    status = hvacResponse.getStatus();
                    action = hvacCommand.getAction();
                    responseObserver.onNext(hvacResponse);
                }

                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC switch: " + throwable.getMessage());
                }

                public void onCompleted() {
                    System.out.println("HVAC switch completed");
                    responseObserver.onCompleted();
                }
            };
        }
    }

    private static class NotifyImpl extends NotificationGrpc.NotificationImplBase {
        public void sensorNotifications(AnalyseResponse analyseResponse, StreamObserver<SensorMessage> sensorObserver) {
            Runnable streamingTask = () -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String air_quality;
                        String message;
                        if (pollution_level < 2) {
                            air_quality = "Great";
                            message = "The air is healthy.";
                        } else if (pollution_level == 2) {
                            air_quality = "Moderate";
                            message = "The air is fine, you could turn on the HVAC manually.";
                        } else {
                            air_quality = "Bad";
                            message = "The air is harmed, HVAC is now ON.";
                            status = "ON";
                        }

                        SensorMessage sensorMessage = SensorMessage.newBuilder()
                                .setLocation(analyseResponse.getLocation())
                                .setAirQuality(air_quality)
                                .setMessage(message)
                                .setTimestamp(timestampNow())
                                .build();
                        sensorObserver.onNext(sensorMessage);
                        Thread.sleep(5000); // Stream every 5 seconds
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    sensorObserver.onCompleted();
                }
            };

            Thread streamingThread = new Thread(streamingTask);
            streamingThread.start();
        }

        public void hvacNotifications(HVACResponse hvacResponse, StreamObserver<HVACMessage> hvacObserver) {
            Runnable streamingTask = () -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        boolean turn_on;
                        String message;

                        if (action.equals(HVACCommand.Action.START)) {
                            if (status.equals("ON")) {
                                message = "HVAC is on.";
                                turn_on = true;
                                HVACMessage hvacMessage = HVACMessage.newBuilder()
                                        .setStatus(turn_on)
                                        .setMessage(message)
                                        .setTimestamp(timestampNow())
                                        .build();
                                hvacObserver.onNext(hvacMessage);
                                Thread.sleep(5000); // Stream every 5 seconds
                            }
                        } else {
                            message = "HVAC is off.";
                            turn_on = false;

                            HVACMessage hvacMessage = HVACMessage.newBuilder()
                                    .setStatus(turn_on)
                                    .setMessage(message)
                                    .setTimestamp(timestampNow())
                                    .build();

                            hvacObserver.onNext(hvacMessage);
                            Thread.sleep(5000); // Stream every 5 seconds
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    hvacObserver.onCompleted();
                }
            };

            Thread streamingThread = new Thread(streamingTask);
            streamingThread.start();
        }
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        AirPollutionServer airPollutionServer = new AirPollutionServer();
        airPollutionServer.start(9090);
        airPollutionServer.blockUntilShutdown();
    }
}