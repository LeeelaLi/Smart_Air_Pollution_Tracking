import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import com.chuntao.service.*;

import java.io.IOException;
import java.time.Instant;

public class grpcService {
    public static void main(String[] args) throws Exception {
        Server sensorServer = ServerBuilder.forPort(9090)
                .addService(new SensorImpl())
                .build();

        sensorServer.start();
        System.out.println("Sensor Service started on port 9090");
        sensorServer.awaitTermination();

        Server HVACServer = ServerBuilder.forPort(9091)
                .addService(new HVACImpl())
                .build();

        HVACServer.start();
        System.out.println("HVAC Service started on port 9091");
        HVACServer.awaitTermination();

        Server NotifyServer = ServerBuilder.forPort(9092)
                .addService(new NotificationServiceImpl())
                .build();

        NotifyServer.start();
        System.out.println("Notification Service started on port 9092");
        NotifyServer.awaitTermination();
    }

//    private final int port;
//    private final Server server;
//
//    public grpcService(int port) {
//        this.port = port;
//        this.server = ServerBuilder.forPort(port)
//                .addService(new SensorImpl())
//                .addService(new HVACImpl())
//                .build();
//    }
//
//    public void start() throws IOException {
//        server.start();
//        System.out.println("Server started, listening on " + port);
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            System.out.println("Shutting down gRPC server");
//            grpcService.this.stop();
//            System.out.println("Server shut down");
//        }));
//    }
//
//    private void stop() {
//        if (server != null) {
//            server.shutdown();
//        }
//    }
//
//    public void blockUntilShutdown() throws InterruptedException {
//        if (server != null) {
//            server.awaitTermination();
//        }
//    }
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        grpcService server = new grpcService(9090);
//        server.start();
//        server.blockUntilShutdown();
//    }

    // Sensor service implement
    static class SensorImpl extends SensorGrpc.SensorImplBase {
        @Override
        public void getSensorData(SensorRequest request, StreamObserver<SensorResponse> responseObserver) {
            System.out.println("Select the sensor id you want to review:" +
                    "\n1. Sensor 1 - Home" +
                    "\n2. Sensor 2 - Garden" +
                    "\n3. Sensor 3 - Car");
            int sensor_id = request.getSensorId();

            SensorResponse.Builder response = SensorResponse.newBuilder();
            switch (sensor_id){
                case 1:
                    response.setLocation("Home").setPM25(9).setTemperature(20).setVOC(0.3F).setHumidity(36).setCO(8);
                case 2:
                    response.setLocation("Garden").setPM25(17).setTemperature(19).setVOC(0.1F).setHumidity(45).setCO(14);
                default:
                    response.setLocation("Car").setPM25(13).setTemperature(24).setVOC(0.6F).setHumidity(27).setCO(11);
            }

            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<SensorResponse> analyseSensorData(StreamObserver<AnalyseResponse> responseObserver) {
            return new StreamObserver<SensorResponse>() {
                float sumPM25 = 0;
                float sumTemp= 0; // Air temperature in Celsius
                float sumVOC = 0; // VOC level in mg/m3
                float sumHumidity = 0; // Humidity in Percentage
                float sumCO = 0; // CO in ppm
                int pollutionItem = 0;
                int pollutionLevel;
                @Override
                public void onNext(SensorResponse sensorResponse) {
                    // Calculate pollution level based on PM2.5
                    sumPM25 += sensorResponse.getPM25();
                    sumTemp += sensorResponse.getTemperature();
                    sumVOC += sensorResponse.getVOC();
                    sumHumidity += sensorResponse.getHumidity();
                    sumCO += sensorResponse.getCO();
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle error
                }
                @Override
                public void onCompleted() {
                    // Calculate average PM2.5 over all samples
                    //int avgPM25 = sumPM25 / numSamples;

                    // Determine pollution level based on average PM2.5
                    String analyse = "";
                    if (sumPM25 > 12) {
                        pollutionItem++;
                        analyse += "\nPM2.5 is over 12.";
                    } else if (sumTemp < 18 || sumTemp > 22) {
                        pollutionItem++;
                        analyse += "\nTemperature is exceed 18-22.";
                    } else if (sumVOC < 0 || sumVOC > 0.5) {
                        pollutionItem++;
                        analyse += "\nVOC is exceed 0-0.5.";
                    } else if (sumHumidity < 30 || sumHumidity > 50) {
                        pollutionItem++;
                        analyse += "\nHumidity is exceed 30-50.";
                    } else if (sumCO < 0 || sumCO > 15) {
                        pollutionItem++;
                        analyse += "\nPCO is exceed 0-15.";
                    }

                    if (pollutionItem <= 0) {
                        pollutionLevel = 1;
                        analyse += "\nPollution level: Low pollution";
                    } else if (pollutionItem < 2) {
                        pollutionLevel = 2;
                        analyse += "\nPollution level: Moderate pollution";
                    } else {
                        pollutionLevel = 3;
                        analyse += "\nPollution level: High pollution";
                    }
                    // Create AnalyseResponse
                    AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                            .setAnalyse(analyse)
                            .build();

                    // Send response to client
                    responseObserver.onNext(analyseResponse);
                    responseObserver.onCompleted();
                }
            };
        }
    }

