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
    private final HVACGrpc.HVACStub hvacStub;
    private final NotificationGrpc.NotificationStub notificationStub;
    private static String location;
    private static int pollution_level;
    private static HvacCommand.Action action;
    private static Date hvac_switch_updatedTime;
    private static String status = null;
    private static boolean turn_on;

    public AirPollutionClient(String host, int port, String consulServiceName) {
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

    // Get sensor data, allow entering an id to query sensor data
    public void GetSensorData(int sensor_id, MessagePassingQueue.Consumer<String> callback) {
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
            }

            @Override
            public void onCompleted() {
                System.out.println("Getting sensor data is completed");
            }
        });
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

    // Get HVAC status
    public void HVACControl(int control, MessagePassingQueue.Consumer<String> callback) {
        StreamObserver<HvacCommand> hvacCommandObserver = new StreamObserver<>() {
            @Override
            public void onNext(HvacCommand hvacCommand) {
                Date updatedTime = new Date(hvacCommand.getUpdatedTime().getSeconds() * 1000);
                // variable 'control' is for getting the latest status by HVAC switch
                if (control == 1) { // if HVAC switch already turn on the HVAC('1' as in turning on HVAC in HVACSwitch method)
                    HvacCommand.Builder hvacCommand1 = HvacCommand.newBuilder();
                    hvacCommand1.setAction(HvacCommand.Action.START).build(); // set action to 'START'
                    String hvacCommandMessage = "\nHVAC Command: " +
                            "\n1. HVAC is: " + hvacCommand1.getAction() +
                            "\n2. Pollution level: " + pollution_level +
                            "\n3. Last status changing time: " + hvac_switch_updatedTime +
                            "\n4. Time: " + updatedTime;
                    action = hvacCommand1.getAction(); // update the latest action
                    callback.accept(hvacCommandMessage);
                } else if (control == 2) { // if HVAC switch already turn off the HVAC('2' as in turning off HVAC in HVACSwitch method)
                    HvacCommand.Builder hvacCommand1 = HvacCommand.newBuilder();
                    hvacCommand1.setAction(HvacCommand.Action.STOP).build(); // set action to 'STOP'
                    String hvacCommandMessage = "\nHVAC Command: " +
                            "\n1. HVAC is: " + hvacCommand1.getAction() +
                            "\n2. Pollution level: " + pollution_level +
                            "\n3. Last status changing time: " + hvac_switch_updatedTime +
                            "\n4. Time: " + updatedTime;
                    action = hvacCommand1.getAction(); // update the latest action
                    callback.accept(hvacCommandMessage);
                } else if (control == 0) { // if HVAC switch hasn't changed the status
                    String hvacCommandMessage = "\nHVAC Command: " +
                            "\n1. HVAC is: " + hvacCommand.getAction() +
                            "\n2. Pollution level: " + pollution_level +
                            "\n3. Last status changing time: " + hvac_switch_updatedTime +
                            "\n4. Time: " + updatedTime;
                    action = hvacCommand.getAction(); // update the latest action
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
        StreamObserver<HvacRequest> hvacRequestObserver = hvacStub.hvacControl(hvacCommandObserver);

        hvacRequestObserver.onNext(HvacRequest.newBuilder().setPollutionLevel(pollution_level).build());
        hvacRequestObserver.onCompleted();
    }

    // Switch HVAC status
    public void HVACSwitch(int turn_on, MessagePassingQueue.Consumer<String> callback) {

        // Create a response observer for the server streaming
        StreamObserver<HvacResponse> hvacResponseObserver = new StreamObserver<>() {
            @Override
            public void onNext(HvacResponse hvacResponse) {
                HvacResponse.Builder  hvacResponse1 = HvacResponse.newBuilder();
                Date responseTime = new Date(hvacResponse.getUpdatedTime().getSeconds() * 1000);

                // allow use to enter '1' for turning on, '2' for turning off
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

                status = hvacResponse1.getStatus(); // update the latest status
                pollution_level = hvacResponse.getPollutionLevel();
                hvac_switch_updatedTime = responseTime; // store HVAC status changing time
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
        StreamObserver<HvacCommand> hvacCommandObserver = hvacStub.hvacSwitch(hvacResponseObserver);
        try {
            HvacCommand hvacCommand = HvacCommand.newBuilder()
                    .setAction(action)
                    .build();
            hvacCommandObserver.onNext(hvacCommand);
        }catch (Exception e) {
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
                System.out.println("Error in server streaming: " + throwable.getMessage());
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
                System.out.println("Error in HVAC notification streaming: " + throwable.getMessage());
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
                    // pass user enter to get sensor data
                    // print sensor data
                    airPollutionClient.GetSensorData(sensor_id, System.out::println);
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
                    System.out.println("Invalid number.");
                }
            } else { // if user enter strings
                String input = keyboard.next();
                if (input.equalsIgnoreCase("q")) { // if user enter 'q', then quit the application
                    break;
                }
            }
        }
        // shutdown client, quit the application
        airPollutionClient.shutdown();
    }
}
