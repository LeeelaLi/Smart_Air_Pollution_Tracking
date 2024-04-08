import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;
public class HVACServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server HVACServer = ServerBuilder.forPort(9091)
                .addService(new HVACImpl())
                .build();

        HVACServer.start();
        System.out.println("HVAC Service started on port 9091");
        HVACServer.awaitTermination();
    }
    // HVAC service implement
    static class HVACImpl extends HVACGrpc.HVACImplBase {

        @Override
        public StreamObserver<SensorResponse> hvacAction(StreamObserver<HVACCommand> responseObserver) {
            return null;
        }

        @Override
        public StreamObserver<SensorResponse> hvacControl(StreamObserver<HVACCommand> responseObserver) {
            return new StreamObserver<SensorResponse>() {
                int pollutionLevel;
                String action;
                @Override
                public void onNext(SensorResponse sensorResponse) {
                    pollutionLevel = sensorResponse.getPollutionLevel();
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    if(pollutionLevel > 2) {
                        action = "START";
                    } else {
                        action = "STOP";
                    }
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
                    // Logic to switch HVAC based on the received command
                    HVACResponse.Builder response = HVACResponse.newBuilder();
                    switch (hvacCommand.getAction()) {
                        case START:
                            response.setStatus(true).setTimestamp(timestampNow());
                            break;
                        case STOP:
                            response.setStatus(false).setTimestamp(timestampNow());
                            break;
                        default:
                            // *
                            break;
                    }
                    responseObserver.onNext(response.build());
                }

                @Override
                public void onError(Throwable throwable) {
                    // *
                }

                @Override
                public void onCompleted() {
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