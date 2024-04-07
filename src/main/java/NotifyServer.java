import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;

public class NotifyServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server NotifyServer = ServerBuilder.forPort(9092)
                .addService(new NotificationServiceImpl())
                .build();

        NotifyServer.start();
        System.out.println("Notification Service started on port 9092");
        NotifyServer.awaitTermination();
    }
    // Notification service implement
    static class NotificationServiceImpl extends NotificationGrpc.NotificationImplBase {
        @Override
        public StreamObserver<SensorResponse> sensorNotifications(StreamObserver<NotificationMessage> responseObserver) {
            // Implementation for receiving sensor notifications (Server-side streaming RPC)
            return new StreamObserver<SensorResponse>() {
                int pollutionLevel = 0;
                String location = "";
                String air_quality = "";
                String message = "";
                @Override
                public void onNext(SensorResponse sensorResponse) {
                    // Process incoming analysis responses and send notifications
                    location = sensorResponse.getLocation();
                    pollutionLevel = sensorResponse.getPollutionLevel();
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
                            .setTimestamp(timestampNow())
                            .build();
                    responseObserver.onNext(notificationMessage);
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<HVACResponse> ventNotifications(StreamObserver<NotificationMessage> responseObserver) {
            // Implementation for receiving HVAC notifications (Server-side streaming RPC)
            return new StreamObserver<HVACResponse>() {
                boolean status;
                Timestamp timestamp;
                String message = "";
                @Override
                public void onNext(HVACResponse hvacResponse) {
                    // Process incoming HVAC responses and send notifications
                    status = hvacResponse.getStatus();
                    timestamp = hvacResponse.getTimestamp();
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
                            .setTimestamp(timestamp)
                            .setMessage(message)
                            .build();

                    responseObserver.onNext(notificationMessage);
                    responseObserver.onCompleted();
                }
            };
        }
        private Timestamp timestampNow() {
            Instant now = Instant.now();
            return Timestamp.newBuilder()
                    .setSeconds(now.getEpochSecond())
                    .setNanos(now.getNano())
                    .build();
        }
    }
}
