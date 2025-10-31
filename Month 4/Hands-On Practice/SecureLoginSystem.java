import java.io.*;
import java.security.*;
import java.util.*;

public class SecureLoginSystem {
    static class User {
        String username;
        String salt;
        String hashedPassword;
        User(String u, String s, String h){ username=u; salt=s; hashedPassword=h; }
    }
    static Map<String, User> users = new HashMap<>();

    static String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\n1) Register  2) Login  3) Exit");
            String choice = br.readLine();
            if (choice.equals("1")) {
                System.out.print("Username: "); String u = br.readLine();
                System.out.print("Password: "); String p = br.readLine();
                String salt = UUID.randomUUID().toString();
                String hash = hashPassword(p, salt);
                users.put(u, new User(u, salt, hash));
                System.out.println("✅ Registered securely.");
            } else if (choice.equals("2")) {
                System.out.print("Username: "); String u = br.readLine();
                System.out.print("Password: "); String p = br.readLine();
                User user = users.get(u);
                if (user != null && user.hashedPassword.equals(hashPassword(p, user.salt)))
                    System.out.println("✅ Login successful!");
                else System.out.println("❌ Invalid credentials");
            } else break;
        }
    }
}
