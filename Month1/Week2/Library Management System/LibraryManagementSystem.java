import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class LibraryManagementSystem {
    public static void main(String[] args) throws IOException {
        Library library = new Library();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Show All Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(reader.readLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = reader.readLine();
                    System.out.print("Enter author name: ");
                    String author = reader.readLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    library.displayBooks();
                    break;
                case 3:
                    library.displayBooks();
                    System.out.print("Enter book number to borrow: ");
                    int borrowIndex = Integer.parseInt(reader.readLine()) - 1;
                    library.borrowBook(borrowIndex);
                    break;
                case 4:
                    library.displayBooks();
                    System.out.print("Enter book number to return: ");
                    int returnIndex = Integer.parseInt(reader.readLine()) - 1;
                    library.returnBook(returnIndex);
                    break;
                case 5:
                    System.out.println("Exiting system.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
