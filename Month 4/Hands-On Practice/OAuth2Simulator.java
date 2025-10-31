import java.io.*;
import java.util.*;

public class OAuth2Simulator {
    static Map<String, String> tokens = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("OAuth2 Protected API Simulation");
        while (true) {
            System.out.println("1) Generate Access Token  2) Access Resource  3) Exit");
            String choice = br.readLine();
            if (choice.equals("1")) {
                System.out.print("Client ID: ");
                String clientId = br.readLine();
                String token = Base64.getEncoder().encodeToString((clientId + ":" + UUID.randomUUID()).getBytes());
                tokens.put(token, clientId);
                System.out.println("Generated Token: " + token);
            } else if (choice.equals("2")) {
                System.out.print("Enter Access Token: ");
                String token = br.readLine();
                if (tokens.containsKey(token))
                    System.out.println("✅ Access granted to resource for client: " + tokens.get(token));
                else
                    System.out.println("❌ Invalid or expired token");
            } else break;
        }
    }
}

