import java.io.*;
import java.sql.*;

public class JDBCD {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Change DB details as per your setup
        String url = "jdbc:mysql://localhost:3306/testdb"; // for PostgreSQL: jdbc:postgresql://localhost:5432/testdb
        String user = "root";   // change if needed
        String pass = "password";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✅ Connected to database!");

            while (true) {
                System.out.println("\nChoose an option: 1.Insert  2.Update  3.Delete  4.Exit");
                int choice = Integer.parseInt(br.readLine());

                if (choice == 1) {
                    System.out.print("Enter ID: ");
                    int id = Integer.parseInt(br.readLine());

                    System.out.print("Enter Name: ");
                    String name = br.readLine();

                    System.out.print("Enter Marks: ");
                    int marks = Integer.parseInt(br.readLine());

                    PreparedStatement ps = con.prepareStatement("INSERT INTO student VALUES (?,?,?)");
                    ps.setInt(1, id);
                    ps.setString(2, name);
                    ps.setInt(3, marks);
                    ps.executeUpdate();
                    System.out.println("✅ Record Inserted!");

                } else if (choice == 2) {
                    System.out.print("Enter ID to update: ");
                    int id = Integer.parseInt(br.readLine());

                    System.out.print("Enter new Marks: ");
                    int marks = Integer.parseInt(br.readLine());

                    PreparedStatement ps = con.prepareStatement("UPDATE student SET marks=? WHERE id=?");
                    ps.setInt(1, marks);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                    System.out.println("✅ Record Updated!");

                } else if (choice == 3) {
                    System.out.print("Enter ID to delete: ");
                    int id = Integer.parseInt(br.readLine());

                    PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE id=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    System.out.println("✅ Record Deleted!");

                } else {
                    System.out.println("Exiting...");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
