import java.io.*;
import java.util.*;

class User {
    int id;
    String name;
    String email;

    User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

public class DatabaseSimulation {
    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<User> users = new ArrayList<>();
        loadUsersFromFile(users);

        int nextId = users.size() + 1;

        while (true) {
            System.out.println("\n===== DATABASE OPERATIONS (Simulated) =====");
            System.out.println("1. INSERT user");
            System.out.println("2. READ all users");
            System.out.println("3. UPDATE user");
            System.out.println("4. DELETE user");
            System.out.println("5. EXIT");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1: // INSERT
                    System.out.print("Enter name: ");
                    String name = br.readLine();
                    System.out.print("Enter email: ");
                    String email = br.readLine();
                    users.add(new User(nextId++, name, email));
                    saveUsersToFile(users);
                    System.out.println("✅ Record inserted successfully!");
                    break;

                case 2: // READ
                    System.out.println("\n--- All Users ---");
                    if (users.isEmpty()) {
                        System.out.println("No records found!");
                    } else {
                        for (User u : users) {
                            System.out.println("ID: " + u.id + " | Name: " + u.name + " | Email: " + u.email);
                        }
                    }
                    break;

                case 3: // UPDATE
                    System.out.print("Enter ID to update: ");
                    int uid = Integer.parseInt(br.readLine());
                    boolean updated = false;

                    for (User u : users) {
                        if (u.id == uid) {
                            System.out.print("Enter new name: ");
                            u.name = br.readLine();
                            System.out.print("Enter new email: ");
                            u.email = br.readLine();
                            saveUsersToFile(users);
                            System.out.println("✅ Record updated successfully!");
                            updated = true;
                            break;
                        }
                    }

                    if (!updated) System.out.println("❌ Record not found!");
                    break;

                case 4: // DELETE
                    System.out.print("Enter ID to delete: ");
                    int did = Integer.parseInt(br.readLine());
                    boolean removed = users.removeIf(u -> u.id == did);
                    if (removed) {
                        saveUsersToFile(users);
                        System.out.println("🗑️ Record deleted successfully!");
                    } else {
                        System.out.println("❌ Record not found!");
                    }
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    return;

                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    // Load users from a file (simulating a database read)
    static void loadUsersFromFile(ArrayList<User> users) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    int id = Integer.parseInt(data[0]);
                    users.add(new User(id, data[1], data[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing database found. Starting fresh...");
        }
    }

    // Save users to a file (simulating database write)
    static void saveUsersToFile(ArrayList<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                writer.write(u.id + "," + u.name + "," + u.email);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }
}
