import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.Timestamp;
import com.chuntao.service.*;

import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

public class SensorClient {
    public static int pollution_level;
    public static int id;
    public static String location;

    public static void main(String[] args) {
        ManagedChannel sensorChannel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        SensorGrpc.SensorBlockingStub sensorBlockingStub = SensorGrpc.newBlockingStub(sensorChannel);

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Select the sensor id you want to review:" +
                "\n1. Sensor 1 - Home" +
                "\n2. Sensor 2 - Garden" +
                "\n3. Sensor 3 - Car");
        int sensorId;
        do {
            try {
                sensorId = keyboard.nextInt();
                if (sensorId < 1 || sensorId > 3) {
                    System.out.println("Invalid sensor ID. Please enter a number between 1 and 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                keyboard.next();
                sensorId = -1; // Set to an invalid value to continue the loop
            }
        } while (sensorId < 1 || sensorId > 3);

        id = sensorId;

        SensorRequest sensorRequest = SensorRequest.newBuilder()
                .setSensorId(sensorId)
                .build();
        SensorResponse sensorResponse = sensorBlockingStub.getSensorData(sensorRequest);
        printSensorResponse(sensorResponse);

        System.out.println("Do you want to analyze this sensor data? (yes/no)");
        keyboard.nextLine(); // Consume newline left from previous nextInt
        String analyzeChoice = keyboard.nextLine().trim().toLowerCase();
        if (analyzeChoice.equals("yes")) {
            // Analyze the sensor data
            ManagedChannel analyseChannel = ManagedChannelBuilder.forAddress("localhost", 9091)
                    .usePlaintext()
                    .build();
            SensorGrpc.SensorStub sensorStub = SensorGrpc.newStub(analyseChannel);
            AnalyseResponse analyseResponse = createAnalyseResponse(sensorResponse);
            printAnalyseResponse(analyseResponse);
            analyseChannel.shutdown();
        } else {
            System.out.println("Thank you for using the sensor service.");
        }

        sensorChannel.shutdown();
    }

    private static void printSensorResponse(SensorResponse sensorResponse) {
        System.out.println("The air index of " + sensorResponse.getLocation() + ":" +
                "\n1. PM25: " + sensorResponse.getPM25() + " μg/m3" +
                "\n2. Temperature: " + sensorResponse.getTemperature() + " °C" +
                "\n3. VOC: " + sensorResponse.getVOC() + " mg/m3" +
                "\n4. Humidity: " + sensorResponse.getHumidity() + " %" +
                "\n5. CO: " + sensorResponse.getCO() + " ppm" +
                "\nAir pollution level: " + sensorResponse.getPollutionLevel());
        location = sensorResponse.getLocation();
        pollution_level = sensorResponse.getPollutionLevel();
    }

    private static AnalyseResponse createAnalyseResponse(SensorResponse sensorResponse) {
        int pollution_level = sensorResponse.getPollutionLevel();
        StringBuilder analyseBuilder = new StringBuilder("Pollution level: ");
        if (pollution_level <= 0) {
            analyseBuilder.append("Low pollution");
        } else if (pollution_level < 2) {
            analyseBuilder.append("Moderate pollution");
        } else {
            analyseBuilder.append("High pollution");
        }
        analyseBuilder.append("\n2. Pollution item(s): ");
        if (sensorResponse.getPM25() > 12) {
            analyseBuilder.append("PM2.5 is over 12.");
        }
        if (sensorResponse.getTemperature() < 18 || sensorResponse.getTemperature() > 22) {
            analyseBuilder.append("Temperature is exceed 18-22.");
        }
        if (sensorResponse.getVOC() < 0 || sensorResponse.getVOC() > 0.5) {
            analyseBuilder.append("VOC is exceed 0-0.5.");
        }
        if (sensorResponse.getHumidity() < 30 || sensorResponse.getHumidity() > 50) {
            analyseBuilder.append("Humidity is exceed 30-50.");
        }
        if (sensorResponse.getCO() < 0 || sensorResponse.getCO() > 15) {
            analyseBuilder.append("PCO is exceed 0-15.");
        }
        Timestamp timestamp = timestampNow();
        return AnalyseResponse.newBuilder()
                .setAnalyse(analyseBuilder.toString())
                .setLocation(sensorResponse.getLocation())
                .setTimestamp(timestamp)
                .build();
    }

    private static void printAnalyseResponse(AnalyseResponse analyseResponse) {
        Date updatedTime = new Date(analyseResponse.getTimestamp().getSeconds() * 1000);
        System.out.println("\nThe air quality of " + analyseResponse.getLocation() + ":" +
                "\n1. " + analyseResponse.getAnalyse() +
                "\n3. Updated time: " + updatedTime);
    }

    private static Timestamp timestampNow() {
        Instant now = Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }
}
