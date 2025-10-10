import java.io.*;
import java.util.*;

public class HttpRequestHandler {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> users = new ArrayList<>();

        while (true) {
            System.out.println("\n=== HTTP Request & Response Simulation ===");
            System.out.println("Choose an HTTP method:");
            System.out.println("1. GET /users");
            System.out.println("2. POST /users");
            System.out.println("3. EXIT");
            System.out.print("Enter your choice: ");
            String choice = br.readLine();

            switch (choice.toLowerCase()) {
                case "get /users":
                    System.out.println("HTTP 200 OK");
                    if (users.isEmpty()) {
                        System.out.println("Response Body: No users found.");
                    } else {
                        System.out.println("Response Body: User List → " + users);
                    }
                    break;

                case "post /users":
                    System.out.print("Enter username to add: ");
                    String username = br.readLine();
                    users.add(username);
                    System.out.println("HTTP 201 Created");
                    System.out.println("Response Body: User '" + username + "' added successfully.");
                    break;

                case "exit":
                    System.out.println("HTTP 200 OK");
                    System.out.println("Response Body: Server shutting down...");
                    return;

                default:
                    System.out.println("HTTP 400 Bad Request");
                    System.out.println("Response Body: Invalid HTTP request!");
            }
        }
    }
}
