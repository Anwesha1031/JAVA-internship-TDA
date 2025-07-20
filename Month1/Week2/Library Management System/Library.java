import java.util.ArrayList;

class Library {
    ArrayList<Book> books;

    Library() {
        books = new ArrayList<>();
    }

    void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added to the library.");
    }

    void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            for (int i = 0; i < books.size(); i++) {
                System.out.print((i + 1) + ". ");
                books.get(i).displayInfo();
            }
        }
    }

    void borrowBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.get(index).borrowBook();
        } else {
            System.out.println("Invalid book number.");
        }
    }

    void returnBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.get(index).returnBook();
        } else {
            System.out.println("Invalid book number.");
        }
    }
}
