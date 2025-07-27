import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ContactBookApp {
    public static void main(String[] args) throws IOException {
        ArrayList<ContactC> contacts = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n*** Contact Book Menu ***");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Search Contact");
            System.out.println("4. Show All Contacts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            String choice = reader.readLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = reader.readLine();
                    System.out.print("Enter phone: ");
                    String phone = reader.readLine();
                    contacts.add(new ContactC(name, phone));
                    System.out.println("Contact added.");
                    break;
                case "2":
                    System.out.print("Enter name to delete: ");
                    String delName = reader.readLine();
                    boolean removed = false;
                    Iterator<ContactC> it = contacts.iterator();
                    while (it.hasNext()) {
                        if (it.next().name.equalsIgnoreCase(delName)) {
                            it.remove();
                            removed = true;
                            System.out.println("Contact deleted.");
                            break;
                        }
                    }
                    if (!removed) {
                        System.out.println("Contact not found.");
                    }
                    break;
                case "3":
                    System.out.print("Enter name to search: ");
                    String searchName = reader.readLine();
                    boolean found = false;
                    for (ContactC c : contacts) {
                        if (c.name.equalsIgnoreCase(searchName)) {
                            c.display();
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Contact not found.");
                    }
                    break;
                case "4":
                    if (contacts.isEmpty()) {
                        System.out.println("No contacts to display.");
                    } else {
                        for (ContactC c : contacts) {
                            c.display();
                        }
                    }
                    break;
                case "5":
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
