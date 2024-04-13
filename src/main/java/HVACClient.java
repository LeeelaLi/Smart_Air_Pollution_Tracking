import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HVACClient {

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

    private static boolean isValidSensorId(int id) {
        return id > 0 && id < 4;
    }

    private static int calculatePollutionLevel(int sensorId) {
        switch (sensorId) {
            case 1:
            case 3:
                return 3;
            default:
                return 1;
        }
    }

    private static HVACCommand.Action determineHVACAction(int pollutionLevel) {
        return pollutionLevel < 2 ? HVACCommand.Action.STOP : HVACCommand.Action.START;
    }

    public void HVACControl(int sensorId) {
        int pollutionLevel = calculatePollutionLevel(sensorId);
        HVACCommand.Action action = determineHVACAction(pollutionLevel);
        HVACCommand hvacCommand = HVACCommand.newBuilder()
                .setAction(action)
                .build();
        System.out.println("Pollution level: " + pollutionLevel +
                "\nHVAC is: " + hvacCommand.getAction());
        StreamObserver<AnalyseResponse> analyseResponseObserver = hvacStub.hVACControl(new StreamObserver<HVACCommand>() {
            @Override
            public void onNext(HVACCommand hvacCommand) {
                // Handle onNext
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in HVAC control: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                // Handle onCompleted
                HVACSwitch(sensorId);
            }
        });
        analyseResponseObserver.onNext(AnalyseResponse.newBuilder().setPollutionLevel(sensorId).build());
        analyseResponseObserver.onCompleted();
    }

    public void HVACSwitch(int sensorId) {
        int pollutionLevel = calculatePollutionLevel(sensorId);
        HVACCommand.Action action = determineHVACAction(pollutionLevel);

        StreamObserver<HVACResponse> hvacResponseObserver = new StreamObserver<HVACResponse>() {
            @Override
            public void onNext(HVACResponse hvacResponse) {
                System.out.println("HVAC server message: " +
                        "\n1. Status: " + hvacResponse.getStatus() +
                        "\n2. Time: " + hvacResponse.getTimestamp());
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error from server: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server stream completed");
            }
        };
        StreamObserver<HVACCommand> hvacCommandObserver = hvacStub.hVACSwitch(hvacResponseObserver);

        try {
            HVACCommand hvacCommand = HVACCommand.newBuilder()
                    .setAction(action)
                    .build();
            System.out.println("Message to server: " +
                    "\n1. HVAC is: " + hvacCommand.getAction());
            hvacCommandObserver.onNext(hvacCommand);
        }catch (Exception e) {
            System.err.println("Error while sending messages: " + e.getMessage());
        }
        hvacCommandObserver.onCompleted();
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 9091;

        HVACClient hvacClient = new HVACClient(host, port);

        Scanner keyboard = new Scanner(System.in);
        int sensorId;

        while (true) {
            System.out.println("Enter the sensor id to check HVAC status (or 'q' to quit):" +
                    "\n1. Sensor 1 - Home" +
                    "\n2. Sensor 2 - Garden" +
                    "\n3. Sensor 3 - Car");
            if (keyboard.hasNextInt()) {
                sensorId = keyboard.nextInt();
                if (isValidSensorId(sensorId)) {
//                    hvacClient.HVACControl(sensorId);
                    hvacClient.HVACSwitch(sensorId);
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

        // Shutdown channels
        hvacClient.shutdown();
    }
}
