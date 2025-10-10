import java.io.*;
import java.time.LocalDateTime;

public class BasicRestSimulation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n=== Basic REST API Simulation ===");
            System.out.println("Available Endpoints:");
            System.out.println("1. GET /hello");
            System.out.println("2. GET /time");
            System.out.println("3. EXIT");
            System.out.print("Enter endpoint: ");
            String endpoint = br.readLine();

            // Handle simulated HTTP requests
            if (endpoint.equalsIgnoreCase("/hello")) {
                System.out.println("HTTP 200 OK");
                System.out.println("Response Body: Hello! Welcome to the REST API Simulation.");
            } 
            else if (endpoint.equalsIgnoreCase("/time")) {
                System.out.println("HTTP 200 OK");
                System.out.println("Response Body: Current Server Time → " + LocalDateTime.now());
            } 
            else if (endpoint.equalsIgnoreCase("/exit")) {
                System.out.println("HTTP 200 OK");
                System.out.println("Response Body: Exiting the API Simulation...");
                break;
            } 
            else {
                System.out.println("HTTP 404 Not Found");
                System.out.println("Response Body: The requested endpoint does not exist!");
            }
        }
    }
}
