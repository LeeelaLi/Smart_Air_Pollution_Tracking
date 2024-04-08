//import com.chuntao.service.*;
//import com.google.protobuf.Timestamp;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//
//import java.time.Instant;
//import java.util.Date;
//import java.util.Scanner;
//
//import com.chuntao.service.PollutionLevelUpdate;
//import io.grpc.stub.StreamObserver;
//
//public class HVACClient {
//
//    public static boolean statusQuote;
//    public static void main(String[] args) {
//
//        // Inside main method of HVACClient class
//        StreamObserver<PollutionLevelUpdate> pollutionLevelObserver = new StreamObserver<PollutionLevelUpdate>() {
//            @Override
//            public void onNext(PollutionLevelUpdate update) {
//                SensorClient.pollution_level = update.getPollutionLevel();
//                // You can add further logic here if needed
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                // Handle error
//            }
//
//            @Override
//            public void onCompleted() {
//                // Handle completion
//            }
//        };
//
//        // Use sensor response data
//        boolean status = SensorClient.pollution_level > 2;
//        HVACCommand.Action action;
//        if (SensorClient.pollution_level < 2) {
//            action = HVACCommand.Action.valueOf(1);
//        }else {
//            action = HVACCommand.Action.valueOf(0);
//        }
//        String location;
//        int id = SensorClient.id;
//        if(id == 1){
//            location = "Home";
//        } else if (id == 2) {
//            location = "Garden";
//        }else {
//            location = "Car";
//        }
//        System.out.println("pollution level: " + SensorClient.pollution_level);
//        StreamObserver<PollutionLevelRequest> pollutionLevelRequestObserver = sensorStub.sendPollutionLevelUpdates(pollutionLevelObserver);
//
//        // Create the ManagedChannel for HVAC command
//        ManagedChannel HVACCommandChannel = ManagedChannelBuilder.forAddress("localhost", 9091)
//                .usePlaintext()
//                .build();
//        HVACGrpc.HVACBlockingStub hvacBlockingStub = HVACGrpc.newBlockingStub(HVACCommandChannel);
//
//        // Set action based on sensor pollution level
//        HVACCommand hvacCommand = HVACCommand.newBuilder()
//                .setAction(action)
//                .build();
//        System.out.println("HVAC is: " + hvacCommand.getAction());
//        // Ask users if they want to turn on/off the HVAC
//        System.out.println("Do you want to turn on(1)/turn off(2)?");
//        Scanner keyboard = new Scanner(System.in);
//        int turnOn = keyboard.nextInt();
//        if (turnOn == 1) {
//            action = HVACCommand.Action.valueOf(0);
//        } else {
//            action = HVACCommand.Action.valueOf(1);
//        }
//        hvacCommand = HVACCommand.newBuilder()
//                .setAction(action)
//                .build();
//        System.out.println("HVAC is now: " + hvacCommand.getAction());
//
//        HVACCommand.Action action1 = hvacCommand.getAction();
//        String statusMessage;
//        if (action1 == HVACCommand.Action.valueOf(0)) {
//            status = true;
//            statusMessage = "ON";
//        } else {
//            status = false;
//            statusMessage = "OFF";
//        }
//        // Set location and status based on sensor response
//        HVACResponse hvacResponse = HVACResponse.newBuilder()
//                .setLocation(location)
//                .setStatus(status)
//                .setTimestamp(timestampNow())
//                .build();
//        statusQuote = hvacResponse.getStatus();
//        Date updatedTime = new Date(hvacResponse.getTimestamp().getSeconds() * 1000);
//
//        // Print HVAC response
//        System.out.println("\n1. HVAC status of " + hvacResponse.getLocation() +
//                ": " + statusMessage +
//                "\n2. Time: " + updatedTime);
//
//        // Shutdown channels
//        HVACCommandChannel.shutdown();
//    }
//
//    private static Timestamp timestampNow() {
//        Instant now = Instant.now();
//        return Timestamp.newBuilder()
//                .setSeconds(now.getEpochSecond())
//                .setNanos(now.getNano())
//                .build();
//    }
//}
