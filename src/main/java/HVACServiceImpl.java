import com.chuntao.service.AnalyseResponse;
import com.chuntao.service.HVACCommand;
import com.chuntao.service.HVACGrpc;
import com.chuntao.service.HVACResponse;

import io.grpc.stub.StreamObserver;
import com.google.protobuf.Timestamp;
import java.time.Instant;

public class HVACServiceImpl extends HVACGrpc.HVACImplBase {
        @Override
        public StreamObserver<AnalyseResponse> hvacAction(StreamObserver<HVACCommand> responseObserver) {
            return null;
        }

        @Override
        public StreamObserver<AnalyseResponse> hvacControl(StreamObserver<HVACCommand> responseObserver) {
            return new StreamObserver<AnalyseResponse>() {
                int pollution_level;
                String action;
                String HVACAction;
                @Override
                public void onNext(AnalyseResponse analyseResponse) {
                    pollution_level = analyseResponse.getPollutionLevel();
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    if(pollution_level > 2) {
                        action = "START";
                        HVACAction = "Turn on the HVAC";
                    } else {
                        action = "STOP";
                        HVACAction = "Turn off the HVAC";
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
                            response.setLocation("Location").setStatus(true).setTimestamp(timestampNow());
                            break;
                        case STOP:
                            response.setLocation("Location").setStatus(false).setTimestamp(timestampNow());
                            break;
                        default:
                            // Handle unsupported action
                            break;
                    }
                    responseObserver.onNext(response.build());
                }

                @Override
                public void onError(Throwable throwable) {
                    // Handle error
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
