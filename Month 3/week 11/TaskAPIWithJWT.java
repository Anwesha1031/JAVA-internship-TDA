import java.io.*;
import java.util.*;

public class TaskAPIWithJWT {

    // Dummy credentials for user authentication
    static final String USERNAME = "admin";
    static final String PASSWORD = "12345";

    // Variable to store JWT token after login
    static String jwtToken = null;

    // Simple Task class to hold task data
    static class Task {
        int id;
        String title;
        String status;

        Task(int id, String title, String status) {
            this.id = id;
            this.title = title;
            this.status = status;
        }
    }

    // In-memory task list
    static ArrayList<Task> taskList = new ArrayList<>();
    static int nextId = 1;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("\n===== TASK API with JWT AUTHENTICATION =====");
                System.out.println("1. Login (Generate Token)");
                System.out.println("2. Create Task");
                System.out.println("3. View All Tasks");
                System.out.println("4. Update Task");
                System.out.println("5. Delete Task");
                System.out.println("6. Logout");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        loginAndGenerateToken(br);
                        break;
                    case 2:
                        if (verifyToken(br)) createTask(br);
                        break;
                    case 3:
                        if (verifyToken(br)) viewTasks();
                        break;
                    case 4:
                        if (verifyToken(br)) updateTask(br);
                        break;
                    case 5:
                        if (verifyToken(br)) deleteTask(br);
                        break;
                    case 6:
                        logout();
                        break;
                    case 7:
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

    // 🟢 Login and Generate Token
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
            jwtToken = generateFakeJWTToken(inputUser);
            System.out.println("✅ Login Successful!");
            System.out.println("🔑 Your Token: " + jwtToken);
        } else {
            System.out.println("❌ Invalid username or password!");
        }
    }

    // 🔒 Token verification before accessing secure API
    static boolean verifyToken(BufferedReader br) throws IOException {
        if (jwtToken == null) {
            System.out.println("🚫 Unauthorized! Please login first.");
            return false;
        }

        System.out.print("Enter your JWT Token: ");
        String inputToken = br.readLine();

        if (inputToken.equals(jwtToken)) {
            return true;
        } else {
            System.out.println("❌ Invalid Token! Access denied.");
            return false;
        }
    }

    // 🔐 Create Task (POST /tasks)
    static void createTask(BufferedReader br) throws IOException {
        System.out.print("Enter Task Title: ");
        String title = br.readLine();

        System.out.print("Enter Task Status (Pending/Completed): ");
        String status = br.readLine();

        Task t = new Task(nextId++, title, status);
        taskList.add(t);
        System.out.println("✅ Task Created Successfully! ID = " + t.id);
    }

    // 🔵 View All Tasks (GET /tasks)
    static void viewTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks available!");
            return;
        }
        System.out.println("\n--- All Tasks ---");
        for (Task t : taskList) {
            System.out.println("ID: " + t.id + " | Title: " + t.title + " | Status: " + t.status);
        }
    }

    // 🟡 Update Task (PUT /tasks/{id})
    static void updateTask(BufferedReader br) throws IOException {
        System.out.print("Enter Task ID to Update: ");
        int id = Integer.parseInt(br.readLine());

        for (Task t : taskList) {
            if (t.id == id) {
                System.out.print("Enter New Title: ");
                t.title = br.readLine();
                System.out.print("Enter New Status: ");
                t.status = br.readLine();
                System.out.println("✅ Task Updated Successfully!");
                return;
            }
        }
        System.out.println("❌ Task not found!");
    }

    // 🔴 Delete Task (DELETE /tasks/{id})
    static void deleteTask(BufferedReader br) throws IOException {
        System.out.print("Enter Task ID to Delete: ");
        int id = Integer.parseInt(br.readLine());

        Iterator<Task> it = taskList.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.id == id) {
                it.remove();
                System.out.println("🗑️ Task Deleted Successfully!");
                return;
            }
        }
        System.out.println("❌ Task not found!");
    }

    // 🔵 Logout — Invalidate Token
    static void logout() {
        if (jwtToken != null) {
            jwtToken = null;
            System.out.println("✅ Logged out successfully! Token invalidated.");
        } else {
            System.out.println("You are not logged in.");
        }
    }

    // 🧩 Generate a fake JWT Token (simulation)
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
