import com.chuntao.service.*;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.model.HealthService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class AirPollutionClient {
    private final ConsulClient consulClient;
    private final String consulServiceName;
    private static int sensor_id;

    public AirPollutionClient(String consulHost, int consulPort, String consulServiceName) {
        this.consulClient = new ConsulClient(consulHost, consulPort);
        this.consulServiceName = consulServiceName;
    }

    public void makeUnaryRequest() {
        // Lookup service details from Consul
        List<HealthService> healthServices = consulClient.getHealthServices(consulServiceName, true, null).getValue();
        if (healthServices.isEmpty()) {
            System.err.println("No healthy instances of " + consulServiceName + " found in Consul.");
            return;
        }

        // Pick the first healthy instance (you can implement a load balancing strategy here)
        HealthService healthService = healthServices.get(0);

        // Debug output for service details
        System.out.println("Service details from Consul:");
        System.out.println("Service ID: " + healthService.getService().getId());
        System.out.println("Service Name: " + healthService.getService().getService());
        System.out.println("Service Address: " + healthService.getService().getAddress());
        System.out.println("Service Port: " + healthService.getService().getPort());

        // Extract host and port from the service details
        String serverHost = healthService.getService().getAddress();
        int serverPort = healthService.getService().getPort();

        // Debug output for extracted host and port
        System.out.println("Server host: " + serverHost);
        System.out.println("Server port: " + serverPort);

        // Create a gRPC channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();

        // Create a stub for the gRPC service
        SensorGrpc.SensorBlockingStub stub = SensorGrpc.newBlockingStub(channel);

        // Prepare and send the unary request based on sensor id 1-3
        System.out.println("Enter the sensor id you want to review:");
        Scanner keyboard = new Scanner(System.in);
        sensor_id = keyboard.nextInt();

        SensorRequest request = SensorRequest.newBuilder().setSensorId(sensor_id).build();
        SensorResponse response = stub.getSensorData(request);

        // Process the response
        System.out.println("Unary response from server: " +
                "\n1. Sensor id: " + request.getSensorId() +
                "\n2. Location: " + response.getLocation());

        // Shutdown the channel when done
        channel.shutdown();
    }

    public void makeAnalyseRequest() {
        // Lookup service details from Consul
        List<HealthService> healthServices = consulClient.getHealthServices(consulServiceName, true, null).getValue();
        if (healthServices.isEmpty()) {
            System.err.println("No healthy instances of " + consulServiceName + " found in Consul.");
            return;
        }

        // Pick the first healthy instance (you can implement a load balancing strategy here)
        HealthService healthService = healthServices.get(0);

        // Debug output for service details
        System.out.println("Service details from Consul:");
        System.out.println("Service ID: " + healthService.getService().getId());
        System.out.println("Service Name: " + healthService.getService().getService());
        System.out.println("Service Address: " + healthService.getService().getAddress());
        System.out.println("Service Port: " + healthService.getService().getPort());

        // Extract host and port from the service details
        String serverHost = healthService.getService().getAddress();
        int serverPort = healthService.getService().getPort();

        // Debug output for extracted host and port
        System.out.println("Server host: " + serverHost);
        System.out.println("Server port: " + serverPort);

        // Create a gRPC channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();

        // Create a stub for the gRPC service
        SensorGrpc.SensorBlockingStub stub = SensorGrpc.newBlockingStub(channel);

        // Prepare and send the analysis request
        AnalyseRequest.Builder request = AnalyseRequest.newBuilder();
        if (sensor_id == 1) {
            request.setPM25(10).setTemperature(19).setVOC(10.3F).setHumidity(36).setCO(18).build();
        } else if (sensor_id == 2) {
            request.setPM25(10).setTemperature(29).setVOC(20.3F).setHumidity(46).setCO(28).build();
        } else if (sensor_id == 3) {
            request.setPM25(10).setTemperature(39).setVOC(30.3F).setHumidity(56).setCO(38).build();
        } else {
            request.setPM25(0).setTemperature(0).setVOC(0).setHumidity(0).setCO(0).build();
        }

        AnalyseRequest request1 = request.build();
        Iterator<AnalyseResponse> responseIterator = stub.analyseSensorData(request1);
        while (responseIterator.hasNext()) {
            AnalyseResponse response = responseIterator.next();
        }

        // Process the response
        String getPM25 = String.format("%.2f", request.getPM25());
        String getTemp = String.format("%.2f", request.getTemperature());
        String getVOC = String.format("%.2f", request.getVOC());
        String getHumid = String.format("%.2f", request.getHumidity());
        String getCO = String.format("%.2f", request.getCO());
        System.out.println("Analyse request from client:" +
                "\n1. pm2.5: " + getPM25 +
                "\n2. Temperature: " + getTemp +
                "\n3. VOC: " + getVOC +
                "\n4. Humidity: " + getHumid +
                "\n5. CO: " + getCO
                );

        System.out.println("Analyse response from server:" +
                "\n1. ");

        // Shutdown the channel when done
        channel.shutdown();
    }

    public static void main(String[] args) {
        String consulHost = "localhost"; // Consul host
        int consulPort = 8500; // Consul port
        String consulServiceName = "air-pollution-tracking-service"; // Name of the service registered in Consul

        AirPollutionClient client = new AirPollutionClient(consulHost, consulPort, consulServiceName);
        client.makeUnaryRequest();
//        client.makeAnalyseRequest();
    }
}
