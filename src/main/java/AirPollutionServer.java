import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AirPollutionServer {
    // Sensor service implement
    private Server server;
    private static int pollution_level;
    private static HVACResponse.Action action;

    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new SensorImpl())
                .addService(new HVACImpl())
                .addService(new NotifyImpl())
                .build()
                .start();
        System.out.println("Sensor server started, listening on port " + port);

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
            Random random = new Random();
            float pm25 = random.nextFloat() * 50;
            float temp = random.nextFloat() * 38;
            switch (sensor_id) {
                case 1:
                    response.setLocation("Home").setPM25(10).setTemperature(19).setVOC(10.3F).setHumidity(36).setCO(18);
                    break;
                case 2:
                    response.setLocation("Garden").setPM25(34).setTemperature(50).setVOC(0.1F).setHumidity(65).setCO(14);
                    break;
                case 3:
                    response.setLocation("Car").setPM25(pm25).setTemperature(temp).setVOC(0.6F).setHumidity(27).setCO(11);
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
                        message.append("Low pollution");
                    } else if (pollutionItem == 2) {
                        message.append("Moderate pollution");
                    } else {
                        message.append("High pollution");
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

        public StreamObserver<HVACRequest> hvacSwitch(StreamObserver<HVACResponse> responseObserver) {
            StreamObserver<HVACRequest> requestObserver = new StreamObserver<HVACRequest>() {
                @Override
                public void onNext(HVACRequest hvacRequest) {
                    System.out.println("HVAC request: " + pollution_level);

                    if (pollution_level > 2) {
                        action = HVACResponse.Action.START;
                    } else {
                        action = HVACResponse.Action.STOP;
                    }

                    HVACResponse hvacResponse = HVACResponse.newBuilder()
                            .setAction(action)
                            .setPollutionLevel(pollution_level)
                            .setTimestamp(timestampNow())
                            .build();
                    responseObserver.onNext(hvacResponse);
                }

                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC switch: " + throwable.getMessage());
                }

                public void onCompleted() {
                    System.out.println("HVAC client stream completed");
                    HVACResponse hvacResponse = HVACResponse.newBuilder()
                            .setAction(action)
                            .setPollutionLevel(pollution_level)
                            .setTimestamp(timestampNow())
                            .build();
                    action = hvacResponse.getAction();

                    responseObserver.onNext(hvacResponse);
                    responseObserver.onCompleted();
                }
            };
            return requestObserver;
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
                            message = "The air is healthy, HVAC is off";
                        } else if (pollution_level == 2) {
                            air_quality = "Moderate";
                            message = "The air is fine. HVAC is off now, you could turn on the HVAC.";
                        } else {
                            air_quality = "Bad";
                            message = "The air is harmed, HVAC is automatically on.";
                        }
                        SensorMessage sensorMessage = SensorMessage.newBuilder()
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
                        String message;
                        boolean status;
                        if (action.equals(HVACResponse.Action.START)) {
                            message = "HVAC is on.";
                            status = true;
                        } else {
                            message = "HVAC is off.";
                            status = false;
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
        AirPollutionServer airPollutionServer = new AirPollutionServer();
        airPollutionServer.start(9090);
        airPollutionServer.blockUntilShutdown();
    }
}