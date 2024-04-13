import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.time.Instant;

import com.chuntao.service.*;

public class HVACServer {

    public static int pollutionLevel;
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

        public StreamObserver<AnalyseResponse> hvacControl(StreamObserver<HVACCommand> responseObserver) {
            return new StreamObserver<AnalyseResponse>() {

                @Override
                public void onNext(AnalyseResponse analyseResponse) {
                    pollutionLevel = analyseResponse.getPollutionLevel();
                    System.out.println("Received pollution level: " + pollutionLevel);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC control: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    String action = (pollutionLevel > 2) ? "START" : "STOP";
                    HVACCommand hvacCommand = HVACCommand.newBuilder()
                            .setAction(HVACCommand.Action.valueOf(action))
                            .build();

                    responseObserver.onNext(hvacCommand);
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<HVACCommand> hvacSwitch(StreamObserver<HVACResponse> responseObserver) {
            return new StreamObserver<HVACCommand>() {
                @Override
                public void onNext(HVACCommand hvacCommand) {
                    System.out.println("HVAC command message: " + hvacCommand.getAction());

                    boolean status = hvacCommand.getAction() == HVACCommand.Action.START;
                    HVACResponse hvacResponse = HVACResponse.newBuilder()
                                    .setStatus(true)
                                    .setTimestamp(timestampNow())
                                    .build();

                    responseObserver.onNext(hvacResponse);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in HVAC switch: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("HVAC client stream completed");
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
}
