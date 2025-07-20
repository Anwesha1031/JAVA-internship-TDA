class Book {
    String title;
    String author;
    boolean isBorrowed;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    void borrowBook() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println("\"" + title + "\" has been borrowed.");
        } else {
            System.out.println("\"" + title + "\" is already borrowed.");
        }
    }

    void returnBook() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println("\"" + title + "\" has been returned.");
        } else {
            System.out.println("\"" + title + "\" was not borrowed.");
        }
    }

    void displayInfo() {
        System.out.println("Title: " + title + ", Author: " + author + ", Status: " +
            (isBorrowed ? "Borrowed" : "Available"));
    }
}
