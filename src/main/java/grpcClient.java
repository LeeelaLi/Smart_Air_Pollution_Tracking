import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import com.chuntao.service.*;

public class grpcClient {
    public static void main(String[] args) {
        // create sensor channel
        ManagedChannel sensorChannel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        SensorGrpc.SensorBlockingStub sensorBlockingStub = SensorGrpc.newBlockingStub(sensorChannel);

        SensorRequest sensorRequest = SensorRequest.newBuilder()
                .setSensorId(1)
                .build();
        SensorResponse sensorResponse = sensorBlockingStub.getSensorData(sensorRequest);
        System.out.println("The air quality of " + sensorResponse.getLocation() + ":" +
                        "\n1. PM25: " + sensorResponse.getPM25() +
                        "\n2. Temperature: " + sensorResponse.getTemperature() +
                        "\n3. VOC: " + sensorResponse.getVOC() +
                        "\n4. Humidity: " + sensorResponse.getHumidity() +
                        "\n5. CO: " + sensorResponse.getCO() +
                        "\nAir pollution level: " + sensorResponse.getPollutionLevel());

        // create analyse channel
//        ManagedChannel analyseChannel = ManagedChannelBuilder.forAddress("localhost", 9091)
//                .usePlaintext()
//                .build();
//        SensorGrpc.SensorBlockingStub sensorBlockingStub1 = SensorGrpc.newBlockingStub(analyseChannel);
//
//        SensorResponse sensorResponse1 = SensorResponse.newBuilder()
//                .setPollutionLevel(2)
//                .build();
//        AnalyseResponse analyseResponse = sensorBlockingStub1.getSensorData(sensorResponse1);
//        System.out.println();
    }
}
