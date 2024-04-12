import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.time.Instant;
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

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 9091;

        HVACClient hvacClient = new HVACClient(host, port);

        Scanner keyboard = new Scanner(System.in);
        int sensorId;

        while (true) {
            System.out.println("Enter the sensor id to check HVAC status (or 'q' to quit):");
            if (keyboard.hasNextInt()) {
                sensorId = keyboard.nextInt();
                if (isValidSensorId(sensorId)) {
                    hvacClient.HVACControl(sensorId);
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

    private static boolean isValidSensorId(int id) {
        return id > 0 && id < 4;
    }

    private static int calculatePollutionLevel(int sensorId) {
        switch (sensorId) {
            case 1:
                return 3;
            case 2:
                return 1;
            default:
                return 3;
        }
    }

    private static HVACCommand.Action determineHVACAction(int pollutionLevel) {
        return pollutionLevel < 2 ? HVACCommand.Action.STOP : HVACCommand.Action.START;
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
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
            }
        });
        analyseResponseObserver.onNext(AnalyseResponse.newBuilder().setPollutionLevel(sensorId).build());
        analyseResponseObserver.onCompleted();
    }
}
