import java.io.*;
import java.util.*;

public class SimpleJWTAuthSystem {

    // Dummy user credentials (simulating a user in database)
    static final String USERNAME = "admin";
    static final String PASSWORD = "12345";

    // Variable to hold generated token (simulating JWT)
    static String jwtToken = null;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("\n===== SIMULATED JWT AUTHENTICATION SYSTEM =====");
                System.out.println("1. Login (Generate Token)");
                System.out.println("2. Access Secure Endpoint (Verify Token)");
                System.out.println("3. Logout (Invalidate Token)");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        loginAndGenerateToken(br);
                        break;
                    case 2:
                        accessSecureEndpoint(br);
                        break;
                    case 3:
                        logout();
                        break;
                    case 4:
                        System.out.println("Exiting... Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🟢 Login: Validate user and generate a fake JWT token
    static void loginAndGenerateToken(BufferedReader br) throws IOException {
        if (jwtToken != null) {
            System.out.println("✅ Already logged in. Token: " + jwtToken);
            return;
        }

        System.out.print("Enter Username: ");
        String inputUser = br.readLine();

        System.out.print("Enter Password: ");
        String inputPass = br.readLine();

        if (inputUser.equals(USERNAME) && inputPass.equals(PASSWORD)) {
            // Generate a fake JWT token (random alphanumeric)
            jwtToken = generateFakeJWTToken(inputUser);
            System.out.println("✅ Login successful!");
            System.out.println("🔑 Your JWT Token: " + jwtToken);
            System.out.println("⚠️ Keep this token safe — it will be needed for accessing secure endpoints.");
        } else {
            System.out.println("❌ Invalid username or password!");
        }
    }

    // 🔒 Secure Endpoint — accessible only if token is valid
    static void accessSecureEndpoint(BufferedReader br) throws IOException {
        if (jwtToken == null) {
            System.out.println("🚫 Access Denied! You must login and provide a valid token.");
            return;
        }

        System.out.print("Enter your JWT Token: ");
        String inputToken = br.readLine();

        if (inputToken.equals(jwtToken)) {
            System.out.println("✅ Token verified! Access granted to secure data.");
            System.out.println("Confidential Tasks:");
            System.out.println("- View system logs");
            System.out.println("- Manage user permissions");
            System.out.println("- Deploy cloud services");
        } else {
            System.out.println("❌ Invalid or expired token! Access denied.");
        }
    }

    // 🔵 Logout — remove current token (invalidate session)
    static void logout() {
        if (jwtToken != null) {
            jwtToken = null;
            System.out.println("✅ Logged out successfully. Token invalidated.");
        } else {
            System.out.println("No active session found!");
        }
    }

    // 🧩 Utility function — simulates JWT token creation
    static String generateFakeJWTToken(String username) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder("JWT-");
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            token.append(chars.charAt(rand.nextInt(chars.length())));
        }
        token.append("-").append(username);
        return token.toString();
    }
}
