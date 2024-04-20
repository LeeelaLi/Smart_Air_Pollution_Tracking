import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AirPollutionClientGUI extends JFrame {
    private JTextField getDataField;
    private JTextField analyseDataField;
    private JTextField hvacSwitchField;
    private JButton getDataButton;
    private JButton analyseDataButton;
    private JButton hvacSwitchButton;
    private JButton hvacControlButton;
    private JButton sensorNotificationsButton;
    private JButton hvacNotificationButton;
    private JTextArea outputArea;
    private static String if_get_data = null;
    private static String if_analyse_data = null;
    private static String if_hvac_control = null;
    private static String if_hvac_switch = null;
    private static int switchInput = 0;

    private final AirPollutionClient airPollutionClient;

    public AirPollutionClientGUI(String host, int port, String consulServiceName) {
        airPollutionClient = new AirPollutionClient(host, port, consulServiceName);

        setTitle("Air Pollution Tracking System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        addComponents();

        setVisible(true);
    }

    private void initComponents() {

        // Sensor service
        getDataField = new JTextField(15);
        getDataButton = new JButton("Get Sensor Data");
        analyseDataField = new JTextField(15);
        analyseDataButton = new JButton("Analyse Sensor Data");

        // HVAC service
        hvacControlButton = new JButton("HVAC Control");
        hvacSwitchField = new JTextField(15);
        hvacSwitchButton = new JButton("HVAC Switch");

        // Notification service
        sensorNotificationsButton = new JButton("Sensor Notifications");
        hvacNotificationButton = new JButton("HVAC Notifications");

        outputArea = new JTextArea(25, 30);
        outputArea.setEditable(false);

        getDataButton.addActionListener(e -> {
            if_analyse_data = null;

            String analyseDataStr = getDataField.getText();
            try {
                int sensorId = Integer.parseInt(analyseDataStr);
                airPollutionClient.GetSensorData(sensorId, sensorData -> {
                    SwingUtilities.invokeLater(() -> outputArea.append("\n" + sensorData));
                    if_get_data = sensorData;
                });
            } catch (NumberFormatException numberFormatException) {
                outputArea.append("\nsensor id error.");
            }

        });

       analyseDataButton.addActionListener(e -> {
            String analyseDataStr = analyseDataField.getText();

            if (analyseDataStr.equalsIgnoreCase("yes")) {
                if (if_get_data != null) {
                    airPollutionClient.AnalyseSensorData(analyseDataStr, analyseData -> {
                        SwingUtilities.invokeLater(() -> outputArea.append("\n" + analyseData));
                        if_analyse_data = analyseData;
                    });
                } else {
                    outputArea.append("\nWarning: Sensor data is empty, please get data first.");
                }
            } else {
                outputArea.append("\nWarning: Invalid input, please try again.");
            }
        });

        hvacControlButton.addActionListener(e -> {
            if (if_analyse_data == null) {
                outputArea.append("\nSensor data analysis is empty. Please analyse data first.");
            }
            else if (if_hvac_switch == null) {
                airPollutionClient.HVACControl(0, hvacCommandMessage -> {
                    SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacCommandMessage));
                    if_hvac_control = "hvacCommandMessage";
                });
            } else if (switchInput != 0) {
                airPollutionClient.HVACControl(switchInput, hvacCommandMessage -> {
                    SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacCommandMessage));
                    if_hvac_control = "hvacCommandMessage";
                });
            } else {
                outputArea.append("\n\nHVAC status has been changed. Please get the latest message from HVAC switch.");
            }
        });

        hvacSwitchButton.addActionListener(e -> {
            String sensorIdStr = hvacSwitchField.getText();
            try {
                if (if_hvac_control == null) {
                    outputArea.append("\nHVAC control message is empty. Please get HVAC control message first.");
                } else {
                    switchInput = Integer.parseInt(sensorIdStr);
                    if (switchInput == 1 || switchInput == 2) {
                        airPollutionClient.HVACSwitch(switchInput, hvacSwitchMessage -> {
                            SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacSwitchMessage));
                            if_hvac_switch = "hvacSwitchMessage";
                        });
                    }else {
                        outputArea.append("\nInvalid number. It should be 1 or 2.");
                    }
                }
            } catch (NumberFormatException ex){
                outputArea.append("\nInvalid string. Please enter a valid integer.");
            }
        });

        sensorNotificationsButton.addActionListener(e -> {
            try {
                if (if_analyse_data == null) {
                    outputArea.append("\nEmpty sensor analyse data. Please get analyse data.");
                } else {
                    airPollutionClient.sensorNotifications(1, sensorNotify -> {
                        SwingUtilities.invokeLater(() -> outputArea.append("\n" + sensorNotify));
                    });
                }
            } catch (NumberFormatException ex) {
                outputArea.append("\nInvalid sensor ID. Please enter a valid integer.");
            }
        });

        hvacNotificationButton.addActionListener(e -> {
            if (if_hvac_switch == null) {
                outputArea.append("\nEmpty hvac switch data. Please get switch data.");
            } else {
                airPollutionClient.hvacNotifications(hvacNotify-> {
                    SwingUtilities.invokeLater(() -> outputArea.append("\n" + hvacNotify));
                });
            }
        });
    }

    private void addComponents() {

        // Add components to panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Sensor section
        JPanel sensorColumnPanel = new JPanel();
        sensorColumnPanel.setLayout(new BoxLayout(sensorColumnPanel, BoxLayout.Y_AXIS));

        sensorColumnPanel.add(new JLabel("Enter 1-3 to get sensor data:"));
        sensorColumnPanel.add(getDataField);
        sensorColumnPanel.add(getDataButton); // get sensor data
        sensorColumnPanel.add(new JLabel("Enter 'yes' to check sensor data:")); // analyse sensor data
        sensorColumnPanel.add(analyseDataField);
        sensorColumnPanel.add(analyseDataButton);
        sensorColumnPanel.setBorder(new EmptyBorder(10, 0, 0, 15));

        // HVAC section
        JPanel hvacColumnPanel = new JPanel();
        hvacColumnPanel.setLayout(new BoxLayout(hvacColumnPanel, BoxLayout.Y_AXIS));

        hvacColumnPanel.add(hvacControlButton);
        hvacColumnPanel.add(new JLabel("Turn on(1)/off(2):"));
        hvacColumnPanel.add(hvacSwitchField);
        hvacColumnPanel.add(hvacSwitchButton);
        hvacColumnPanel.setBorder(new EmptyBorder(10, 15, 0, 15)); // Add left and right padding

        // Notification section
        JPanel notificationColumnPanel = new JPanel();
        notificationColumnPanel.setLayout(new BoxLayout(notificationColumnPanel, BoxLayout.Y_AXIS));

        notificationColumnPanel.add(sensorNotificationsButton);
        notificationColumnPanel.add(hvacNotificationButton);
        notificationColumnPanel.setBorder(new EmptyBorder(10, 15, 0, 0)); // Add left padding

        inputPanel.add(sensorColumnPanel);
        inputPanel.add(hvacColumnPanel);
        inputPanel.add(notificationColumnPanel);

        JScrollPane inputScrollPane = new JScrollPane(inputPanel); // Wrap inputPanel in a JScrollPane
        inputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Enable horizontal scrolling

        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(actionPanel, BorderLayout.CENTER);
        mainPanel.add(outputScrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void appendOutput(String text) {
        outputArea.append(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AirPollutionClientGUI("localhost", 9090, "AirPollutionService"));
    }
}
