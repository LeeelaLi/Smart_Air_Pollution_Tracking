import com.chuntao.service.AnalyseResponse;
import com.chuntao.service.HVACResponse;
import com.chuntao.service.NotificationGrpc;
import com.chuntao.service.NotificationMessage;
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

    public void sensorNotifications() {
        StreamObserver<NotificationMessage> notificationObserver = new StreamObserver<NotificationMessage>() {
            @Override
            public void onNext(NotificationMessage notificationMessage) {
                System.out.println("Sensor server message: " + notificationMessage.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in server streaming: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server streaming completed");
            }
        };
        notificationStub.sensorNotifications(AnalyseResponse.newBuilder().setPollutionLevel(1).build(), notificationObserver);
    }

    public void hvacNotifications() {
        StreamObserver<NotificationMessage> notificationObserver = new StreamObserver<NotificationMessage>() {
            @Override
            public void onNext(NotificationMessage notificationMessage) {
                System.out.println("HVAC server message: " + notificationMessage.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in HVAC server streaming: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server streaming completed");
            }
        };
        notificationStub.hVACNotifications(HVACResponse.newBuilder().setStatus(true).build(), notificationObserver);
    }
    public static void main(String[] args) {
        NotifyClient client = new NotifyClient("localhost", 9092);
        client.sensorNotifications();
        client.hvacNotifications();

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

    public void shutdown() {
        try {
            notifyChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error while shutting down client: " + e.getMessage());
        }
    }

//        // Use sensor response data
//        int pollutionLevel = SensorClient.pollution_level;
//        String location;
//        String air_quality;
//        String message;
//        int id = SensorClient.id;
//        if(id == 1){
//            location = "home";
//        } else if (id == 2) {
//            location = "garden";
//        }else {
//            location = "car";
//        }
//
//        if (pollutionLevel < 2) {
//            air_quality = "Great";
//            message = "The air is healthy, HVAC is off";
//        } else if (pollutionLevel == 2) {
//            air_quality = "Moderate";
//            message = "The air is fine. HVAC is off now, you could turn on the HVAC.";
//        } else {
//            air_quality = "Bad";
//            message = "The air is harmed, HVAC is automatically on.";
//        }
//
//        // create sensor Notification channel
//        ManagedChannel sensorNotifyChannel = ManagedChannelBuilder.forAddress("localhost", 9092)
//                .usePlaintext()
//                .build();
//        NotificationGrpc.NotificationBlockingStub sensorNotifyBlockingStub = NotificationGrpc.newBlockingStub(sensorNotifyChannel);
//
//        NotificationMessage sensorMessage = NotificationMessage.newBuilder()
//                .setLocation(location)
//                .setAirQuality(air_quality)
//                .setMessage(message)
//                .build();
//        Date updatedTime = new Date(sensorMessage.getTimestamp().getSeconds() * 1000);
//
//        System.out.println("Air quality message from sensor. " +
//                "\n1. Detected from: " + sensorMessage.getLocation() +
//                "\n2. Air quality: " + sensorMessage.getAirQuality() +
//                "\n3. Advice: " + sensorMessage.getMessage() +
//                "\n4. Time: " + updatedTime);
//
//        // create HVAC Notification
//        NotificationMessage hvacMessage = NotificationMessage.newBuilder()
//                        .setLocation(location)
//                        .setMessage(message)
//                        .setTimestamp(timestampNow())
//                        .build();
//        System.out.println("\nAir quality message from HVAC. " +
//                "\n1. Detected from: " + hvacMessage.getLocation() +
//                "\n2. Air quality: " + sensorMessage.getAirQuality() +
//                "\n3. Advice: " + sensorMessage.getMessage() +
//                "\n4. Time: " + updatedTime);
//        sensorNotifyChannel.shutdown();
//    }
}
