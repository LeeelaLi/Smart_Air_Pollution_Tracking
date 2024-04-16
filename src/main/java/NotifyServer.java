import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class NotifyServer extends NotificationGrpc.NotificationImplBase {
    private static int pollutionLevel;

    public void sensorNotifications(AnalyseResponse analyseResponse, StreamObserver<SensorMessage> sensorObserver) {
        Runnable streamingTask = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    pollutionLevel = 1;
                    String location = analyseResponse.getLocation();
                    String air_quality;
                    String advice;
                    if (pollutionLevel == 1) {
                        air_quality = "Great";
                        advice = "The air is healthy, HVAC is off";
                    } else if (pollutionLevel == 2) {
                        air_quality = "Moderate";
                        advice = "The air is fine. HVAC is off now, you could turn on the HVAC.";
                    } else {
                        air_quality = "Bad";
                        advice = "The air is harmed, HVAC is automatically on.";
                    }
                    SensorMessage sensorMessage = SensorMessage.newBuilder()
                            .setLocation(location)
                            .setAirQuality(air_quality)
                            .setAdvice(advice)
                            .setTimestamp(timestampNow())
                            .build();
                    sensorObserver.onNext(sensorMessage);
                    Thread.sleep(5000); // Stream every 5 seconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                sensorObserver.onCompleted();
            }
        };

        Thread streamingThread = new Thread(streamingTask);
        streamingThread.start();
    }

    public void hvacNotifications(HVACResponse hvacResponse, StreamObserver<HVACMessage> hvacObserver) {
        Runnable streamingTask = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                   HVACResponse.Action action = hvacResponse.getAction();
                   String message;
                    boolean status;
                    if (pollutionLevel > 2) {
                        message = "HVAC is on.";
                        status = true;
                    } else {
                        message = "HVAC is off.";
                        status = false;
                    }
                    HVACMessage hvacMessage = HVACMessage.newBuilder()
                            .setStatus(status)
                            .setMessage(message)
                            .setTimestamp(timestampNow())
                            .build();

                    hvacObserver.onNext(hvacMessage);
                    Thread.sleep(5000); // Stream every 5 seconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                hvacObserver.onCompleted();
            }
        };

        Thread streamingThread = new Thread(streamingTask);
        streamingThread.start();
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        NotifyServer server = new NotifyServer();
        Server grpcServer = ServerBuilder.forPort(9092)
                .addService(server)
                .build();

        grpcServer.start();
        System.out.println("Notification server started, listening on port 9092");

        // Graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down notification server");
            try {
                grpcServer.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));

        grpcServer.awaitTermination();
    }
}