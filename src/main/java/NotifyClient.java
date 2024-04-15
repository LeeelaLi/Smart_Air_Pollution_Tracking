import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NotifyClient {
    private final ManagedChannel notifyChannel;
    private final NotificationGrpc.NotificationStub notificationStub;

    public NotifyClient(String host, int port) {
        this.notifyChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.notificationStub = NotificationGrpc.newStub(notifyChannel);
    }

    public void start() {
        // Start threads for listening to sensor and HVAC notifications concurrently
        Thread sensorThread = new Thread(this::sensorNotifications);
        Thread hvacThread = new Thread(this::hvacNotifications);

        sensorThread.start();
        hvacThread.start();

        try {
            // Wait for both threads to complete
            sensorThread.join();
            hvacThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error while waiting for threads to complete: " + e.getMessage());
        }
    }

    public void shutdown()  throws InterruptedException  {
        try {
            notifyChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error while shutting down client: " + e.getMessage());
        }
    }

    public void sensorNotifications() {
        StreamObserver<SensorMessage> sensorObserver = new StreamObserver<SensorMessage>() {
            @Override
            public void onNext(SensorMessage sensorMessage) {
                System.out.println("\nSensor message: " +
                        "\n1. Location: " + sensorMessage.getLocation() +
                        "\n2. Air quality: " + sensorMessage.getAirQuality() +
                        "\n3. Advice: " + sensorMessage.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in Sensor server streaming: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sensor notification completed");
            }
        };
        notificationStub.sensorNotifications(AnalyseResponse.newBuilder().setPollutionLevel(1).build(), sensorObserver);
    }

    public void hvacNotifications() {
        StreamObserver<HVACMessage> hvacObserver = new StreamObserver<HVACMessage>() {
            @Override
            public void onNext(HVACMessage hvacMessage) {
                System.out.println("\nHVAC message: " +
                        "\n1. HVAC status: " + hvacMessage.getStatus() +
                        "\n2. Message: " + hvacMessage.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in HVAC server streaming: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC notification completed");
            }
        };
        notificationStub.hvacNotifications(HVACResponse.newBuilder().setPollutionLevel(1).build(), hvacObserver);
    }

    public static void main(String[] args) throws InterruptedException {
        NotifyClient client = new NotifyClient("localhost", 9092);
        client.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Press 'Q' to quit");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Q")) {
                client.shutdown();
                break;
            }
        }
    }

}
