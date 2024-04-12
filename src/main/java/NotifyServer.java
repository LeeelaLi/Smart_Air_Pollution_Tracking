import com.chuntao.service.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class NotifyServer extends NotificationGrpc.NotificationImplBase {

    public void sensorNotifications(AnalyseResponse analyseResponse, StreamObserver<NotificationMessage> notificationObserver) {
        int pollutionLevel = analyseResponse.getPollutionLevel();
        Runnable streamingTask = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String message = "This is a message from the Sensor server in : " + analyseResponse.getLocation() +
                            "\n1. Pollution level: " + pollutionLevel +
                            "\n2. Current time: " + LocalDateTime.now() +
                            "\n3. Advice: " + analyseResponse.getAnalyse();
                    NotificationMessage sensorMessage = NotificationMessage.newBuilder()
                            .setMessage(message)
                            .build();
                    notificationObserver.onNext(sensorMessage);
                    Thread.sleep(5000); // Stream every 5 seconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                notificationObserver.onCompleted();
            }
        };

        Thread streamingThread = new Thread(streamingTask);
        streamingThread.start();
    }

    public StreamObserver<AnalyseResponse> sensorNotifications(StreamObserver<NotificationMessage> responseObserver) {
        // Implementation for receiving sensor notifications (Server-side streaming RPC)
        return new StreamObserver<AnalyseResponse>() {
            int pollutionLevel = 0;
            String location = "";
            String air_quality = "";
            String message = "";

            public void onNext(AnalyseResponse analyseResponse) {
                // Process incoming analysis responses and send notifications
                location = analyseResponse.getLocation();
                pollutionLevel = analyseResponse.getPollutionLevel();
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle errors
            }

            @Override
            public void onCompleted() {
                // Finalize processing
                if (pollutionLevel == 1) {
                    air_quality = "Air quality: Great";
                    message = "The air is healthy, HVAC is off";
                } else if (pollutionLevel == 2) {
                    air_quality = "Air quality: Moderate";
                    message = "The air is fine. HVAC is off now, you could turn on the HVAC.";
                } else {
                    air_quality = "Air quality: Bad";
                    message = "The air is harmed, HVAC is automatically on.";
                }

                NotificationMessage notificationMessage = NotificationMessage.newBuilder()
                        .setLocation(location)
                        .setAirQuality(air_quality)
                        .setMessage(message)
                        .build();
                responseObserver.onNext(notificationMessage);
                responseObserver.onCompleted();
            }
        };
    }

    public StreamObserver<HVACResponse> hvacNotifications(StreamObserver<NotificationMessage> responseObserver) {
        // Implementation for receiving HVAC notifications (Server-side streaming RPC)
        return new StreamObserver<HVACResponse>() {
            boolean status;
            String message = "";
            @Override
            public void onNext(HVACResponse hvacResponse) {
                // Process incoming HVAC responses and send notifications
                status = hvacResponse.getStatus();
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle errors
            }

            @Override
            public void onCompleted() {
                // Finalize processing
                if (status) {
                    message = "HVAC is on.";
                } else {
                    message = "HVAC is off.";
                }
                NotificationMessage notificationMessage = NotificationMessage.newBuilder()
                        .setMessage(message)
                        .build();

                responseObserver.onNext(notificationMessage);
                responseObserver.onCompleted();
            }
        };
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        NotifyServer server = new NotifyServer();
        Server grpcServer = ServerBuilder.forPort(9092)
                .addService(server)
                .build();

        grpcServer.start();
        System.out.println("Server started, listening on port 9092");

        // Graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            try {
                grpcServer.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));

        grpcServer.awaitTermination();
    }
}