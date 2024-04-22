import com.chuntao.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import io.grpc.stub.StreamObserver;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * AirPollutionClient class provides functionality to interact with the Air Pollution service.
 * It allows users to query sensor data, analyze data, control HVAC, and receive notifications.
 */
public class AirPollutionClient {
    private final ManagedChannel clientChannel;
    private final SensorGrpc.SensorStub sensorStub;
    private final HVACGrpc.HVACStub hvacStub;
    private final NotificationGrpc.NotificationStub notificationStub;
    private static String location;
    private static int pollution_level;
    private static HvacCommand.Action action;
    private static Date hvac_switch_updatedTime;
    private static String status = null;
    private static boolean turn_on;

    public AirPollutionClient(String host, int port, String consulServiceName) {
        this.clientChannel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.sensorStub = SensorGrpc.newStub(clientChannel);
        this.hvacStub = HVACGrpc.newStub(clientChannel);
        this.notificationStub = NotificationGrpc.newStub(clientChannel);
    }

    public void shutdown() throws InterruptedException {
        // catch InterruptedException that may occur during the clientChannel shutdown process.
        // ensure that the AirPollutionClient instance is properly closed after use
        try {
            if (clientChannel != null && !clientChannel.isShutdown()) {
                clientChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            }
        // provide more informative error messages to the user
        } catch (InterruptedException e) { // preserve the interrupted status
            Thread.currentThread().interrupt();
            System.err.println("Error while shutting down the channel: " + e.getMessage());
            System.err.println("Please try again later or contact support for assistance.");
        } catch (Exception e) { // catch any other unexpected exceptions
            System.err.println("Unexpected error during shutdown: " + e.getMessage());
            System.err.println("Please try again later or contact support for assistance.");
        }
    }

    // Get sensor data, allow entering an id to query sensor data
    public void GetSensorData(int sensor_id, MessagePassingQueue.Consumer<String> callback) {
        // catch Specific Exceptions: gRPC calls
        try {
            SensorRequest request = SensorRequest.newBuilder()
                    .setSensorId(sensor_id)
                    .build();
            sensorStub.getSensorData(request, new StreamObserver<>() {
                @Override
                public void onNext(SensorResponse sensorResponse) {
                    String sensorData = convertFloat(sensorResponse);
                    location = sensorResponse.getLocation(); // store the queried sensor id location
                    callback.accept(sensorData); // invoke the callback with sensor data
                }

                // create a method to keep two decimal from float
                private static String convertFloat(SensorResponse sensorResponse) {
                    String pm25Value = String.format("%.2f", sensorResponse.getPm25());
                    String tempValue = String.format("%.2f", sensorResponse.getTemperature());
                    String vocValue = String.format("%.2f", sensorResponse.getVoc());
                    String humidValue = String.format("%.2f", sensorResponse.getHumidity());
                    String coValue = String.format("%.2f", sensorResponse.getCo());

                    return "\nSensor response from 「" + sensorResponse.getLocation() + "」:" +
                            "\n1. PM2.5 - " + pm25Value + " μg/m3" +
                            "\n2. Temperature - " + tempValue + " °C" +
                            "\n3. VOC - " + vocValue + " mg/m3" +
                            "\n4. Humidity - " + humidValue + " %" +
                            "\n5. CO - " + coValue + " ppm";
                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in getting sensor data: " + throwable.getMessage());
                    System.err.println("Please check your network connection and try again.");
                    callback.accept("Error occurred while getting sensor data: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Getting sensor data is completed");
                }
            });
        } catch (StatusRuntimeException e) {
            // catch more specific exceptions
            Status status = e.getStatus();
            System.err.println("Error in gRPC call to get sensor data: " + status.getCode() + ": " + status.getDescription());
            System.err.println("Please try again later or contact support for assistance.");
        } catch (Exception e) {
            System.err.println("Unexpected error in getting sensor data: " + e.getMessage());
            System.err.println("Please try again later or contact support for assistance.");
        }
    }

