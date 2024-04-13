import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HVACClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9091;

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

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        HVACGrpc.HVACStub hvacStub = HVACGrpc.newStub(channel);

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
            HVACCommand.Action action = determineHVACAction(1);
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

        // Shutdown the channel gracefully
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while shutting down the channel: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
