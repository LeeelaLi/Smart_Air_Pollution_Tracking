import com.chuntao.service.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
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
                float sumTemp = 0; // Air temperature in Celsius
                float sumVOC = 0; // VOC level in mg/m3
                float sumHumidity = 0; // Humidity in Percentage
                float sumCO = 0; // CO in ppm
                int pollutionItem = 0;

                @Override
                public void onNext(SensorResponse sensorResponse) {
                    // Calculate pollution level based on 5 factors of qir quality
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
                    // Determine pollution level based on average PM2.5
                    String analyse = "";
                    if (sumPM25 > 12) {
                        pollutionItem++;
                        analyse += "\nPM2.5 is over 12.";
                    }
                    if (sumTemp < 18 || sumTemp > 22) {
                        pollutionItem++;
                        analyse += "\nTemperature is exceed 18-22.";
                    }
                    if (sumVOC < 0 || sumVOC > 0.5) {
                        pollutionItem++;
                        analyse += "\nVOC is exceed 0-0.5.";
                    }
                    if (sumHumidity < 30 || sumHumidity > 50) {
                        pollutionItem++;
                        analyse += "\nHumidity is exceed 30-50.";
                    }
                    if (sumCO < 0 || sumCO > 15) {
                        pollutionItem++;
                        analyse += "\nPCO is exceed 0-15.";
                    }

                    // Create AnalyseResponse
                    AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                            .setAnalyse(analyse)
                            .setPollutionLevel(pollutionItem)
                            .build();

                    // Send response to client
                    responseObserver.onNext(analyseResponse);
                    responseObserver.onCompleted();
                }
            };
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        SensorServer sensorServer = new SensorServer();
        sensorServer.start(9090);
        sensorServer.blockUntilShutdown();
    }
}
