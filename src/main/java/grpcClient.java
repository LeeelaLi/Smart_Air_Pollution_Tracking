import com.chuntao.service.AirPollutionTrackGrpc;
import com.chuntao.service.SensorRequest;
import com.chuntao.service.SensorResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class grpcClient {
    public static void main(String[] args) {
        //create supported all-data channel
        ManagedChannel allDataChannel = ManagedChannelBuilder.forAddress("localhost", 8020)
                .usePlaintext()
                .build();

        AirPollutionTrackGrpc.AirPollutionTrackBlockingStub allDataStub = AirPollutionTrackGrpc.newBlockingStub(allDataChannel);

        SensorRequest allDataRequest = SensorRequest.newBuilder()
                .setSensorId(1)
                .build();
        SensorResponse allDataResponse = allDataStub.getAllSensorData(allDataRequest);
        System.out.println("The air quality of " + allDataResponse.getLocation() + " as follows:" +
                "\n1. PM2.5: " + allDataResponse.getPM() + "(μg/m3)" +
                "\n2. Temperature: " + allDataResponse.getTemperature() + "(Celsius)" +
                "\n3. VOC: " + allDataResponse.getVOC() + "(mg/m3)" +
                "\n4. Humidity: " + allDataResponse.getHumidity() + "(%)" +
                "\n5. CO: " + allDataResponse.getCO() + "(ppm)" +
                "\n6. Pollution Level: " + allDataResponse.getPollutionLevel() +
                "\n7. Time: " + allDataResponse.getTimestamp());

        //create supported single channel
        ManagedChannel singleChannel = ManagedChannelBuilder.forAddress("localhost", 8020)
                .usePlaintext()
                .build();

        AirPollutionTrackGrpc.AirPollutionTrackBlockingStub singleStub = AirPollutionTrackGrpc.newBlockingStub(singleChannel);

        SensorRequest singleRequest = SensorRequest.newBuilder()
                .setSensorId(1)
                .build();
        SensorResponse singleResponse = singleStub.getAllSensorData(singleRequest);
        System.out.println();

    }
}
