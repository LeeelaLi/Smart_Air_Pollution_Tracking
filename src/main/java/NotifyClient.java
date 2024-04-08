import com.chuntao.service.*;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.Instant;
import java.util.Date;

public class NotifyClient {
    public static void main(String[] args) {

        // Use sensor response data
        int pollutionLevel = SensorClient.pollution_level;
        String location;
        String air_quality;
        String message;
        int id = SensorClient.id;
        if(id == 1){
            location = "home";
        } else if (id == 2) {
            location = "garden";
        }else {
            location = "car";
        }

        if (pollutionLevel < 2) {
            air_quality = "Great";
            message = "The air is healthy, HVAC is off";
        } else if (pollutionLevel == 2) {
            air_quality = "Moderate";
            message = "The air is fine. HVAC is off now, you could turn on the HVAC.";
        } else {
            air_quality = "Bad";
            message = "The air is harmed, HVAC is automatically on.";
        }

        // create sensor Notification channel
        ManagedChannel sensorNotifyChannel = ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();
        NotificationGrpc.NotificationBlockingStub sensorNotifyBlockingStub = NotificationGrpc.newBlockingStub(sensorNotifyChannel);

        NotificationMessage sensorMessage = NotificationMessage.newBuilder()
                .setLocation(location)
                .setAirQuality(air_quality)
                .setMessage(message)
                .build();
        Date updatedTime = new Date(sensorMessage.getTimestamp().getSeconds() * 1000);

        System.out.println("Air quality message from sensor. " +
                "\n1. Detected from: " + sensorMessage.getLocation() +
                "\n2. Air quality: " + sensorMessage.getAirQuality() +
                "\n3. Advice: " + sensorMessage.getMessage() +
                "\n4. Time: " + updatedTime);

        // create HVAC Notification
        NotificationMessage hvacMessage = NotificationMessage.newBuilder()
                        .setLocation(location)
                        .setMessage(message)
                        .setTimestamp(timestampNow())
                        .build();
        System.out.println("\nAir quality message from HVAC. " +
                "\n1. Detected from: " + hvacMessage.getLocation() +
                "\n2. Air quality: " + sensorMessage.getAirQuality() +
                "\n3. Advice: " + sensorMessage.getMessage() +
                "\n4. Time: " + updatedTime);
        sensorNotifyChannel.shutdown();
    }
    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }
}