    // Analyse sensor data
    public void AnalyseSensorData(String analyseOrNot, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<AnalyseResponse> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(AnalyseResponse analyseResponse) {
                // convert to standard time format
                Date updatedTime = new Date(analyseResponse.getTimestamp().getSeconds() * 1000);
                // Process each AnalyseResponse received
                String analyseData = "\nAnalyse Response: " +
                        "\n1. Location: 「" + analyseResponse.getLocation() + "」" +
                        "\n2. Polluted item(s): " + analyseResponse.getAnalyse() +
                        "\n3. Pollution Level: " + analyseResponse.getPollutionLevel() +
                        "\n4. Analyse: " + analyseResponse.getMessage() +
                        "\n5. Updated time: " + updatedTime;
                pollution_level = analyseResponse.getPollutionLevel(); // store pollution level
                callback.accept(analyseData);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in analysing sensor data: " + throwable.getMessage());
                callback.accept("Error occurred while analysing sensor data: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                // Stream completed, perform any necessary cleanup or finalization
                System.out.println("Analysing sensor data is completed");
            }
        };

        StreamObserver<SensorResponse> requestObserver = sensorStub.analyseSensorData(responseObserver);

        // send the sensor id to the server to start analysing data for that sensor
        requestObserver.onNext(SensorResponse.newBuilder().setLocation(location).build());
        requestObserver.onCompleted();
    }

    // extract the common functionality of handling HVAC command responses into this method
    private void handleHVACCommandResponse(HvacCommand hvacCommand, int control, MessagePassingQueue.Consumer<String> callback) {
        Date updatedTime = new Date(hvacCommand.getUpdatedTime().getSeconds() * 1000);
        HvacCommand.Builder hvacCommandBuilder = HvacCommand.newBuilder();
        switch (control) {
            case 1:
                hvacCommandBuilder.setAction(HvacCommand.Action.START);
                break;
            case 2:
                hvacCommandBuilder.setAction(HvacCommand.Action.STOP);
                break;
            default:
                hvacCommandBuilder.setAction(hvacCommand.getAction());
                break;
        }
        HvacCommand updatedHvacCommand = hvacCommandBuilder.build();
        String hvacCommandMessage = "\nHVAC Command: " +
                "\n1. HVAC is: " + updatedHvacCommand.getAction() +
                "\n2. Pollution level: " + pollution_level +
                "\n3. Last status changing time: " + hvac_switch_updatedTime +
                "\n4. Time: " + updatedTime;
        action = updatedHvacCommand.getAction();
        callback.accept(hvacCommandMessage);
    }

    // Get HVAC status
    public void HVACControl(int control, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<HvacCommand> hvacCommandObserver = new StreamObserver<>() {
            @Override
            public void onNext(HvacCommand hvacCommand) {
                handleHVACCommandResponse(hvacCommand, control, callback);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error in HVAC Command: " + throwable.getMessage());
                callback.accept("Error occurred while checking HVAC status: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC Command completed");
            }
        };
        StreamObserver<HvacRequest> hvacRequestObserver = hvacStub.hvacControl(hvacCommandObserver);
        hvacRequestObserver.onNext(HvacRequest.newBuilder().setPollutionLevel(pollution_level).build());
        hvacRequestObserver.onCompleted();
    }

    // HVAC status switch
    public void HVACSwitch(int turn_on, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<HvacResponse> hvacResponseObserver = new StreamObserver<>() {
            @Override
            public void onNext(HvacResponse hvacResponse) {
                HvacResponse.Builder hvacResponseBuilder = HvacResponse.newBuilder();
                Date responseTime = new Date(hvacResponse.getUpdatedTime().getSeconds() * 1000);
                if (turn_on == 1) {
                    status = "ON";
                } else {
                    status = "OFF";
                }
                hvacResponseBuilder.setStatus(status);
                String hvacSwitchMessage = "\nHVAC Switch: " +
                        "\n1. HVAC is changed to: " + status +
                        "\n2. Pollution level: " + hvacResponse.getPollutionLevel() +
                        "\n3. Time: " + responseTime;
                status = hvacResponseBuilder.getStatus();
                pollution_level = hvacResponse.getPollutionLevel();
                hvac_switch_updatedTime = responseTime;
                callback.accept(hvacSwitchMessage);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Error from HVAC switch: " + throwable.getMessage());
                callback.accept("Error occurred while switching HVAC status: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC Switch completed");
            }
        };

        StreamObserver<HvacCommand> hvacCommandObserver = hvacStub.hvacSwitch(hvacResponseObserver);
        try {
            HvacCommand hvacCommand = HvacCommand.newBuilder().setAction(action).build();
            hvacCommandObserver.onNext(hvacCommand);
        } catch (Exception e) {
            System.err.println("Error while sending HVAC Command messages: " + e.getMessage());
        }
        hvacResponseObserver.onCompleted();
    }