    // HVAC service implement
    static class HVACImpl extends HVACGrpc.HVACImplBase {

        @Override
        public StreamObserver<SensorResponse> hvacAction(StreamObserver<HVACCommand> responseObserver) {
            return null;
        }

        @Override
        public StreamObserver<SensorResponse> hvacControl(StreamObserver<HVACCommand> responseObserver) {
            return new StreamObserver<SensorResponse>() {
                int pollutionLevel;
                String action;
                String HVACAction;
                @Override
                public void onNext(SensorResponse sensorResponse) {
                    pollutionLevel = sensorResponse.getPollutionLevel();
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    if(pollutionLevel > 2) {
                        action = "START";
                        HVACAction = "Turn on the HVAC";
                    } else {
                        action = "STOP";
                        HVACAction = "Turn off the HVAC";
                    }
                    HVACCommand hvacCommand = HVACCommand.newBuilder()
                            .setAction(HVACCommand.Action.valueOf(action))
                            .build();
                    responseObserver.onNext(hvacCommand);
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<HVACCommand> hvacSwitch(StreamObserver<HVACResponse> responseObserver) {
            return new StreamObserver<HVACCommand>() {
                @Override
                public void onNext(HVACCommand hvacCommand) {
                    // Logic to switch HVAC based on the received command
                    HVACResponse.Builder response = HVACResponse.newBuilder();
                    switch (hvacCommand.getAction()) {
                        case START:
                            response.setLocation("Location").setStatus(true).setTimestamp(timestampNow());
                            break;
                        case STOP:
                            response.setLocation("Location").setStatus(false).setTimestamp(timestampNow());
                            break;
                        default:
                            // Handle unsupported action
                            break;
                    }
                    responseObserver.onNext(response.build());
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle error
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }

        private Timestamp timestampNow() {
            Instant now = Instant.now();
            return Timestamp.newBuilder()
                    .setSeconds(now.getEpochSecond())
                    .setNanos(now.getNano())
                    .build();
        }
    }

    // Notification service implement
    static class NotificationServiceImpl extends NotificationGrpc.NotificationImplBase {
        @Override
        public StreamObserver<SensorResponse> sensorNotifications(StreamObserver<NotificationMessage> responseObserver) {
            // Implementation for receiving sensor notifications (Server-side streaming RPC)
            return new StreamObserver<SensorResponse>() {
                int pollutionLevel = 0;
                String location = "";
                String air_quality = "";
                String message = "";
                @Override
                public void onNext(SensorResponse sensorResponse) {
                    // Process incoming analysis responses and send notifications
                    location = sensorResponse.getLocation();
                    pollutionLevel = sensorResponse.getPollutionLevel();
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle errors
                }

                @Override
                public void onCompleted() {
                    // Finalize processing
                    if (pollutionLevel == 1) {
                        air_quality = "Air quality: Great";
                        message = "The air is healthy, HVAC is off";
                    } else if (pollutionLevel == 2) {
                        air_quality = "Air quality: Moderate";
                        message = "The air is fine. HVAC is off now, you could turn on the HVAC.";
                    } else {
                        air_quality = "Air quality: Bad";
                        message = "The air is harmed, HVAC is automatically on.";
                    }

                    NotificationMessage notificationMessage = NotificationMessage.newBuilder()
                            .setLocation(location)
                            .setAirQuality(air_quality)
                            .setMessage(message)
                            .setTimestamp(timestampNow())
                            .build();
                    responseObserver.onNext(notificationMessage);
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<HVACResponse> ventNotifications(StreamObserver<NotificationMessage> responseObserver) {
            // Implementation for receiving HVAC notifications (Server-side streaming RPC)
            return new StreamObserver<HVACResponse>() {
                boolean status;
                Timestamp timestamp;
                String message = "";
                @Override
                public void onNext(HVACResponse hvacResponse) {
                    // Process incoming HVAC responses and send notifications
                    status = hvacResponse.getStatus();
                    timestamp = hvacResponse.getTimestamp();
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle errors
                }

                @Override
                public void onCompleted() {
                    // Finalize processing
                    if (status) {
                        message = "HVAC is on.";
                    } else {
                        message = "HVAC is off.";
                    }
                NotificationMessage notificationMessage = NotificationMessage.newBuilder()
                        .setTimestamp(timestamp)
                        .setMessage(message)
                        .build();

                responseObserver.onNext(notificationMessage);
                responseObserver.onCompleted();
                }
            };
        }
        private Timestamp timestampNow() {
            Instant now = Instant.now();
            return Timestamp.newBuilder()
                    .setSeconds(now.getEpochSecond())
                    .setNanos(now.getNano())
                    .build();
        }
    }
}
