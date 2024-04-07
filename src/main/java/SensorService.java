import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import com.chuntao.service.*;

import java.time.Instant;

public class SensorService {
    public static void main(String[] args) throws Exception {
        Server sensorServer = ServerBuilder.forPort(9090)
                .addService(new SensorImpl())
                .build();

        sensorServer.start();
        System.out.println("Sensor Service started on port 9090");
        sensorServer.awaitTermination();
    }



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
}
