import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class HVACClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9091;
    private final ManagedChannel managedChannel;
    public HVACClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
    }

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        HVACClient hvacClient = new HVACClient(channel);
        hvacClient.HVACSwitch();

        // Shutdown the channel gracefully
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while shutting down the channel: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
    private void HVACSwitch() {

        final HVACGrpc.HVACStub hvacStub = HVACGrpc.newStub(managedChannel);
        CountDownLatch latch = new CountDownLatch(1);

        // Create a response observer for the server streaming
        StreamObserver<HVACResponse> hvacResponseObserver = new StreamObserver<HVACResponse>() {
            @Override
            public void onNext(HVACResponse hvacResponse) {
                Date responseTime = new Date(hvacResponse.getTimestamp().getSeconds() * 1000);
                System.out.println("\nHVAC response: " +
                        "\n1. HVAC is now: " + hvacResponse.getAction() +
                        "\n2. HVAC status: " + hvacResponse.getMessage() +
                        "\n3. Time: " + responseTime);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error from server: " + throwable.getMessage());
                latch.countDown(); // Release the latch on error
            }

            @Override
            public void onCompleted() {
                System.out.println("Server stream completed");
                latch.countDown();
            }
        };

        // Create a request observer for the client streaming
        StreamObserver<HVACRequest> hvacRequestObserver = hvacStub.hvacSwitch(hvacResponseObserver);
        try {
            for (int i = 0; i < 5; i++) {
                String request = "" + i;
                HVACRequest hvacRequest = HVACRequest.newBuilder()
                        .setPollutionLevel(i)
                        .setHumidity(34.44F)
                        .setTemperature(23.33F)
                        .build();
                System.out.println("HVAC request for air pollution level: " + request);
                hvacRequestObserver.onNext(hvacRequest);
            }
        }catch (Exception e) {
            System.err.println("Error while sending messages: " + e.getMessage());
        }
        hvacRequestObserver.onCompleted();
        try {
            latch.await();
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for response: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
