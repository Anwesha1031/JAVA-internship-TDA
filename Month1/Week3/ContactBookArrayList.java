import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class ContactBookArrayList {
    public static void main(String[] args) throws IOException {
        ArrayList<Contact> contacts = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\nContact Book");
            System.out.println("1. Add Contact");
            System.out.println("2. Show Contacts");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = reader.readLine();
                    System.out.print("Enter phone: ");
                    String phone = reader.readLine();
                    contacts.add(new Contact(name, phone));
                    System.out.println("Contact added.");
                    break;
                case 2:
                    if (contacts.isEmpty()) {
                        System.out.println("No contacts to display.");
                    } else {
                        for (Contact c : contacts) {
                            c.display();
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
