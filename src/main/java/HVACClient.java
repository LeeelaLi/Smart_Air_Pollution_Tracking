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

    private final ManagedChannel hvacChannel;
    private final HVACGrpc.HVACStub hvacStub;

    String location;
    HVACCommand.Action action;

    public HVACClient(String host, int port) {
        this.hvacChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.hvacStub = HVACGrpc.newStub(hvacChannel);
    }

    public void HVACControl(int sensor_id) {
        StreamObserver<HVACCommand> hvacCommandObserver = new StreamObserver<HVACCommand>() {
            @Override
            public void onNext(HVACCommand hvacCommand) {
                int pollution_level;
                if(sensor_id == 1){
                    location = "Home";
                    pollution_level = 3;
                } else if (sensor_id == 2) {
                    location = "Garden";
                    pollution_level = 1;
                } else {
                    location = "Car";
                    pollution_level = 3;
                }
                System.out.println("pollution level: " + pollution_level);

                if(pollution_level < 2) {
                    action = HVACCommand.Action.START;
                } else {
                    action = HVACCommand.Action.STOP;
                }
                System.out.println("HVAC is: " + action);

                // Modify the existing hvacCommand object
                HVACCommand hvacCommand1 = HVACCommand.newBuilder().setAction(action).build();
                System.out.println("HVAC is now: " + hvacCommand1.getAction());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
//                responseObserver.onCompleted();
                HVACSwitch(action);
            }
        };
        StreamObserver<AnalyseResponse> analyseResponseObserver = hvacStub.hVACControl(hvacCommandObserver);

        // Send the sensor ID to the server to start analysing data for that sensor
        analyseResponseObserver.onNext(AnalyseResponse.newBuilder().setPollutionLevel(sensor_id).build());
        analyseResponseObserver.onCompleted();
    }

    public void HVACSwitch(HVACCommand.Action switchAction) {
        StreamObserver<HVACResponse> hvacResponseObserver = new StreamObserver<HVACResponse>() {
            @Override
            public void onNext(HVACResponse hvacResponse) {
                // Ask users if they want to turn on/off the HVAC
                System.out.println("Do you want to turn on(1)/turn off(2)?");
                Scanner keyboard = new Scanner(System.in);
                int turnOn = keyboard.nextInt();
                if (turnOn == 1) {
                    action = HVACCommand.Action.START;
                } else {
                    action = HVACCommand.Action.STOP;
                }
                boolean status;
                String statusMessage;
                if (action == HVACCommand.Action.START) {
                    status = true;
                    statusMessage = "ON";
                } else {
                    status = false;
                    statusMessage = "OFF";
                }
                // Set location and status based on sensor response
                hvacResponse = HVACResponse.newBuilder()
                        .setLocation(location)
                        .setStatus(status)
                        .setTimestamp(timestampNow())
                        .build();
                Date updatedTime = new Date(hvacResponse.getTimestamp().getSeconds() * 1000);

                // Print HVAC response
                System.out.println("\n1. HVAC status of " + hvacResponse.getLocation() +
                        ": " + statusMessage +
                        "\n2. Time: " + updatedTime);
                System.out.println("HVAC Response: " + hvacResponse.getStatus() +
                        hvacResponse.getLocation());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };
        StreamObserver<HVACCommand> hvacCommandObserver = hvacStub.hVACSwitch(hvacResponseObserver);

        hvacCommandObserver.onNext(HVACCommand.newBuilder().setAction(action).build());
        hvacCommandObserver.onCompleted();
    }

    public void shutdown() throws InterruptedException {
        hvacChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 9091;
        System.out.println("Here are the hvac status:");
        HVACClient hvacClient = new HVACClient(host, port);

        // Use sensor response data
        Scanner keyboard = new Scanner(System.in);
        int id = keyboard.nextInt();

        // Set action based on sensor pollution level
        hvacClient.HVACControl(id);

        // Shutdown channels
        hvacClient.shutdown();
    }
}
