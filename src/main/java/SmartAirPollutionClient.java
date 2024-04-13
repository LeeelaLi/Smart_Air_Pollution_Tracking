import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SmartAirPollutionClient {
    private final ManagedChannel channel;
    private final SmartAirPollutionGrpc.SmartAirPollutionStub stub;

    int pollution_level;

    public SmartAirPollutionClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = SmartAirPollutionGrpc.newStub(channel);
    }

    public void GetSensorData(int sensor_id) {
        SensorRequest request = SensorRequest.newBuilder()
                .setSensorId(sensor_id)
                .build();
        stub.getSensorData(request, new StreamObserver<SensorResponse>() {

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
                pollution_level = analyseResponse.getPollutionLevel();
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
                HVACControl(sensor_id);
            }
        };

        StreamObserver<SensorResponse> requestObserver = stub.analyseSensorData(responseObserver);

        // Send the sensor ID to the server to start analysing data for that sensor
        requestObserver.onNext(SensorResponse.newBuilder().setLocation("Sensor" + sensor_id).build());
        requestObserver.onCompleted();
    }

    public void HVACControl(int sensor_id) {
        StreamObserver<HVACCommand> hvacCommandObserver = new StreamObserver<HVACCommand>() {
            @Override
            public void onNext(HVACCommand hvacCommand) {
                System.out.println("\nHVAC is: " + hvacCommand.getAction());
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in receiving HVACCommand: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC command stream completed");
            }
        };
        StreamObserver<AnalyseResponse> analyseResponseObserver = stub.hVACControl(hvacCommandObserver);

        analyseResponseObserver.onNext(AnalyseResponse.newBuilder().setPollutionLevel(sensor_id).build());
        analyseResponseObserver.onCompleted();
    }

    public void sensorNotifications() {
        StreamObserver<SensorMessage> sensorObserver = new StreamObserver<SensorMessage>() {
            @Override
            public void onNext(SensorMessage sensorMessage) {
                System.out.println("Sensor server message: " +
                        "\n1. Location: " + sensorMessage.getLocation() +
                        "\n2. Air quality: " + sensorMessage.getAirQuality() +
                        "\n3. Advice: " + sensorMessage.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in server streaming: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server streaming completed");
            }
        };
        stub.sensorNotifications(AnalyseResponse.newBuilder().setPollutionLevel(1).build(), sensorObserver);
    }

    public void hvacNotifications() {
        StreamObserver<HVACMessage> havcObserver = new StreamObserver<HVACMessage>() {
            @Override
            public void onNext(HVACMessage hvacMessage) {
                System.out.println("HVAC server message: " +
                        "\n1. HVAC status: " + hvacMessage.getStatus() +
                        "\n2. Message: " + hvacMessage.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in HVAC server streaming: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server streaming completed");
            }
        };
        stub.hVACNotifications(HVACResponse.newBuilder().setStatus(true).build(), havcObserver);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 9090;
        System.out.println("Select the sensor id you want to review:" +
                "\n1. Sensor 1 - Home" +
                "\n2. Sensor 2 - Garden" +
                "\n3. Sensor 3 - Car");
        Scanner keyboard = new Scanner(System.in);

        SmartAirPollutionClient smartAirPollutionClient = new SmartAirPollutionClient(host, port);

        // Wait for user input to continue request or stop streaming
        while (true) {
            System.out.println("Enter sensor ID (or 'q' to quit):");
            if (keyboard.hasNextInt()) {
                int sensor_id = keyboard.nextInt();
                if (sensor_id > 0 && sensor_id < 4) {
                    smartAirPollutionClient.GetSensorData(sensor_id);
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
        smartAirPollutionClient.shutdown();
    }
}