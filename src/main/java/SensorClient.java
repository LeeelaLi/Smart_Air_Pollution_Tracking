import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SensorClient {
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
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in Sensor request: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sensor data request completed");
                AnalyseSensorData(sensor_id);
            }
        });
    }

    public void AnalyseSensorData(int sensor_id) {
        StreamObserver<AnalyseResponse> responseObserver = new StreamObserver<AnalyseResponse>() {
            @Override
            public void onNext(AnalyseResponse analyseResponse) {
                Date updatedTime = new Date(analyseResponse.getTimestamp().getSeconds() * 1000);
                // Process each AnalyseResponse received
                System.out.println("\nAnalyse Response: ");
                System.out.println("1. Location: " + analyseResponse.getLocation());
                System.out.println("2. Polluted item(s): " + analyseResponse.getAnalyse());
                System.out.println("3. Pollution Level: " + analyseResponse.getPollutionLevel());
                System.out.println("4. Analyse: " + analyseResponse.getMessage());
                System.out.println("5. Updated time: " + updatedTime);
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
        System.out.println("Select the sensor id you want to review:" +
                "\n1. Sensor 1 - Home" +
                "\n2. Sensor 2 - Garden" +
                "\n3. Sensor 3 - Car");
        Scanner keyboard = new Scanner(System.in);

        SensorClient sensorClient = new SensorClient(host, port);

        // Wait for user input to continue request or stop streaming
        while (true) {
            System.out.println("Enter sensor ID (or 'q' to quit):");
            if (keyboard.hasNextInt()) {
                int sensor_id = keyboard.nextInt();
                if (sensor_id > 0 && sensor_id < 4) {
                    sensorClient.GetSensorData(sensor_id);
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