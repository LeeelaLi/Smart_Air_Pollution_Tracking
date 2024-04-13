import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SmartAirPollutionServer {
    // Sensor service implement
    private Server server;

    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new SensorImpl())
                .build()
                .start();
        System.out.println("Sensor server started, listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            try {
                SmartAirPollutionServer.this.stop();
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
    private static class SensorImpl extends SmartAirPollutionGrpc.SmartAirPollutionImplBase {
        private SensorResponse sensorData;

        @Override
        public void getSensorData(SensorRequest request, StreamObserver<SensorResponse> responseObserver) {
            int sensor_id = request.getSensorId();
            SensorResponse.Builder response = SensorResponse.newBuilder();
            switch (sensor_id) {
                case 1:
                    response.setLocation("Home").setPM25(19).setTemperature(29).setVOC(10.3F).setHumidity(36).setCO(18);
                    break;
                case 2:
                    response.setLocation("Garden").setPM25(10).setTemperature(19).setVOC(0.1F).setHumidity(45).setCO(14);
                    break;
                case 3:
                    response.setLocation("Car").setPM25(13).setTemperature(20).setVOC(0.6F).setHumidity(27).setCO(11);
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
            return new StreamObserver<SensorResponse>() {
                int pollution_level;

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
                        analyse.append("\n· PM2.5 is over 12.");
                    }
                    if (sumTemp < 18 || sumTemp > 22) {
                        pollutionItem++;
                        analyse.append("\n· Temperature is exceed 18-22.");
                    }
                    if (sumVOC < 0 || sumVOC > 0.5) {
                        pollutionItem++;
                        analyse.append("\n· VOC is exceed 0-0.5.");
                    }
                    if (sumHumidity < 30 || sumHumidity > 50) {
                        pollutionItem++;
                        analyse.append("\n· Humidity is exceed 30-50.");
                    }
                    if (sumCO < 0 || sumCO > 15) {
                        pollutionItem++;
                        analyse.append("\n· CO is exceed 0-15.");
                    }

                    if (pollutionItem < 2) {
                        pollution_level = 1;
                        message.append("Low pollution");
                    } else if (pollutionItem == 2) {
                        pollution_level = 2;
                        message.append("Moderate pollution");
                    } else {
                        pollution_level = 3;
                        message.append("High pollution");
                    }
                    // Create AnalyseResponse
                    AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                            .setLocation(sensorData.getLocation())
                            .setAnalyse(analyse.toString())
                            .setPollutionLevel(pollution_level)
                            .setMessage(message.toString())
                            .setTimestamp(timestampNow())
                            .build();

                    // Send response to client
                    responseObserver.onNext(analyseResponse);
                    responseObserver.onCompleted();
                }
            };
        }

        public StreamObserver<AnalyseResponse> hvacControl(StreamObserver<HVACCommand> responseObserver) {
            return new StreamObserver<AnalyseResponse>() {

                @Override
                public void onNext(AnalyseResponse analyseResponse) {
                    int pollutionLevel = analyseResponse.getPollutionLevel();
                    System.out.println("Received pollution level: " + pollutionLevel);
                    String action = (pollutionLevel > 2) ? "START" : "STOP";
                    HVACCommand hvacCommand = HVACCommand.newBuilder()
                            .setAction(HVACCommand.Action.valueOf(action))
                            .build();
                    responseObserver.onNext(hvacCommand);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC control: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }

        public StreamObserver<HVACCommand> hvacSwitch(StreamObserver<HVACResponse> responseObserver) {
            return new StreamObserver<HVACCommand>() {
                @Override
                public void onNext(HVACCommand hvacCommand) {
                    HVACResponse.Builder hvacResponse = HVACResponse.newBuilder();
                    boolean status = hvacCommand.getAction() == HVACCommand.Action.START;
                    hvacResponse.setStatus(status).setTimestamp(timestampNow());
                    responseObserver.onNext(hvacResponse.build());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC switch: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }

        public void sensorNotifications(AnalyseResponse analyseResponse, StreamObserver<SensorMessage> sensorObserver) {
            Runnable streamingTask = () -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        int pollutionLevel = analyseResponse.getPollutionLevel();
                        String location = analyseResponse.getLocation();
                        String air_quality;
                        String message;
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
                        SensorMessage sensorMessage = SensorMessage.newBuilder()
                                .setLocation(location)
                                .setAirQuality(air_quality)
                                .setMessage(message)
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
                        boolean status = hvacResponse.getStatus();
                        String message;
                        if (status) {
                            message = "HVAC is on.";
                        } else {
                            message = "HVAC is off.";
                        }
                        HVACMessage hvacMessage = HVACMessage.newBuilder()
                                .setStatus(status)
                                .setMessage(message)
                                .build();

                        hvacObserver.onNext(hvacMessage);
                        Thread.sleep(5000); // Stream every 5 seconds
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
        SmartAirPollutionServer smartAirPollutionServer = new SmartAirPollutionServer();
        smartAirPollutionServer.start(9090);
        smartAirPollutionServer.blockUntilShutdown();
    }
}