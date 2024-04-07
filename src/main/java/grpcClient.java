import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.Timestamp;

import com.chuntao.service.*;

import java.time.Instant;

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
        ManagedChannel analyseChannel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();
        SensorGrpc.SensorStub sensorStub = SensorGrpc.newStub(analyseChannel);

        // Pass the pollution level from SensorResponse to AnalyseResponse
        AnalyseResponse analyseResponse = AnalyseResponse.newBuilder()
                .setAnalyse("" + sensorResponse.getPollutionLevel())
                .setLocation(sensorResponse.getLocation())
                .setTimestamp(timestampNow())
                .build();

        // Print AnalyseResponse
        System.out.println("The analysis of air quality is:" +
                "\n1. Pollution level: " + analyseResponse.getAnalyse() +
                "\n2. Location: " + analyseResponse.getLocation() +
                "\n3. Time: " + analyseResponse.getTimestamp());

        // Shutdown channels
        sensorChannel.shutdown();
        analyseChannel.shutdown();
    }
    // Helper method to get the current timestamp
    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }
}
