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

public class AirPollutionServer {
    // Sensor service implement
    private Server server;
    private static int sensor_id;

    private void start() throws IOException {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new SensorImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);

        // Register server to Consul
        registerToConsul();

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            AirPollutionServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

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

    private static class SensorImpl extends SensorGrpc.SensorImplBase {

        public void getSensorData(SensorRequest request, StreamObserver<SensorResponse> sensorResponseObserver) {
            System.out.println("Sensor id: " + request.getSensorId());
            sensor_id = request.getSensorId();
            SensorResponse.Builder response = SensorResponse.newBuilder();

            if(sensor_id == 1) {
                response.setLocation("Bedroom").build();
            } else if (sensor_id == 2) {
                response.setLocation("Living room").build();
            } else if (sensor_id == 3) {
                response.setLocation("Karaoke room").build();
            } else {
                response.setLocation("Unknown").build();
            }

            sensorResponseObserver.onNext(response.build());
            sensorResponseObserver.onCompleted();
        }

        public StreamObserver<AnalyseRequest> analyseSensorData(StreamObserver<AnalyseResponse> analyseResponseObserver) {
            return new StreamObserver<AnalyseRequest>() {
                @Override
                public void onNext(AnalyseRequest analyseRequest) {

                    // Perform analysis based on the stored sensor data
                    float sumPM25 = analyseRequest.getPM25();
                    float sumTemp = analyseRequest.getTemperature();
                    float sumVOC = analyseRequest.getVOC();
                    float sumHumidity = analyseRequest.getHumidity();
                    float sumCO = analyseRequest.getCO();
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
                        message.append("Low pollution");
                    } else if (pollutionItem == 2) {
                        message.append("Moderate pollution, recommend to turn on the HVAC.");
                    } else {
                        message.append("High pollution, HVAC is automatically on.");
                    }

                    // Create AnalyseResponse
                    AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                            .setAnalyse(analyse.toString())
                            .setPollutionLevel(pollutionItem)
                            .setMessage(message.toString())
                            .setTimestamp(timestampNow())
                            .build();

                    analyseResponseObserver.onNext(analyseResponse);
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onCompleted() {
                    analyseResponseObserver.onCompleted();
                }
            };
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
        final AirPollutionServer server = new AirPollutionServer();
        server.start();
        server.blockUntilShutdown();
    }
}
