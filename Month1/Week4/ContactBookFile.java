import java.io.*;
import java.util.*;

public class ContactBookFile {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String filename = "contacts.txt";

        while (true) {
            System.out.println("\nContact Book Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Show All Contacts");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = reader.readLine();
                    System.out.print("Enter phone: ");
                    String phone = reader.readLine();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                        writer.write(name + "," + phone);
                        writer.newLine();
                        System.out.println("Contact saved.");
                    } catch (IOException e) {
                        System.out.println("Error writing to file.");
                    }
                    break;
                case "2":
                    try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
                        String line;
                        System.out.println("Contacts:");
                        while ((line = fileReader.readLine()) != null) {
                            String[] parts = line.split(",", 2);
                            if (parts.length == 2) {
                                System.out.println("Name: " + parts[0] + ", Phone: " + parts[1]);
                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("No contacts found.");
                    } catch (IOException e) {
                        System.out.println("Error reading file.");
                    }
                    break;
                case "3":
                    System.out.println("Exiting.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
