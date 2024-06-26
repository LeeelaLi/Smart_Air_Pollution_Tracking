import com.chuntao.service.*;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AirPollutionServer {
    // Sensor service implement
    private Server server;
    private static int pollution_level; // store pollution level to send through the whole system
    private static HvacCommand.Action action; // store HVAC action
    private static String status = null; // store HVAC status
    private static String ifHvacSwitch = null; // store if HVAC has been changed
    private static StringBuilder sensorAlert = null;
    private static float sumPM25;
    private static float sumTemp;
    private static float sumVOC;
    private static float sumHumidity;
    private static float sumCO;

    // Register to consul
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
                .addService(new SensorImpl()) // add sensor service
                .addService(new HVACImpl()) // add HVAC service
                .addService(new NotifyImpl()) // add notification service
                .build()
                .start();
        System.out.println("Air-Pollution-Tracking server started, listening on port " + port);

        // Register server to Consul
        registerToConsul();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Air-Pollution-Tracking server");
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

    // Sensor service
    private static class SensorImpl extends SensorGrpc.SensorImplBase {
        private SensorResponse sensorData; // declare this variable to store sensor data

        @Override
        // get sensor data
        public void getSensorData(SensorRequest request, StreamObserver<SensorResponse> responseObserver) {
            int sensor_id = request.getSensorId();
            SensorResponse.Builder response = SensorResponse.newBuilder();
            try {
                if (request.getSensorId() <= 0 || request.getSensorId() >= 4) {
                    // invalid sensor ID, return an error to the client
                    throw new IllegalArgumentException("Invalid sensor ID");
                } else {
                    switch (sensor_id) {
                        case 1: // set up 'home' sensor data
                            response.setLocation("Bedroom").setPm25(13).setTemperature(19).setVoc(0.2F).setHumidity(33).setCo(3);
                            break;
                        case 2: // set up 'garden' sensor data
                            response.setLocation("Living room").setPm25(16).setTemperature(29).setVoc(0.3F).setHumidity(44).setCo(5);
                            break;
                        case 3: // set up 'car' sensor data
                            response.setLocation("Karaoke room").setPm25(29).setTemperature(35).setVoc(1).setHumidity(55).setCo(9);
                            break;
                        default:
                            response.setLocation("Unknown");
                            break;
                    }
                }

                // store the sensor data for analysis
                sensorData = response.build();

                // send response to client
                responseObserver.onNext(sensorData);
                responseObserver.onCompleted();
            } catch (IllegalArgumentException e) {
                // handle invalid argument error
                responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Invalid sensor ID").asException());
            } catch (Exception e) {
                // handle other exceptions
                responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asException());
            }
        }

        @Override
        // analyse based on sensor data
        public StreamObserver<SensorResponse> analyseSensorData(StreamObserver<AnalyseResponse> responseObserver) {
            return new StreamObserver<>() {

                @Override
                public void onNext(SensorResponse sensorResponse) {
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in sensor analysis: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {

                    // perform analysis based on the stored sensor data
                    sumPM25 = sensorData.getPm25();
                    sumTemp = sensorData.getTemperature();
                    sumVOC = sensorData.getVoc();
                    sumHumidity = sensorData.getHumidity();
                    sumCO = sensorData.getCo();
                    int pollutionItem = 0; // record polluted items number

                    StringBuilder analyse = new StringBuilder(); // analyse data based on polluted items number
                    StringBuilder message = new StringBuilder(); // give suggests based on polluted items number
                    StringBuilder tempAlert = new StringBuilder();

                    if (sumPM25 > 12) { // pm2.5 > 12 is referred to pollution
                        pollutionItem++;
                        analyse.append("\n· PM2.5 is over 12μg/m3.");
                    }
                    if (sumTemp < 18 || sumTemp > 28) { // 18 > temperature < 28 is referred to pollution
                        pollutionItem++;
                        analyse.append("\n· Temperature is exceed 18-28°C.");
                        if (sumTemp < 18) {
                            tempAlert.append("\n· Low temperature warning!");
                        } else {
                            tempAlert.append("\n· High temperature warning!");
                        }
                    }
                    if (sumVOC < 0 || sumVOC > 0.5) { // 0.5 > VOC < 0 is referred to pollution
                        pollutionItem++;
                        analyse.append("\n· VOC is exceed 0-0.5mg/m3.");
                    }
                    if (sumHumidity < 30 || sumHumidity > 50) { // 50 > humidity < 30 is referred to pollution
                        pollutionItem++;
                        analyse.append("\n· Humidity is exceed 30-50%.");
                        if (sumHumidity > 50) {
                            tempAlert.append("\n· Mould risk warning!");
                        }
                    }
                    if (sumCO < 0 || sumCO > 15) { // 15 > CO < 0 is referred to pollution
                        pollutionItem++;
                        analyse.append("\n· CO is exceed 0-15ppm.");
                    }

                    // set up pollution level based on pollution items
                    if (pollutionItem < 2) { // if pollution items are less than 2
                        message.append("Low pollution.");
                    } else if (pollutionItem == 2) { // if pollution items are 2
                        message.append("Moderate pollution, you could turn on the HVAC manually.");
                    } else { // if pollution items are greater than 2
                        message.append("High pollution, automatically turn on the HVAC.");
                        status = "ON"; // automatically turn on HVAC
                    }

                    // create AnalyseResponse
                    AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                            .setLocation(sensorData.getLocation())
                            .setAnalyse(analyse.toString())
                            .setPollutionLevel(pollutionItem)
                            .setMessage(message.toString())
                            .setTimestamp(timestampNow())
                            .build();

                    pollution_level = pollutionItem; // pass pollution items value to pollution level
                    sensorAlert = tempAlert;

                    // send response to client
                    responseObserver.onNext(analyseResponse);
                    responseObserver.onCompleted();
                }
            };
        }
    }

    // HVAC service
    private static class HVACImpl extends HVACGrpc.HVACImplBase {

        // get HVAC status
        public StreamObserver<HvacRequest> hvacControl(StreamObserver<HvacCommand> hvacCommandObserver) {
            return new StreamObserver<>() {
                @Override
                public void onNext(HvacRequest hvacRequest) {
                    try {
                        if (ifHvacSwitch == null) { // check if HVAC status hasn't been changed
                            // if pollution level is greater than 2, automatically turn on HVAC
                            action = pollution_level > 2 ? HvacCommand.Action.START : HvacCommand.Action.STOP;
                        } else if (status.equalsIgnoreCase("ON")) { // if HVAC status has been changed to 'ON'
                            action = HvacCommand.Action.START; // change action to 'START'
                        } else if (status.equalsIgnoreCase("OFF")) { // if HVAC status has been changed to 'OFF'
                            action = HvacCommand.Action.STOP; // change action to 'STOP'
                        }

                        HvacCommand hvacCommand = HvacCommand.newBuilder()
                                .setAction(action)
                                .setUpdatedTime(timestampNow())
                                .build();

                        action = hvacCommand.getAction(); // store latest HVAC action
                        hvacCommandObserver.onNext(hvacCommand);
                    } catch (Exception e) {
                        // handle other exceptions
                        hvacCommandObserver.onError(Status.INTERNAL.withDescription("Internal server error").asException());
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC control: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("HVAC control completed");
                    hvacCommandObserver.onCompleted();
                }
            };
        }

        // switch HVAC status
        public StreamObserver<HvacCommand> hvacSwitch(StreamObserver<HvacResponse> responseObserver) {
            return new StreamObserver<>() {

                @Override
                public void onNext(HvacCommand hvacCommand) {
                    try {
                        System.out.println("HVAC command: " + hvacCommand.getAction());
                        if (hvacCommand.getAction().equals(HvacCommand.Action.START)) { // check if the latest HVAC action is 'START'
                            status = "ON"; // turn on the HVAC
                        } else {
                            status = "OFF"; // turn off the HVAC
                        }

                        HvacResponse hvacResponse = HvacResponse.newBuilder()
                                .setStatus(status)
                                .setPollutionLevel(pollution_level)
                                .setUpdatedTime(timestampNow())
                                .build();

                        status = hvacResponse.getStatus(); // store the latest HVAC status
                        action = hvacCommand.getAction(); // store the latest HVAC action
                        responseObserver.onNext(hvacResponse);
                    } catch (Exception e) {
                        // handle other exceptions
                        responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asException());
                    }
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

    // Notification service
    private static class NotifyImpl extends NotificationGrpc.NotificationImplBase {
        // get sensor notification
        public void sensorNotifications(AnalyseResponse analyseResponse, StreamObserver<SensorMessage> sensorObserver) {
            Runnable streamingTask = () -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String air_quality;
                        String message;
                        // generate air quality and advice based on pollution level
                        if (pollution_level < 2) {
                            air_quality = "Great";
                            message = "The air is healthy.";
                        } else if (pollution_level == 2) {
                            air_quality = "Moderate";
                            message = "The air is fine.";
                        } else {
                            air_quality = "Bad";
                            message = "The air is harmed, HVAC is now ON.";
                            status = "ON"; // if pollution level is high, turn on the HVAC automatically
                            action = HvacCommand.Action.START;
                        }

                        SensorMessage sensorMessage = SensorMessage.newBuilder()
                                .setLocation(analyseResponse.getLocation())
                                .setAirQuality(air_quality)
                                .setAlert(sensorAlert.toString())
                                .setAdvice(message)
                                .setTimestamp(timestampNow())
                                .build();
                        sensorObserver.onNext(sensorMessage);
                        Thread.sleep(5000); // stream every 5 seconds
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    // handle other exceptions
                    sensorObserver.onError(Status.INTERNAL.withDescription("Internal server error").asException());
                } finally {
                    sensorObserver.onCompleted();
                }
            };

            Thread streamingThread = new Thread(streamingTask);
            streamingThread.start();
        }

        // get HVAC notification
        public void hvacNotifications(HvacResponse hvacResponse, StreamObserver<HvacMessage> hvacObserver) {
            Runnable streamingTask = () -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String message;

                        // update HVAC notification message based on the latest HVAC status
                        if (action.equals(HvacCommand.Action.START)) {
                            if (status.equals("ON")) { // if the latest HVAC status is ON
                                message = "HVAC is on.";
                                HvacMessage hvacMessage = HvacMessage.newBuilder()
                                        .setStatus(true)
                                        .setMessage(message)
                                        .setUpdatedTime(timestampNow())
                                        .build();
                                hvacObserver.onNext(hvacMessage);
                                Thread.sleep(5000); // stream every 5 seconds
                            }
                        } else {
                            message = "HVAC is off."; // if the latest HVAC status is OFF

                            HvacMessage hvacMessage = HvacMessage.newBuilder()
                                    .setStatus(false)
                                    .setMessage(message)
                                    .setUpdatedTime(timestampNow())
                                    .build();

                            hvacObserver.onNext(hvacMessage);
                            Thread.sleep(5000); // stream every 5 seconds
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    // handle other exceptions
                    hvacObserver.onError(Status.INTERNAL.withDescription("Internal server error").asException());
                } finally {
                    hvacObserver.onCompleted();
                }
            };

            Thread streamingThread = new Thread(streamingTask);
            streamingThread.start();
        }
    }

    // Get current time
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