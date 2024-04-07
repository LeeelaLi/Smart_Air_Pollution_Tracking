import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class NotifyClient {
    public static void main(String[] args) {
        SensorClient.main(args);
        HVACClient.main(args);

        // Use sensor response data
        SensorResponse sensorResponse = SensorClient.getSensorResponse();
        int pollutionLevel = sensorResponse.getPollutionLevel();
        boolean status = pollutionLevel > 2;
        // Use HVAC response data
        HVACResponse hvacResponse1 = HVACClient.gethvacResponse();

        // create sensor Notification channel
        ManagedChannel sensorNotifyChannel = ManagedChannelBuilder.forAddress("localhost", 9094)
                .usePlaintext()
                .build();
        NotificationGrpc.NotificationBlockingStub sensorNotifyBlockingStub = NotificationGrpc.newBlockingStub(sensorNotifyChannel);

        SensorResponse sensorResponsetoNotify = SensorResponse.newBuilder()
                .setPollutionLevel(sensorResponse.getPollutionLevel())
                .build();
        NotificationMessage sensorMessage = sensorNotifyBlockingStub.sensorNotifications(sensorResponsetoNotify).next();
        System.out.println("Air quality message from sensor. " +
                "\n1. Detected from: " + sensorMessage.getLocation() +
                "\n2. Air quality: " + sensorMessage.getAirQuality() +
                "\n3. Advice: " + sensorMessage.getMessage() +
                "\n4. Time: " + sensorMessage.getTimestamp());

        // create HVAC Notification channel
        ManagedChannel hvacNotifyChannel = ManagedChannelBuilder.forAddress("localhost", 9095)
                .usePlaintext()
                .build();
        NotificationGrpc.NotificationBlockingStub hvacNotifyBlockingStub = NotificationGrpc.newBlockingStub(hvacNotifyChannel);

        HVACResponse hvacResponsetoNotify = HVACResponse.newBuilder()
                .setStatus(status)
                .build();
        NotificationMessage hvacMessage = hvacNotifyBlockingStub.ventNotifications(hvacResponsetoNotify).next();
        System.out.println("Air quality message from HVAC. " +
                "\n1. Detected from: " + hvacMessage.getLocation() +
                "\n2. Air quality: " + hvacMessage.getAirQuality() +
                "\n3. Advice: " + hvacMessage.getMessage() +
                "\n4. Time: " + hvacMessage.getTimestamp());
    }
}
