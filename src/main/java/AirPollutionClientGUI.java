import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AirPollutionClientGUI extends JFrame {

    // Allow user to enter their choice
    private JTextField getDataField;
    private JTextField analyseDataField;
    private JTextField hvacSwitchField;
    // Allow user to click the button to implement corresponded function
    private JButton getDataButton;
    private JButton analyseDataButton;
    private JButton hvacSwitchButton;
    private JButton hvacControlButton;
    private JButton sensorNotificationsButton;
    private JButton hvacNotificationButton;
    private JButton quitButton;
    // Print the result
    private JTextArea outputArea;
    private static String if_get_data = null; // check if sensor data is successfully obtained
    private static String if_analyse_data = null; // check if sensor data is successfully analysed
    private static String if_hvac_control = null; // check if HVAC status is successfully obtained
    private static String if_hvac_switch = null; // check if HVAC status has been changed by HVAC switch
    private static int switchInput = 0; // store user input in HVAC switch method

    private final AirPollutionClient airPollutionClient;

    public AirPollutionClientGUI(String host, int port, String consulServiceName) {
        airPollutionClient = new AirPollutionClient(host, port, consulServiceName);

        setTitle("Air Pollution Tracking System"); // set up GUI title - project name
        setSize(800, 600); // set up GUI area
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        addComponents();

        setVisible(true);
    }

    private void initComponents() {

        // Sensor service
        getDataField = new JTextField(15);
        getDataButton = new JButton("Get Sensor Data"); // set get sensor data button name
        analyseDataField = new JTextField(15); // set up sensor id enter field
        analyseDataButton = new JButton("Analyse Sensor Data"); // set analyse sensor data button name

        // HVAC service
        hvacControlButton = new JButton("HVAC Control"); // set HVAC status button name
        hvacSwitchField = new JTextField(15); // set up HVAC switch enter field
        hvacSwitchButton = new JButton("HVAC Switch"); // set HVAC switch button name

        // Notification service
        sensorNotificationsButton = new JButton("Sensor Notifications"); // set sensor notification button name
        hvacNotificationButton = new JButton("HVAC Notifications"); // set HVAC notification button name
        quitButton = new JButton("Quit"); // set quit button name

        outputArea = new JTextArea(25, 30); // set up output area
        outputArea.setEditable(false);

        outputArea.append("""
                    1. Sensor 1: Bedroom
                    2. Sensor 2: Living room
                    3. Sensor 3: Karaoke room""");

        // Get sensor data
        getDataButton.addActionListener(e -> {
            if_get_data = null;  // reset sensor data every time making a sensor id new query
            if_analyse_data = null; // reset analysis every time making a sensor id new query

            String analyseDataStr = getDataField.getText();
            try {
                int sensorId = Integer.parseInt(analyseDataStr);
                if (sensorId > 0 && sensorId < 4) { // query a sensor id 1-3
                    airPollutionClient.GetSensorData(sensorId, sensorData -> { // pass entering number to query corresponded sensor id data
                        SwingUtilities.invokeLater(() -> outputArea.append("\n" + sensorData));
                        if_get_data = sensorData; // ensure sensor data query is successful
                    });
                } else {
                    outputArea.append("\nWarning: Sensor id should be between 1-3, please try again.");
                }
            } catch (NumberFormatException numberFormatException) {
                outputArea.append("\nWarning: Invalid string, please try again.");
            }
        });

        // Analyse sensor data
        analyseDataButton.addActionListener(e -> {
            String analyseDataStr = analyseDataField.getText();

            if (analyseDataStr.equalsIgnoreCase("yes")) {
                if (if_get_data != null) { // analyse data if sensor data query is successful
                    airPollutionClient.AnalyseSensorData(analyseDataStr, analyseData -> { // pass 'yes' to AnalyseSensorData method in Client
                        SwingUtilities.invokeLater(() -> outputArea.append("\n" + analyseData));
                        if_analyse_data = analyseData;  // ensure analyse data query is successful
                    });
                } else {
                    outputArea.append("\nWarning: Sensor data is empty, please get data first.");
                }
            } else {
                outputArea.append("\nWarning: Invalid input, please try again.");
            }
        });

        // Get HVAC status
        hvacControlButton.addActionListener(e -> {
            if (if_analyse_data == null) { // if there's no data analysis, unable to get pollution level to know the HVAC status
                outputArea.append("\nWarning: Sensor data analysis is empty. Please analyse data first.");
            }
            else if (if_hvac_switch == null) { // if HVAC status hasn't been changed by HVACSwitch
                airPollutionClient.HVACControl(0, hvacCommandMessage -> { // pass '0' to HVACControl method to get original hvacCommandMessage
                    SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacCommandMessage));
                    if_hvac_control = "hvacCommandMessage"; // ensure HVAC status is not null
                });
            } else if (switchInput != 0) { // if HVAC status has been changed by HVACSwitch
                airPollutionClient.HVACControl(switchInput, hvacCommandMessage -> { // pass switch number to HVACControl method to get final HVAC status
                    SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacCommandMessage));
                    if_hvac_control = "hvacCommandMessage"; // ensure HVAC status is not null
                });
            }
        });

        // Switch the HVAC button
        hvacSwitchButton.addActionListener(e -> {
            String sensorIdStr = hvacSwitchField.getText();
            try {
                if (if_hvac_control == null) { // if HVAC status is null
                    outputArea.append("\nWarning: HVAC control message is empty. Please get HVAC control message first.");
                } else {
                    switchInput = Integer.parseInt(sensorIdStr);
                    if (switchInput == 1 || switchInput == 2) { // 1 for turn on HVAC, 2 for turn off HVAC
                        airPollutionClient.HVACSwitch(switchInput, hvacSwitchMessage -> { // pass the switch number to HVACSwitch method
                            SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacSwitchMessage));
                            if_hvac_switch = "hvacSwitchMessage"; // ensure HVAC status changing is recorded
                        });
                    }else {
                        outputArea.append("\nWarning: Invalid number. It should be 1 or 2.");
                    }
                }
            } catch (NumberFormatException ex){
                outputArea.append("\nWarning: Invalid string. Please enter a valid integer.");
            }
        });

        // Get sensor notification
        sensorNotificationsButton.addActionListener(e -> {
            if (if_analyse_data == null) { // if didn't analyse data
                outputArea.append("\nWarning: Empty sensor analyse data. Please get analyse data.");
            } else {
                airPollutionClient.sensorNotifications(1, sensorNotify -> SwingUtilities.invokeLater(() -> outputArea.append("\n" + sensorNotify)));
            }
        });

        // Get HVAC notification
        hvacNotificationButton.addActionListener(e -> {
            if (if_hvac_control == null) { // if HVAC status is null
                outputArea.append("\nWarning: Empty HVAC data. Please get HVAC data.");
            } else {
                airPollutionClient.hvacNotifications(hvacNotify-> SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacNotify)));
            }
        });

        // Quit button
        quitButton.addActionListener(e -> {
            // terminate the application when quit button is clicked
            dispose(); // close the JFrame
            System.exit(0); // terminate the application
        });
    }

    private void addComponents() {

        // Add components to panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Sensor section
        JPanel sensorPanel = new JPanel();
        sensorPanel.setLayout(new BoxLayout(sensorPanel, BoxLayout.Y_AXIS)); // ensure items are aligned vertically

        sensorPanel.add(new JLabel("Enter 1-3 to get sensor data:"));
        sensorPanel.add(getDataField); // allow user to enter sensor id to get sensor data
        sensorPanel.add(getDataButton); // get sensor data
        sensorPanel.add(new JLabel("Enter 'yes' to check sensor data:"));
        sensorPanel.add(analyseDataField); // confirm that analyse sensor data
        sensorPanel.add(analyseDataButton); // analyse sensor data
        sensorPanel.setBorder(new EmptyBorder(10, 0, 0, 15)); // Add right padding

        // HVAC section
        JPanel hvacPanel = new JPanel();
        hvacPanel.setLayout(new BoxLayout(hvacPanel, BoxLayout.Y_AXIS)); // ensure items are aligned vertically

        hvacPanel.add(hvacControlButton); // get HVAC status
        hvacPanel.add(new JLabel("Turn on(1)/off(2):"));
        hvacPanel.add(hvacSwitchField);
        hvacPanel.add(hvacSwitchButton); // turn on/off HVAC
        hvacPanel.setBorder(new EmptyBorder(10, 15, 0, 15)); // Add left and right padding

        // Notification section
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS)); // ensure items are aligned vertically

        notificationPanel.add(sensorNotificationsButton); // turn on sensor notification
        notificationPanel.add(hvacNotificationButton); // turn on HVAC notification
        notificationPanel.add(quitButton);
        notificationPanel.setBorder(new EmptyBorder(10, 15, 0, 0)); // Add left padding

        inputPanel.add(sensorPanel);
        inputPanel.add(hvacPanel);
        inputPanel.add(notificationPanel);

        JScrollPane inputScrollPane = new JScrollPane(inputPanel); // wrap inputPanel in a JScrollPane
        inputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // enable horizontal scrolling when interface has been zoomed in or out

        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(actionPanel, BorderLayout.CENTER);
        mainPanel.add(outputScrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AirPollutionClientGUI("localhost", 9090, "AirPollutionService"));
    }
}
