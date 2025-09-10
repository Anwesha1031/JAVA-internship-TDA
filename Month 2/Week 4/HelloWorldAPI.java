import java.io.*;

public class HelloWorldAPI {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your name: ");
            String name = br.readLine();

            String response = "Hello, " + name + "! Welcome to the Simple API Simulation.";
            System.out.println("API Response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
