import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AirPollutionClient {
    private final ManagedChannel sensorChannel;
    private final SensorGrpc.SensorStub sensorStub;
    private final HVACGrpc.HVACStub hvacStub;
    private final NotificationGrpc.NotificationStub notificationStub;
    private static int pollution_level;
    private static HVACCommand.Action action;
    private static String status;
    private static boolean turn_on;

    public AirPollutionClient(String host, int port) {
        this.sensorChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.sensorStub = SensorGrpc.newStub(sensorChannel);
        this.hvacStub = HVACGrpc.newStub(sensorChannel);
        this.notificationStub = NotificationGrpc.newStub(sensorChannel);
    }

    public void shutdown() throws InterruptedException {
        sensorChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void GetSensorData(int sensor_id) {
        SensorRequest request = SensorRequest.newBuilder()
                .setSensorId(sensor_id)
                .build();
        sensorStub.getSensorData(request, new StreamObserver<>() {

            @Override
            public void onNext(SensorResponse sensorResponse) {
                System.out.println("Sensor response from " + sensorResponse.getLocation() + ":" +
                        "\n1. PM2.5 - " + sensorResponse.getPM25() + " μg/m3" +
                        "\n2. Temperature - " + sensorResponse.getTemperature() + " °C" +
                        "\n3. VOC - " + sensorResponse.getVOC() + " mg/m3" +
                        "\n4. Humidity - " + sensorResponse.getHumidity() + " %" +
                        "\n5. CO - " + sensorResponse.getCO() + " ppm");
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
        StreamObserver<AnalyseResponse> responseObserver = new StreamObserver<>() {
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

    public void HVACControl() {
        StreamObserver<HVACCommand> hvacCommandObserver = new StreamObserver<>() {
            @Override
            public void onNext(HVACCommand hvacCommand) {
                System.out.println("\nHVAC command: " +
                        "\n1. HVAC is: " + hvacCommand.getAction());
                action = hvacCommand.getAction();
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in HVAC command: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC command completed");
            }
        };
        StreamObserver<HVACRequest> hvacRequestObserver = hvacStub.hvacControl(hvacCommandObserver);

        hvacRequestObserver.onNext(HVACRequest.newBuilder().setPollutionLevel(pollution_level).build());
        hvacRequestObserver.onCompleted();
    }

    public void HVACSwitch(int turn_on) {

        // Create a response observer for the server streaming
        StreamObserver<HVACResponse> hvacResponseObserver = new StreamObserver<>() {
            @Override
            public void onNext(HVACResponse hvacResponse) {
                HVACResponse.Builder  hvacResponse1 = HVACResponse.newBuilder();
                Date responseTime = new Date(hvacResponse.getTimestamp().getSeconds() * 1000);
                if (turn_on == 1) {
                    status = "ON";
                    hvacResponse1.setStatus(status).build();
                } else {
                    status = "OFF";
                    hvacResponse1.setStatus(status).build();
                }
                System.out.println("\nHVAC switch: " +
                        "\n1. HVAC is changed to: " + hvacResponse1.getStatus() +
                        "\n2. Pollution level: " + hvacResponse.getPollutionLevel() +
                        "\n3. Time: " + responseTime);
                pollution_level = hvacResponse.getPollutionLevel();
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error from server: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC switch completed");
            }
        };

        // Create a request observer for the client streaming
        StreamObserver<HVACCommand> hvacCommandObserver = hvacStub.hvacSwitch(hvacResponseObserver);
        try {
            HVACCommand hvacCommand = HVACCommand.newBuilder()
                    .setAction(action)
                    .build();

            System.out.println("HVAC switch request:");
            hvacCommandObserver.onNext(hvacCommand);
        }catch (Exception e) {
            System.err.println("Error while sending messages: " + e.getMessage());
        }
        hvacResponseObserver.onCompleted();
    }

    public void sensorNotifications() {
        StreamObserver<SensorMessage> sensorObserver = new StreamObserver<>() {
            @Override
            public void onNext(SensorMessage sensorMessage) {
                System.out.println("\nSensor notifications: " +
                        "\n1. Air quality: " + sensorMessage.getAirQuality() +
                        "\n2. Advice: " + sensorMessage.getMessage());
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
        notificationStub.sensorNotifications(AnalyseResponse.newBuilder().setPollutionLevel(1).build(), sensorObserver);
    }

    public void hvacNotifications() {
        StreamObserver<HVACMessage> hvacObserver = new StreamObserver<>() {
            @Override
            public void onNext(HVACMessage hvacMessage) {
                HVACMessage.Builder hvacMessage1 = HVACMessage.newBuilder();
                if (status.equalsIgnoreCase("ON")) {
                    turn_on = true;
                    hvacMessage1.setMessage(status).setStatus(turn_on);
                } else {
                    turn_on = false;
                    hvacMessage1.setMessage(status).setStatus(turn_on);
                }
                System.out.println("\nHVAC notifications: " +
                        "\n1. HVAC status: " + hvacMessage1.getStatus() +
                        "\n2. Message: " + hvacMessage1.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in HVAC notification streaming: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server streaming completed");
            }
        };
        notificationStub.hvacNotifications(HVACResponse.newBuilder().setPollutionLevel(1).build(), hvacObserver);
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 9090;
        System.out.println("""
                Select the sensor id you want to review:
                1. Sensor 1 - Home
                2. Sensor 2 - Garden
                3. Sensor 3 - Car""");
        Scanner keyboard = new Scanner(System.in);

        AirPollutionClient airPollutionClient = new AirPollutionClient(host, port);

        // Wait for user input to continue request or stop streaming
        while (true) {
            System.out.println("Enter sensor ID (or 'q' to quit):");
            if (keyboard.hasNextInt()) {
                int sensor_id = keyboard.nextInt();
                if (sensor_id > 0 && sensor_id < 4) {
                    airPollutionClient.GetSensorData(sensor_id);
                    System.out.println("Do you want to check hvac data?");
                    if (keyboard.hasNext()) {
                        String input = keyboard.next();
                        if (input.equalsIgnoreCase("yes")) {
                            airPollutionClient.HVACControl();
                            System.out.println("Do you want to turn on(1)/turn off(2)?");
                            int turn_on = keyboard.nextInt();
                            if(turn_on == 1) {
                                airPollutionClient.HVACSwitch(turn_on);
//                                airPollutionClient.sensorNotifications();
                                airPollutionClient.hvacNotifications();
                            }else {
                                airPollutionClient.HVACSwitch(turn_on);
                                airPollutionClient.hvacNotifications();
                            }
                        }
                    }
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
        airPollutionClient.shutdown();
    }
}