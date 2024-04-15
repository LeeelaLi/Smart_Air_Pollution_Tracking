import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirPollutionClientGUI extends JFrame {
    private JTextField sensorIdField;
    private JButton getDataButton;
    private JButton hvacSwitchButton;
    private JButton sensorNotificationsButton;
    private JTextArea outputArea;

    private AirPollutionClient airPollutionClient;

    public AirPollutionClientGUI(String host, int port) {
        airPollutionClient = new AirPollutionClient(host, port);

        setTitle("Air Pollution Tracking System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        addComponents();

        setVisible(true);
    }

    private void initComponents() {
        sensorIdField = new JTextField(10);
        getDataButton = new JButton("Get Sensor Data");
        hvacSwitchButton = new JButton("HVAC Switch");
        sensorNotificationsButton = new JButton("Sensor Notifications");
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        getDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sensorIdStr = sensorIdField.getText();
                try {
                    int sensorId = Integer.parseInt(sensorIdStr);
                    if (sensorId >= 1 && sensorId <= 3) {
                        airPollutionClient.GetSensorData(sensorId);
                    } else {
                        outputArea.append("Invalid sensor ID. Sensor ID should be between 1 and 3.\n");
                    }
                } catch (NumberFormatException ex) {
                    outputArea.append("Invalid sensor ID. Please enter a valid integer.\n");
                }
            }
        });

        hvacSwitchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sensorIdStr = sensorIdField.getText();
                try {
                    int sensorId = Integer.parseInt(sensorIdStr);
                    if (sensorId == 1) {
                        airPollutionClient.HVACSwitch(sensorId);
                    } else if (sensorId == 2) {
                        airPollutionClient.HVACSwitch(sensorId);
                    }else {
                        outputArea.append("Invalid sensor ID. Sensor ID should be between 1 and 3.\n");
                    }
                }catch (NumberFormatException ex){
                    outputArea.append("Invalid sensor ID. Please enter a valid integer.\n");
                }
            }
        });

        sensorNotificationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                airPollutionClient.sensorNotifications();
            }
        });
    }

    private void addComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Sensor ID:"));
        inputPanel.add(sensorIdField);
        inputPanel.add(getDataButton);

        JPanel actionPanel = new JPanel();
        actionPanel.add(hvacSwitchButton);
        actionPanel.add(sensorNotificationsButton);

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
        SwingUtilities.invokeLater(() -> new AirPollutionClientGUI("localhost", 9090));
    }
}
