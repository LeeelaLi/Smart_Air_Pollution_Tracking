import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.time.Instant;

import com.chuntao.service.*;

public class HVACServer {

    private Server server;
    public static int pollutionLevel;
    public void start(int port) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new HVACImpl())
                .build()
                .start();
        System.out.println("HVAC server started, listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            try {
                HVACServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static class HVACImpl extends HVACGrpc.HVACImplBase {

        @Override
        public StreamObserver<AnalyseResponse> hvacControl(StreamObserver<HVACCommand> responseObserver) {
            return new StreamObserver<AnalyseResponse>() {
                boolean isOn = false;

                @Override
                public void onNext(AnalyseResponse analyseResponse) {
                    pollutionLevel = analyseResponse.getPollutionLevel();
                    isOn = pollutionLevel > 2;
                    HVACCommand.Action action;
                    if (isOn) {
                        action = HVACCommand.Action.START;
                    } else {
                        action = HVACCommand.Action.STOP;
                    }
                    HVACCommand hvacCommand = HVACCommand.newBuilder()
                            .setAction(action)
                            .build();

                    responseObserver.onNext(hvacCommand);
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle error if any
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<HVACCommand> hvacSwitch(StreamObserver<HVACResponse> responseObserver) {
            return new StreamObserver<HVACCommand>() {
                @Override
                public void onNext(HVACCommand hvacCommand) {
                    HVACResponse hvacResponse = HVACResponse.newBuilder()
                            .setStatus(true)
                            .setLocation("Home")
                            .setTimestamp(timestampNow())
                            .build();
                    responseObserver.onNext(hvacResponse);
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }

    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HVACServer hvacServer = new HVACServer();
        hvacServer.start(9091);
        hvacServer.blockUntilShutdown();
    }
}
