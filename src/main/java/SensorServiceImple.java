
import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;

import java.time.Instant;

public class SensorServiceImple extends SensorGrpc.SensorImplBase {
    @Override
    public void getAllSensorData(SensorRequest request, StreamObserver<SensorResponse> responseObserver) {
        System.out.println("Select the sensor id you want to review:" +
                "\n1. Sensor 1 - Home" +
                "\n2. Sensor 2 - Garden" +
                "\n3. Sensor 3 - Car");
        int sensor_id = request.getSensorId();

        SensorResponse.Builder response = SensorResponse.newBuilder();
        switch (sensor_id){
            case 1:
                response.setPM(9).setTemperature(20).setVOC(0.3F).setHumidity(36).setPollutionLevel(2).setTimestamp(timestampNow());
            case 2:
                response.setPM(17).setTemperature(19).setVOC(0.1F).setHumidity(45).setPollutionLevel(4).setTimestamp(timestampNow());
            default:
                response.setPM(13).setTemperature(24).setVOC(0.6F).setHumidity(27).setPollutionLevel(3).setTimestamp(timestampNow());
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    private Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    @Override
    public void getSingleSensorData(SensorRequest request, StreamObserver<SensorResponse> responseObserver) {

    }

    public static void main(String[] args) {

    }
}
