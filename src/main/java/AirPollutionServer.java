import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;

import java.time.Instant;

public class AirPollutionServer extends AirPollutionTrackGrpc.AirPollutionTrackImplBase {
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
                response.setLocation("Home").setPM(9).setTemperature(20).setVOC(0.3F).setHumidity(36).setCO(8).setPollutionLevel(2).setTimestamp(timestampNow());
            case 2:
                response.setLocation("Garden").setPM(17).setTemperature(19).setVOC(0.1F).setHumidity(45).setCO(14).setPollutionLevel(4).setTimestamp(timestampNow());
            default:
                response.setLocation("Car").setPM(13).setTemperature(24).setVOC(0.6F).setHumidity(27).setCO(11).setPollutionLevel(3).setTimestamp(timestampNow());
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
        System.out.println("Select the item you want to review:" +
                "\n1. Item 1 - PM2.5(μg/m3)" +
                "\n2. Item 2 - Temperature(Celsius)" +
                "\n3. Item 3 - VOC(mg/m3)" +
                "\n4. Item 4 - Humidity(%)" +
                "\n5. Item 5 - CO(ppm)" +
                "\n6. Item 6 - Pollution Level(0-7)");
        int sensor_id = request.getSensorId();

        SensorResponse.Builder response = SensorResponse.newBuilder();
        switch (sensor_id){
            case 1:
                response.setPM(9);
            case 2:
                response.setTemperature(19);
            case 3:
                response.setVOC(0.6F);
            case 4:
                response.setHumidity(27);
            case 5:
                response.setCO(10);
            default:
                response.setPollutionLevel(3);
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
