import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SensorClientGUI extends JFrame {
    private JTextField hostField;
    private JTextField portField;
    private JButton connectButton;
    private JComboBox<String> sensorComboBox;
    private JTextArea outputTextArea;

    private AirPollutionClient airPollutionClient;

    public SensorClientGUI() {
        setTitle("Sensor Client GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel hostLabel = new JLabel("Host:");
        hostField = new JTextField("localhost", 10);
        JLabel portLabel = new JLabel("Port:");
        portField = new JTextField("9090", 5);
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String host = hostField.getText();
                int port = Integer.parseInt(portField.getText());
                connect(host, port);
            }
        });

        topPanel.add(hostLabel);
        topPanel.add(hostField);
        topPanel.add(portLabel);
        topPanel.add(portField);
        topPanel.add(connectButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());

        sensorComboBox = new JComboBox<>(new String[]{"Sensor 1 - Home", "Sensor 2 - Garden", "Sensor 3 - Car"});
        centerPanel.add(sensorComboBox);

        outputTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void connect(String host, int port) {
        airPollutionClient = new AirPollutionClient(host, port);
        connectButton.setEnabled(false);
        outputTextArea.append("Connected to server.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SensorClientGUI().setVisible(true);
            }
        });
    }
}
