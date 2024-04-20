import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AirPollutionClient {
    private final ManagedChannel sensorChannel;
    private final SensorGrpc.SensorStub sensorStub;
    private final String consulServiceName;
    private final HVACGrpc.HVACStub hvacStub;
    private final NotificationGrpc.NotificationStub notificationStub;
    private static int pollution_level;
    private static HVACCommand.Action action;
    private static String status = null;
    private static boolean turn_on;
    private boolean sensorNotificationsActive = false;

    public AirPollutionClient(String host, int port, String consulServiceName) {
        this.sensorChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.sensorStub = SensorGrpc.newStub(sensorChannel);
        this.hvacStub = HVACGrpc.newStub(sensorChannel);
        this.notificationStub = NotificationGrpc.newStub(sensorChannel);
        this.consulServiceName = consulServiceName;
    }

    public void shutdown() throws InterruptedException {
        sensorChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void GetSensorData(int sensor_id, MessagePassingQueue.Consumer<String> callback) {
        SensorRequest request = SensorRequest.newBuilder()
                .setSensorId(sensor_id)
                .build();
        sensorStub.getSensorData(request, new StreamObserver<>() {
            @Override
            public void onNext(SensorResponse sensorResponse) {
                String pm25Value = String.format("%.2f", sensorResponse.getPM25());
                String tempValue = String.format("%.2f", sensorResponse.getTemperature());
                String vocValue = String.format("%.2f", sensorResponse.getVOC());
                String humidValue = String.format("%.2f", sensorResponse.getHumidity());
                String coValue = String.format("%.2f", sensorResponse.getCO());

                String sensorData = "\nSensor response from " + sensorResponse.getLocation() + ":" +
                        "\n1. PM2.5 - " + pm25Value + " μg/m3" +
                        "\n2. Temperature - " + tempValue + " °C" +
                        "\n3. VOC - " + vocValue + " mg/m3" +
                        "\n4. Humidity - " + humidValue + " %" +
                        "\n5. CO - " + coValue + " ppm";
                callback.accept(sensorData); // Invoke the callback with sensor data
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

    public void AnalyseSensorData(String analyseOrNot, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<AnalyseResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(AnalyseResponse analyseResponse) {
                Date updatedTime = new Date(analyseResponse.getTimestamp().getSeconds() * 1000);
                // Process each AnalyseResponse received
                String analyseData = "\nAnalyse Response: " +
                        "\n1. Location: " + analyseResponse.getLocation() +
                        "\n2. Polluted item(s): " + analyseResponse.getAnalyse() +
                        "\n3. Pollution Level: " + analyseResponse.getPollutionLevel() +
                        "\n4. Analyse: " + analyseResponse.getMessage() +
                        "\n5. Updated time: " + updatedTime;
                callback.accept(analyseData);
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
        requestObserver.onNext(SensorResponse.newBuilder().setLocation("HOME").build());
        requestObserver.onCompleted();
    }

    public void HVACControl(int control, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<HVACCommand> hvacCommandObserver = new StreamObserver<>() {
            @Override
            public void onNext(HVACCommand hvacCommand) {
                if (control == 1) {
                    HVACCommand.Builder hvacCommand1 = HVACCommand.newBuilder();
                    hvacCommand1.setAction(HVACCommand.Action.START).build();
                    String hvacCommandMessage = "\nHVAC Command: " +
                            "\n1. HVAC is: " + hvacCommand1.getAction();
                    action = hvacCommand1.getAction();
                    callback.accept(hvacCommandMessage);
                } else if (control == 2) {
                    HVACCommand.Builder hvacCommand1 = HVACCommand.newBuilder();
                    hvacCommand1.setAction(HVACCommand.Action.STOP).build();
                    String hvacCommandMessage = "\nHVAC Command: " +
                            "\n1. HVAC is: " + hvacCommand1.getAction();
                    action = hvacCommand1.getAction();
                    callback.accept(hvacCommandMessage);
                } else {
                    callback.accept("Sensor data is empty");
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in HVAC Command: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC Command completed");
            }
        };
        StreamObserver<HVACRequest> hvacRequestObserver = hvacStub.hvacControl(hvacCommandObserver);

        hvacRequestObserver.onNext(HVACRequest.newBuilder().setPollutionLevel(pollution_level).build());
        hvacRequestObserver.onCompleted();
    }

    public void HVACSwitch(int turn_on, MessagePassingQueue.Consumer<String> callback) {

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
                String hvacSwitchMessage ="\nHVAC Switch: " +
                        "\n1. HVAC is changed to: " + hvacResponse1.getStatus() +
                        "\n2. Pollution level: " + hvacResponse.getPollutionLevel() +
                        "\n3. Time: " + responseTime;

                status = hvacResponse1.getStatus();
                pollution_level = hvacResponse.getPollutionLevel();
                callback.accept(hvacSwitchMessage);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error from server: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC Switch completed");
            }
        };

        // Create a request observer for the client streaming
        StreamObserver<HVACCommand> hvacCommandObserver = hvacStub.hvacSwitch(hvacResponseObserver);
        try {
            HVACCommand hvacCommand = HVACCommand.newBuilder()
                    .setAction(action)
                    .build();

            hvacCommandObserver.onNext(hvacCommand);
        }catch (Exception e) {
            System.err.println("Error while sending messages: " + e.getMessage());
        }
        hvacResponseObserver.onCompleted();
    }

    public void sensorNotifications(int turn_on, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<SensorMessage> sensorObserver = new StreamObserver<>() {
            @Override
            public void onNext(SensorMessage sensorMessage) {
                if(status.equalsIgnoreCase("OFF")) {
                    SensorMessage.Builder sensorMessage1 = SensorMessage.newBuilder();
                    sensorMessage1.setMessage("The air is harmed and you turn off the HVAC manually. Please turn on the HVAC").build();
                    String sensorNotify = "\nSensor notifications: " +
                            "\n1. Air quality: " + sensorMessage.getAirQuality() +
                            "\n2. Advice: " + sensorMessage1.getMessage();
                    callback.accept(sensorNotify);
                } else {
                    String sensorNotify = "\nSensor notifications: " +
                            "\n1. Air quality: " + sensorMessage.getAirQuality() +
                            "\n2. Advice: " + sensorMessage.getMessage();
                    callback.accept(sensorNotify);
                }
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

    public void hvacNotifications(MessagePassingQueue.Consumer<String> callback) {
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
                String hvacNotify = "\nHVAC notifications: " +
                        "\n1. HVAC status: " + hvacMessage1.getStatus() +
                        "\n2. Message: " + hvacMessage1.getMessage();
                callback.accept(hvacNotify);
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
        String consulServiceName = "AirPollutionService";
        Scanner keyboard = new Scanner(System.in);

        AirPollutionClient airPollutionClient = new AirPollutionClient(host, port, consulServiceName);

        // Wait for user input to continue request or stop streaming
        while (true) {
            System.out.println("Enter '1' to get sensor data, 'q' to quit:");
            if (keyboard.hasNextInt()) {
                int sensor_id = keyboard.nextInt();
                if (sensor_id == 1) {
                    airPollutionClient.GetSensorData(sensor_id, sensorData -> {
                        System.out.println(sensorData); // Print sensor data
                    });
                    System.out.println("Enter 'yes' to analyse sensor data, others to go back to sensor data:");
                    String if_analyse = keyboard.next();
                    while (if_analyse.equalsIgnoreCase("yes")) {
                        airPollutionClient.AnalyseSensorData(if_analyse, analyseData -> {
                            System.out.println(analyseData); // Print sensor data
                        });
                        System.out.println("Enter 'yes' to check hvac data:");
                        if (keyboard.hasNext()) {
                            String input = keyboard.next();
                            if (input.equalsIgnoreCase("yes")) {
                                airPollutionClient.HVACControl(1, hvacCommandMessage -> {
                                    System.out.println(hvacCommandMessage); // Print sensor data
                                });
                                System.out.println("Do you want to turn on(1)/turn off(2)?");
                                int turn_on = keyboard.nextInt();
                                airPollutionClient.HVACSwitch(turn_on, hvacSwitchMessage -> {
                                    System.out.println(hvacSwitchMessage);
                                });
                                airPollutionClient.HVACControl(turn_on, hvacCommandMessage -> {
                                    System.out.println(hvacCommandMessage); // Print sensor data
                                });
                                System.out.println("Do you want to turn on the notify(1)?");
                                int turnOnNotify = keyboard.nextInt();
                                if (turnOnNotify == 1) {
                                    airPollutionClient.sensorNotifications(turnOnNotify, sensorNotify -> {
                                        System.out.println(sensorNotify);
                                    });
                                    airPollutionClient.hvacNotifications(hvacNotify -> {
                                        System.out.println(hvacNotify);
                                    });
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid number.");
                }
            } else {
                String input = keyboard.next();
                System.out.println("Invalid input.");
                if (input.equalsIgnoreCase("q")) {
                    break;
                }
            }
        }
        // Shutdown client
        airPollutionClient.shutdown();
    }
}
