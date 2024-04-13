import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.TimeUnit;

public class HVACClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9091;

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        HVACGrpc.HVACStub hvacStub = HVACGrpc.newStub(channel);

        // Create a response observer for the server streaming
        StreamObserver<HVACResponse> hvacResponseObserver = new StreamObserver<HVACResponse>() {
            @Override
            public void onNext(HVACResponse hvacResponse) {
                System.out.println("HVAC server message: " +
                        "\n1. Status: " + hvacResponse.getAction() +
                        "\n2. Pollution level" + hvacResponse.getPollutionLevel() +
                        "\n3. Time: " + hvacResponse.getTimestamp());
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

        // Create a request observer for the client streaming
        StreamObserver<HVACRequest> hvacRequestObserver = hvacStub.hVACSwitch(hvacResponseObserver);
        try {
            for (int i = 0; i < 5; i++) {
                String request = "Request " + i;
                HVACRequest hvacRequest = HVACRequest.newBuilder()
                        .setRequest(request)
                        .build();
                System.out.println("Message to server: " + request);
                hvacRequestObserver.onNext(hvacRequest);
            }
        }catch (Exception e) {
            System.err.println("Error while sending messages: " + e.getMessage());
        }
        hvacRequestObserver.onCompleted();

        // Shutdown the channel gracefully
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while shutting down the channel: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
