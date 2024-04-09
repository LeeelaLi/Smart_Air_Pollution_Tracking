import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SensorClient {
    private String location;
    private final ManagedChannel sensorChannel;
    private final SensorGrpc.SensorStub sensorStub;

    public SensorClient(String host, int port) {
        this.sensorChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.sensorStub = SensorGrpc.newStub(sensorChannel);
    }

    public void GetSensorData(int sensor_id) {
        SensorRequest request = SensorRequest.newBuilder()
                .setSensorId(sensor_id)
                .build();
        sensorStub.getSensorData(request, new StreamObserver<SensorResponse>() {

            @Override
            public void onNext(SensorResponse sensorResponse) {
                System.out.println("Sensor response from " + sensorResponse.getLocation() + ":" +
                        "\n1. PM2.5: " + sensorResponse.getPM25() +
                        "\n2. Temperature: " + sensorResponse.getTemperature() +
                        "\n3. VOC: " + sensorResponse.getVOC() +
                        "\n4. Humidity: " + sensorResponse.getHumidity() +
                        "\n5. CO: " + sensorResponse.getCO());
                location = sensorResponse.getLocation();
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in Sensor request: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sensor data request completed");
            }
        });
    }

    public void AnalyseSensorData(int sensor_id) {
        StreamObserver<AnalyseResponse> responseObserver = new StreamObserver<AnalyseResponse>() {
            @Override
            public void onNext(AnalyseResponse analyseResponse) {
                // Process each AnalyseResponse received
                System.out.println("\nAnalyse Response: ");
                System.out.println("Location: " + location);
                System.out.println("Analyse: " + analyseResponse.getAnalyse());
                System.out.println("Pollution Level: " + analyseResponse.getPollutionLevel());
                System.out.println("Message: " + analyseResponse.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle error if any
                System.err.println("Error in receiving AnalyseResponse: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                // Stream completed, perform any necessary cleanup or finalization
                System.out.println("Analyse response stream completed");
            }
        };

        StreamObserver<SensorResponse> requestObserver = sensorStub.analyseSensorData(responseObserver);

        // Send the sensor ID to the server to start analysing data for that sensor
        requestObserver.onNext(SensorResponse.newBuilder().setLocation("Sensor" + sensor_id).build());
        requestObserver.onCompleted();
    }

    public void shutdown() throws InterruptedException {
        sensorChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 9090;
        int sensor_id;
        System.out.println("Select the sensor id you want to review:" +
                "\n1. Sensor 1 - Home" +
                "\n2. Sensor 2 - Garden" +
                "\n3. Sensor 3 - Car");
        Scanner keyboard = new Scanner(System.in);
        sensor_id = keyboard.nextInt();

        SensorClient sensorClient = new SensorClient(host, port);
        sensorClient.GetSensorData(sensor_id); // Send unary request
        sensorClient.AnalyseSensorData(sensor_id); // Start streaming analysis

        // Wait for user input to continue request or stop streaming
        while (true) {
            System.out.println("Enter sensor ID (or 'q' to quit):");
            if (keyboard.hasNextInt()) {
                int sensor_id1 = keyboard.nextInt();
                if (sensor_id1 > 0 && sensor_id1 < 4) {
                    sensorClient.GetSensorData(sensor_id1);
                    sensorClient.AnalyseSensorData(sensor_id1);
                } else {
                    System.out.println("Invalid sensor ID. Sensor ID should be between 1 and 3.");
                }
            } else if (keyboard.hasNext()) {
                String input = keyboard.next();
                if (input.equalsIgnoreCase("q")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid sensor ID or 'q' to quit.");
                }
            }
        }

        // Shutdown client
        sensorClient.shutdown();
    }
}


//     private int pollutionLevel; // Member variable to store pollution level
//public int getPollutionLevel() {
//    return pollutionLevel; // Method to retrieve pollution level
//}

//    public int analyseSensorData(SensorResponse sensorResponse) {
//        int pollution_level = 0;
//        // Perform pollution analysis based on sensor response
//        if (sensorResponse.getPM25() > 12) {
//            pollution_level++;
//        }
//        if (sensorResponse.getTemperature() < 18 || sensorResponse.getTemperature() > 22) {
//            pollution_level++;
//        }
//        if (sensorResponse.getVOC() < 0 || sensorResponse.getVOC() > 0.5) {
//            pollution_level++;
//        }
//        if (sensorResponse.getHumidity() < 30 || sensorResponse.getHumidity() > 50) {
//            pollution_level++;
//        }
//        if (sensorResponse.getCO() < 0 || sensorResponse.getCO() > 15) {
//            pollution_level++;
//        }
//        return pollution_level;
//    }

//    public static <Empty> void main(String[] args) {
//
//        ManagedChannel sensorChannel = ManagedChannelBuilder.forAddress("localhost", 9090)
//                .usePlaintext()
//                .build();
//        SensorGrpc.SensorBlockingStub sensorBlockingStub = SensorGrpc.newBlockingStub(sensorChannel);
//
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Select the sensor id you want to review:" +
//                "\n1. Sensor 1 - Home" +
//                "\n2. Sensor 2 - Garden" +
//                "\n3. Sensor 3 - Car");
//        int sensorId;
//        do {
//            try {
//                sensorId = keyboard.nextInt();
//                if (sensorId < 1 || sensorId > 3) {
//                    System.out.println("Invalid sensor ID. Please enter a number between 1 and 3.");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Invalid input. Please enter a valid number.");
//                keyboard.next();
//                sensorId = -1; // Set to an invalid value to continue the loop
//            }
//        } while (sensorId < 1 || sensorId > 3);
//
//        id = sensorId;
//
//        SensorRequest sensorRequest = SensorRequest.newBuilder()
//                .setSensorId(sensorId)
//                .build();
//        SensorResponse sensorResponse = sensorBlockingStub.getSensorData(sensorRequest);
//        printSensorResponse(sensorResponse);
//
//        // Ask user to review analyse
//        System.out.println("Do you want to analyze this sensor data? (yes/other)");
//        keyboard.nextLine(); // Consume newline left from previous nextInt
//        String analyzeChoice = keyboard.nextLine().trim().toLowerCase();
//        if (analyzeChoice.equals("yes")) {
//            // Analyze the sensor data
//            AnalyseResponse analyseResponse = createAnalyseResponse(sensorResponse);
//            printAnalyseResponse(analyseResponse);
//            //updatePollutionLevel(analyseResponse);
//            pollution_level = analyseResponse.getPollutionLevel();
//        } else {
//            System.out.println("Thank you for using the sensor service.");
//        }
//
//        sensorChannel.shutdown();
//    }
//
//    private static void printSensorResponse(SensorResponse sensorResponse) {
//        System.out.println("The air index of " + sensorResponse.getLocation() + ":" +
//                "\n1. PM25: " + sensorResponse.getPM25() + " μg/m3" +
//                "\n2. Temperature: " + sensorResponse.getTemperature() + " °C" +
//                "\n3. VOC: " + sensorResponse.getVOC() + " mg/m3" +
//                "\n4. Humidity: " + sensorResponse.getHumidity() + " %" +
//                "\n5. CO: " + sensorResponse.getCO() + " ppm");
//    }
//
//    private static AnalyseResponse createAnalyseResponse(SensorResponse sensorResponse) {
//        int pollution_item = 0;
//        StringBuilder analyseBuilder = new StringBuilder("Pollution item(s): ");
//
//        if (sensorResponse.getPM25() > 12) {
//            pollution_item++;
//            analyseBuilder.append("\n· PM2.5 is over 12 μg/m3.");
//        }
//        if (sensorResponse.getTemperature() < 18 || sensorResponse.getTemperature() > 22) {
//            pollution_item++;
//            analyseBuilder.append("\n· Temperature is exceed 18-22 °C.");
//        }
//        if (sensorResponse.getVOC() < 0 || sensorResponse.getVOC() > 0.5) {
//            pollution_item++;
//            analyseBuilder.append("\n· VOC is exceed 0-0.5 mg/m3.");
//        }
//        if (sensorResponse.getHumidity() < 30 || sensorResponse.getHumidity() > 50) {
//            pollution_item++;
//            analyseBuilder.append("\n· Humidity is exceed 30-50 %.");
//        }
//        if (sensorResponse.getCO() < 0 || sensorResponse.getCO() > 15) {
//            pollution_item++;
//            analyseBuilder.append("\n· PCO is exceed 0-15 ppm.");
//        }
//
//        analyseBuilder.append("\n2. Pollution number: ").append(pollution_item)
//                .append("\n3. Pollution level: ").append(pollution_item)
//                .append("\n4. Quality: ");
//        StringBuilder adviceBuilder = new StringBuilder();
//        if (pollution_item <= 2) {
//            analyseBuilder.append("Low pollution");
//            adviceBuilder.append("The air quality is great, keep going.");
//        } else if (pollution_item == 3) {
//            analyseBuilder.append("Moderate pollution");
//            adviceBuilder.append("The air is a bit polluted, it is recommended to turn on the HVAC.");
//        } else {
//            analyseBuilder.append("High pollution");
//            adviceBuilder.append("The air is highly polluted, HVAC is automatically turning on.");
//        }
//
//        Timestamp timestamp = timestampNow();
//        return AnalyseResponse.newBuilder()
//                .setAnalyse(analyseBuilder.toString())
//                .setLocation(sensorResponse.getLocation())
//                .setPollutionLevel(pollution_item)
//                .setMessage(adviceBuilder.toString())
//                .setTimestamp(timestamp)
//                .build();
//    }
//
//    private static void printAnalyseResponse(AnalyseResponse analyseResponse) {
//        Date updatedTime = new Date(analyseResponse.getTimestamp().getSeconds() * 1000);
//        System.out.println("\nThe air quality of " + analyseResponse.getLocation() + ":" +
//                "\n1. " + analyseResponse.getAnalyse() +
//                "\n5. Advice: " + analyseResponse.getMessage() +
//                "\n6. Updated time: " + updatedTime);
//    }
//
//    private static PollutionLevelResponse updatePollutionLevel(AnalyseResponse analyseResponse) {
//        return PollutionLevelResponse.newBuilder()
//                .setPollutionLevel(analyseResponse.getPollutionLevel())
//                .build();
//    }
//
//    private static void updatePollution(PollutionLevelResponse pollutionLevelResponse) {
//        pollution_level = pollutionLevelResponse.getPollutionLevel();
//    }

//    private static Timestamp timestampNow() {
//        Instant now = Instant.now();
//        return Timestamp.newBuilder()
//                .setSeconds(now.getEpochSecond())
//                .setNanos(now.getNano())
//                .build();
//    }

