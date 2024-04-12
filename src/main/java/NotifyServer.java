import com.chuntao.service.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class NotifyServer extends NotificationGrpc.NotificationImplBase {

    public void sensorNotifications(AnalyseResponse analyseResponse, StreamObserver<SensorMessage> sensorObserver) {
        Runnable streamingTask = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    int pollutionLevel = analyseResponse.getPollutionLevel();
                    String location = analyseResponse.getLocation();
                    String air_quality;
                    String message;
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
                    SensorMessage sensorMessage = SensorMessage.newBuilder()
                            .setLocation(location)
                            .setAirQuality(air_quality)
                            .setMessage(message)
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
                   boolean status = hvacResponse.getStatus();
                   String message;
                    if (status) {
                        message = "HVAC is on.";
                    } else {
                        message = "HVAC is off.";
                    }
                    HVACMessage hvacMessage = HVACMessage.newBuilder()
                            .setStatus(status)
                            .setMessage(message)
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

//    public StreamObserver<AnalyseResponse> sensorNotifications(StreamObserver<SensorMessage> sensorObserver) {
//        // Implementation for receiving sensor notifications (Server-side streaming RPC)
//        return new StreamObserver<AnalyseResponse>() {
//            int pollutionLevel = 0;
//            String location = "";
//            String air_quality = "";
//            String message = "";
//
//            public void onNext(AnalyseResponse analyseResponse) {
//                // Process incoming analysis responses and send notifications
//                location = analyseResponse.getLocation();
//                pollutionLevel = analyseResponse.getPollutionLevel();
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                // Handle errors
//            }
//
//            @Override
//            public void onCompleted() {
//                // Finalize processing
//                if (pollutionLevel == 1) {
//                    air_quality = "Air quality: Great";
//                    message = "The air is healthy, HVAC is off";
//                } else if (pollutionLevel == 2) {
//                    air_quality = "Air quality: Moderate";
//                    message = "The air is fine. HVAC is off now, you could turn on the HVAC.";
//                } else {
//                    air_quality = "Air quality: Bad";
//                    message = "The air is harmed, HVAC is automatically on.";
//                }
//
//                SensorMessage sensorMessage = SensorMessage.newBuilder()
//                        .setLocation(location)
//                        .setAirQuality(air_quality)
//                        .setMessage(message)
//                        .build();
//                sensorObserver.onNext(sensorMessage);
//                sensorObserver.onCompleted();
//            }
//        };
//    }

//    public StreamObserver<HVACResponse> hvacNotifications(StreamObserver<HVACMessage> responseObserver) {
//        // Implementation for receiving HVAC notifications (Server-side streaming RPC)
//        return new StreamObserver<HVACResponse>() {
//            boolean status;
//            String message = "";
//            @Override
//            public void onNext(HVACResponse hvacResponse) {
//                // Process incoming HVAC responses and send notifications
//                status = hvacResponse.getStatus();
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                // Handle errors
//            }
//
//            @Override
//            public void onCompleted() {
//                // Finalize processing
//                if (status) {
//                    message = "HVAC is on.";
//                } else {
//                    message = "HVAC is off.";
//                }
//                HVACMessage hvacMessage = HVACMessage.newBuilder()
//                        .setStatus(status)
//                        .setMessage(message)
//                        .build();
//
//                responseObserver.onNext(hvacMessage);
//                responseObserver.onCompleted();
//            }
//        };
//    }

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