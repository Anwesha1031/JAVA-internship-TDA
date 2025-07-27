import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

public class FileBasedContactBook {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filename = "contacts.txt";

        while (true) {
            System.out.println("\n--- Contact Book ---");
            System.out.println("1. Add Contact");
            System.out.println("2. Show Contacts");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter name: ");
                        String name = reader.readLine();

                        System.out.print("Enter phone: ");
                        String phone = reader.readLine();

                        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
                        writer.write(name + "," + phone);
                        writer.newLine();
                        writer.close();

                        System.out.println("✅ Contact saved!");
                        break;

                    case "2":
                        File file = new File(filename);
                        if (!file.exists() || file.length() == 0) {
                            System.out.println("📄 No contacts found.");
                        } else {
                            System.out.println("\n📇 Contact List:");
                            BufferedReader fileReader = new BufferedReader(new FileReader(filename));
                            String line;
                            while ((line = fileReader.readLine()) != null) {
                                String[] parts = line.split(",", 2);
                                if (parts.length == 2) {
                                    System.out.println("● Name: " + parts[0] + ", Phone: " + parts[1]);
                                }
                            }
                            fileReader.close();
                        }
                        break;

                    case "3":
                        System.out.println("👋 Exiting Contact Book. Goodbye!");
                        return;

                    default:
                        System.out.println("⚠️ Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }
}
