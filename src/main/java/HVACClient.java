//import com.chuntao.service.*;
//import com.google.protobuf.Timestamp;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//
//import java.time.Instant;
//
//public class HVACClient {
//    private static HVACResponse hvacResponse;
//
//    public static void main(String[] args) {
//        // Call the main method of SensorClient to get sensor response data
//        //SensorClient.main(args);
//
//        // Use sensor response data
//        SensorResponse sensorResponse = SensorClient.getSensorResponse();
//        int pollutionLevel = sensorResponse.getPollutionLevel();
//        boolean status = pollutionLevel > 2;
//
//        // Create the ManagedChannel for HVAC command
//        ManagedChannel HVACCommandChannel = ManagedChannelBuilder.forAddress("localhost", 9092)
//                .usePlaintext()
//                .build();
//        HVACGrpc.HVACBlockingStub hvacBlockingStub = HVACGrpc.newBlockingStub(HVACCommandChannel);
//
//        // Set action based on sensor pollution level
//        HVACCommand hvacCommand = HVACCommand.newBuilder()
//                .setAction(HVACCommand.Action.valueOf(sensorResponse.getPollutionLevel()))
//                .build();
//        System.out.println("HVAC action: " + hvacCommand.getAction());
//
//        // Create the ManagedChannel for HVAC response
//        ManagedChannel HVACResponseChannel = ManagedChannelBuilder.forAddress("localhost", 9093)
//                .usePlaintext()
//                .build();
//        HVACGrpc.HVACStub hvacStub = HVACGrpc.newStub(HVACResponseChannel);
//
//        // Set location and status based on sensor response
//        HVACResponse hvacResponse = HVACResponse.newBuilder()
//                .setLocation(sensorResponse.getLocation())
//                .setStatus(status)
//                .setTimestamp(timestampNow())
//                .build();
//
//        // Print HVAC response
//        System.out.println("Response from HVAC:" +
//                "\n1. Location: " + hvacResponse.getLocation() +
//                "\n2. HVAC status: " + hvacResponse.getStatus() +
//                "\n3. Time: " + hvacResponse.getTimestamp());
//
//        // Shutdown channels
//        HVACCommandChannel.shutdown();
//        HVACResponseChannel.shutdown();
//    }
//
//    private static Timestamp timestampNow() {
//        Instant now = Instant.now();
//        return Timestamp.newBuilder()
//                .setSeconds(now.getEpochSecond())
//                .setNanos(now.getNano())
//                .build();
//    }
//
//    // Getter method for hvacResponse
//    public static HVACResponse getHvacResponse() {
//        return hvacResponse;
//    }
//}
