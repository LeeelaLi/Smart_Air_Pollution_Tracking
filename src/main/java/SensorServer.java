import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            System.out.println("Shutting down gRPC server");
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
            SensorResponse.Builder response = SensorResponse.newBuilder();
            switch (sensor_id) {
                case 1:
                    response.setLocation("Home").setPM25(19).setTemperature(29).setVOC(10.3F).setHumidity(36).setCO(18);
                    break;
                case 2:
                    response.setLocation("Garden").setPM25(10).setTemperature(19).setVOC(0.1F).setHumidity(45).setCO(14);
                    break;
                case 3:
                    response.setLocation("Car").setPM25(13).setTemperature(24).setVOC(0.6F).setHumidity(27).setCO(11);
                    break;
                default:
                    response.setLocation("Unknown");
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

                    // Determine pollution level based on sensor data
                    StringBuffer analyse = new StringBuffer().append("");
                    String message = "";
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
                    
                    if (pollutionItem < 1) {
                        message += "Low pollution";
                    } else if (pollutionItem < 3 && pollutionItem > 1) {
                        message += "Moderare pollution";
                    } else {
                        message += "High pollution";
                    }

                    // Create AnalyseResponse
                    AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                            .setLocation(sensorData.getLocation())
                            .setAnalyse(analyse.toString())
                            .setPollutionLevel(pollutionItem)
                            .setMessage(message)
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
