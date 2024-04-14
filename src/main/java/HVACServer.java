import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.time.Instant;

import com.chuntao.service.*;

public class HVACServer {

    private static final int PORT = 9091;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(PORT)
                .addService(new HVACImpl())
                .build();

        server.start();
        System.out.println("HVAC server started, listening on port " + PORT);

        // Graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));

        server.awaitTermination();
    }

    private static class HVACImpl extends HVACGrpc.HVACImplBase {

        public StreamObserver<HVACRequest> hvacSwitch(StreamObserver<HVACResponse> responseObserver) {
            StreamObserver<HVACRequest> requestObserver = new StreamObserver<HVACRequest>() {
                HVACResponse.Action action;
                int pollution_level;
                @Override
                public void onNext(HVACRequest hvacRequest) {
                    pollution_level = hvacRequest.getPollutionLevel();
                    System.out.println("HVAC request: " + pollution_level);

                    if (pollution_level > 2) {
                        action = HVACResponse.Action.START;
                    } else {
                        action = HVACResponse.Action.STOP;
                    }

                    HVACResponse hvacResponse = HVACResponse.newBuilder()
                            .setAction(action)
                            .setPollutionLevel(pollution_level)
                            .setTimestamp(timestampNow())
                            .build();
                    responseObserver.onNext(hvacResponse);
                }

                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC switch: " + throwable.getMessage());
                }

                public void onCompleted() {
                    System.out.println("HVAC client stream completed");
                    responseObserver.onNext(HVACResponse.newBuilder()
                            .setAction(action)
                            .setPollutionLevel(pollution_level)
                            .setTimestamp(timestampNow())
                            .build());
                    responseObserver.onCompleted();
                }
            };
            return requestObserver;
        }
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }
}