    // Get sensor notification
    public void sensorNotifications(int turn_on, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<SensorMessage> sensorObserver = new StreamObserver<>() {
            @Override
            public void onNext(SensorMessage sensorMessage) {
                if (pollution_level > 2) { // pollution level > 2 means HVAC is already on automatically
                    if (status == null) { // if HVAC status hasn't been changed by HVAC switch, HVAC should be on now
                        Date updatedTime = new Date(sensorMessage.getTimestamp().getSeconds() * 1000);
                        String sensorNotify = "\nSensor notifications from " + location + ": " +
                                "\n1. Air quality: " + sensorMessage.getAirQuality() +
                                "\n2. Pollution level: " + pollution_level +
                                "\n3. Alert: " + sensorMessage.getAlert() +
                                "\n4. Advice: " + sensorMessage.getAdvice() +
                                "\n5. Time: " + updatedTime;
                        callback.accept(sensorNotify);
                    } else {  // if HVAC status has been changed by HVAC switch
                        Date updatedTime = new Date(sensorMessage.getTimestamp().getSeconds() * 1000);
                        if (status.equalsIgnoreCase("OFF")){ // if HVAC status has been changed to 'OFF', alert user to turn on HVAC
                            SensorMessage.Builder sensorMessage1 = SensorMessage.newBuilder();
                            sensorMessage1.setAdvice("The air is harmed, and the HVAC is OFF now. Please turn on the HVAC.").build();
                            String sensorNotify = "\nSensor notifications from " + location + ": " +
                                    "\n1. Air quality: " + sensorMessage.getAirQuality() +
                                    "\n2. Pollution level: " + pollution_level +
                                    "\n3. Alert: " + sensorMessage.getAlert() +
                                    "\n4. Advice: " + sensorMessage1.getAdvice() +
                                    "\n5. Time: " + updatedTime;
                            callback.accept(sensorNotify);
                        } else {
                            String sensorNotify = "\nSensor notifications from " + location + ": " +
                                    "\n1. Air quality: " + sensorMessage.getAirQuality() +
                                    "\n2. Pollution level: " + pollution_level +
                                    "\n3. Alert: " + sensorMessage.getAlert() +
                                    "\n4. Advice: " + sensorMessage.getAdvice() +
                                    "\n5. Time: " + updatedTime;
                            callback.accept(sensorNotify);
                        }
                    }
                } else{ // pollution level <= 2 means pollution is not high, neither turn on/off is urgent
                    Date updatedTime = new Date(sensorMessage.getTimestamp().getSeconds() * 1000);
                    String sensorNotify = "\nSensor notifications from " + location + ": " +
                            "\n1. Air quality: " + sensorMessage.getAirQuality() +
                            "\n2. Pollution level: " + pollution_level +
                            "\n3. Alert: " + sensorMessage.getAlert() +
                            "\n3. Advice: " + sensorMessage.getAdvice() +
                            "\n4. Time: " + updatedTime;
                    callback.accept(sensorNotify);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in sensor notifications streaming: " + throwable.getMessage());
                callback.accept("Error occurred while getting sensor notifications: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sensor Notification is completed");
            }
        };
        notificationStub.sensorNotifications(AnalyseResponse.newBuilder().setPollutionLevel(pollution_level).build(), sensorObserver);
    }

    // Get HVAC notification
    public void hvacNotifications(MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<HvacMessage> hvacObserver = new StreamObserver<>() {
            @Override
            public void onNext(HvacMessage hvacMessage) {
                if (status != null) { // status is not null means HVAC status has been changed by HVAC switch
                    HvacMessage.Builder hvacMessage1 = HvacMessage.newBuilder();
                    if (status.equalsIgnoreCase("ON")) { // if HVAC status has been changed to 'ON'
                        turn_on = true; // set 'true' to status
                        hvacMessage1.setMessage(status).setStatus(turn_on);
                    } else { // if HVAC status has been changed to 'OFF'
                        turn_on = false; // set 'false' to status
                        hvacMessage1.setMessage(status).setStatus(turn_on);
                    }
                    String hvacNotify = "\nHVAC Notifications: " +
                            "\n1. HVAC status: " + hvacMessage1.getStatus() +
                            "\n2. Message: " + hvacMessage1.getMessage();
                    callback.accept(hvacNotify);
                } else { // if HVAC status hasn't been changed by HVAC switch
                    String hvacNotify = "\nHVAC Notifications: " +
                            "\n1. HVAC status: " + hvacMessage.getStatus() +
                            "\n2. Message: " + hvacMessage.getMessage();
                    callback.accept(hvacNotify);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in HVAC notifications streaming: " + throwable.getMessage());
                callback.accept("Error occurred while getting HVAC notifications: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("HVAC Notification is completed");
            }
        };
        notificationStub.hvacNotifications(HvacResponse.newBuilder().setPollutionLevel(1).build(), hvacObserver);
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 9090;
        String consulServiceName = "AirPollutionService";
        Scanner keyboard = new Scanner(System.in);

        // catch any unexpected exceptions that may occur during the initialization of AirPollutionClient
        try {
            AirPollutionClient airPollutionClient = new AirPollutionClient(host, port, consulServiceName);

            // wait for user input to continue request or stop streaming
            while (true) {
                System.out.println("""
                        Enter 1-3 to get sensor data, 'q' to quit:
                        1. Sensor 1: Bedroom
                        2. Sensor 2: Living room
                        3. Sensor 3: Karaoke room""");
                if (keyboard.hasNextInt()) { // if user enter an integer
                    int sensor_id = keyboard.nextInt();
                    if (sensor_id > 0 && sensor_id < 4) { // if user enter between 1-3
                        airPollutionClient.GetSensorData(sensor_id, System.out::println);// pass user enter to get sensor data adn print sensor data
                        System.out.println("Enter 'yes' to analyse sensor data, 'q' to quit:");
                        String if_analyse = keyboard.next(); // allow user entering strings to confirm that analyse sensor data
                        while (if_analyse.equalsIgnoreCase("yes")) { // if user confirm to analyse sensor data
                            // print sensor data analysis
                            airPollutionClient.AnalyseSensorData(if_analyse, System.out::println);
                            System.out.println("Enter 'yes' to check HVAC status, 'q' to quit:"); // allow user entering strings to confirm that getting HVAC status
                            if (keyboard.hasNext()) {
                                String input = keyboard.next();
                                if (input.equalsIgnoreCase("yes")) { // if user confirm to check HVAC status
                                    // print HVAC status
                                    airPollutionClient.HVACControl(0, System.out::println);
                                    System.out.println("Enter '1' to turn on, '2' to turn off, 'q' to quit:"); // allow user entering integers to turn on HVAC or not
                                    String turn_on = keyboard.next();
                                    // pass user choice to switch HVAC, '1' for turning on, '2' for turning off
                                    // print HVAC switch result
                                    if (turn_on.equalsIgnoreCase("1") || turn_on.equalsIgnoreCase("2")) {
                                        airPollutionClient.HVACSwitch(Integer.parseInt(turn_on), System.out::println);
                                        // pass the same rules of switching HVAC to update HVAC status
                                        // print HVAC latest status
                                        airPollutionClient.HVACControl(Integer.parseInt(turn_on), System.out::println);
                                    } else if (turn_on.equalsIgnoreCase("q")) {
                                        break;
                                    }
                                    System.out.println("Enter '1' to turn on the sensor and HVAC notifications, 'q' to go back to enter a new sensor id:"); // allow user entering integers to turn on notifications or not
                                    String turnOnNotify = keyboard.next();
                                    if (turnOnNotify.equalsIgnoreCase("q")) {
                                        break;
                                    } else if (Integer.parseInt(turnOnNotify) == 1) { // if user want to turn on notifications
                                        // print sensor notifications
                                        airPollutionClient.sensorNotifications(Integer.parseInt(turnOnNotify), System.out::println);
                                        // print HVAC notifications
                                        airPollutionClient.hvacNotifications(System.out::println);
                                    }
                                } else if (input.equalsIgnoreCase("q")) {
                                    break;
                                }
                            }
                        }
                    } else { // throw an error message when user enter invalid sensor id(not 1-3)
                        System.out.println("Invalid sensor id. Please enter a number between 1 and 3.");
                    }
                } else { // if user enter strings
                    String input = keyboard.next();
                    if (input.equalsIgnoreCase("q")) { // if user enter 'q', then quit the application
                        System.out.println("Are you sure you want to quit?(yes/other)");
                        String confirmQuit = keyboard.next();
                        if(confirmQuit.equalsIgnoreCase("yes")) {
                            airPollutionClient.shutdown();
                            break;
                        }
                    } else {
                        continue;
                    }
                }
            }
            // shutdown client, quit the application
            airPollutionClient.shutdown();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            System.err.println("Please try again or contact support for assistance.");
        }
    }
}
