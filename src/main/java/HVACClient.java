import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HVACClient {

    public static boolean statusQuote;
    private final HVACGrpc.HVACStub hvacStub;
    private final ManagedChannel hvacChannel;

    public HVACClient(String host, int port) {
        this.hvacChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.hvacStub = HVACGrpc.newStub(hvacChannel);
    }

    public void shutdown() throws InterruptedException {
        hvacChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {

        String host = "localhost";
        int port = 9091;

        HVACClient hvacClient = new HVACClient(host, port);

        Scanner keyboard = new Scanner(System.in);
        String location;
        int pollution_level;

        while(true) {
            System.out.println("Enter the sensor id to check HVAC status (or 'q' to quit):");
            if(keyboard.hasNextInt()) {
                int id = keyboard.nextInt();
                if (id > 0 && id < 4) {
                    switch (id) {
                        case 1:
                            location = "Home";
                            pollution_level = 3;
                            break;
                        case 2:
                            location = "Garden";
                            pollution_level = 1;
                            break;
                        default:
                            location = "Car";
                            pollution_level = 3;
                            break;
                    }
                    // Use sensor response data
                    boolean status = pollution_level < 2;
                    HVACCommand.Action action;
                    if (status) {
                        action = HVACCommand.Action.STOP;
                    }else {
                        action = HVACCommand.Action.START;
                    }

                    // Set action based on sensor pollution level
                    HVACCommand hvacCommand = HVACCommand.newBuilder()
                            .setAction(action)
                            .build();
                    System.out.println("Pollution level: " + pollution_level +
                            "\nHVAC is: " + hvacCommand.getAction());
                } else {
                    System.out.println("Invalid sensor ID. Sensor ID should be between 1 and 3.");
                }
            } else if (keyboard.hasNext()) {
                String input = keyboard.next();
                if (input.equalsIgnoreCase("q")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid sensor ID or 'q' to quit.");
                }
            }
        }


        // Ask users if they want to turn on/off the HVAC
//        System.out.println("Do you want to turn on(1)/turn off(2)?");
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

        // Shutdown channels
        hvacClient.shutdown();
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    public void HVACControl(int sensor_id) {

        StreamObserver<HVACCommand> hvacCommandObserver = new StreamObserver<HVACCommand>() {
            @Override
            public void onNext(HVACCommand hvacCommand) {
                String location;
                int pollution_level;
                if(sensor_id == 1){
                    location = "Home";
                    pollution_level = 3;
                } else if (sensor_id == 2) {
                    location = "Garden";
                    pollution_level = 1;
                }else {
                    location = "Car";
                    pollution_level = 3;
                }

                // Use sensor response data
                boolean status = pollution_level < 2;
                HVACCommand.Action action;
                if (status) {
                    action = HVACCommand.Action.STOP;
                }else {
                    action = HVACCommand.Action.START;
                }

                // Set action based on sensor pollution level
                hvacCommand = HVACCommand.newBuilder()
                        .setAction(action)
                        .build();
                System.out.println("Pollution level: " + pollution_level +
                        "\nHVAC is: " + hvacCommand.getAction());
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in HVAC control: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
            }
        };
        StreamObserver<AnalyseResponse> analyseResponseObserver = hvacStub.hVACControl(hvacCommandObserver);

        analyseResponseObserver.onNext(AnalyseResponse.newBuilder().setPollutionLevel(sensor_id).build());
        analyseResponseObserver.onCompleted();
    }
}