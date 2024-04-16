import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SensorServer {
    // Sensor service implement
    private Server server;

    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new SensorImpl())
                .build()
                .start();
        System.out.println("Sensor server started, listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Sensor server");
            try {
                SensorServer.this.stop();
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
            Random random = new Random();
            float pm25 = random.nextFloat() * 50;
            float temp = random.nextFloat() * (40 - (-20)) + (-20); // set temperature between -20 and 40
            float VOC = random.nextFloat() * 3;
            float humidity = random.nextFloat() * 70;
            float CO = random.nextFloat() * 100;
            SensorResponse.Builder response = SensorResponse.newBuilder();

            switch (sensor_id) {
                case 1:
                    response.setLocation("Bedroom").setPM25(pm25).setTemperature(temp).setVOC(VOC).setHumidity(humidity).setCO(CO);
                    break;
                case 2:
                    response.setLocation("Living room").setPM25(pm25).setTemperature(temp).setVOC(VOC).setHumidity(humidity).setCO(CO);
                    break;
                case 3:
                    response.setLocation("Karaoke room").setPM25(pm25).setTemperature(temp).setVOC(VOC).setHumidity(humidity).setCO(CO);
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
                int pollution_level;

                @Override
                public void onNext(SensorResponse sensorResponse) {
                    System.out.println("Get analyse response from: " + sensorResponse.getLocation());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in analyse data: " + throwable.getMessage());
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
                    if (sumPM25 > 25) {
                        pollutionItem++;
                        analyse.append("\n· PM2.5 is over 25 μg/m3.");
                    }
                    if (sumTemp < 22 || sumTemp > 30) {
                        pollutionItem++;
                        analyse.append("\n· Temperature is exceed 22-30 °C.");
                    }
                    if (sumVOC < 0 || sumVOC > 0.5) {
                        pollutionItem++;
                        analyse.append("\n· VOC is exceed 0-0.5 mg/m3.");
                    }
                    if (sumHumidity < 30 || sumHumidity > 50) {
                        pollutionItem++;
                        analyse.append("\n· Humidity is exceed 30-50 %.");
                    }
                    if (sumCO < 0 || sumCO > 50) {
                        pollutionItem++;
                        analyse.append("\n· CO is exceed 50 ppm.");
                    }

                    if (pollutionItem < 2) {
                        pollution_level = pollutionItem;
                        message.append("Low pollution");
                    } else if (pollutionItem == 2) {
                        pollution_level = pollutionItem;
                        message.append("Moderate pollution");
                    } else {
                        pollution_level = pollutionItem;
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
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SensorServer sensorServer = new SensorServer();
        sensorServer.start(9090);
        sensorServer.blockUntilShutdown();
    }
}