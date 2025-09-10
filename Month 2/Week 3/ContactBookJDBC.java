import java.io.*;
import java.sql.*;

public class ContactBookJDBC {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Change DB details
        String url = "jdbc:mysql://localhost:3306/contactdb"; // PostgreSQL: jdbc:postgresql://localhost:5432/contactdb
        String user = "root";
        String pass = "password";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✅ Connected to Contact Book DB!");

            while (true) {
                System.out.println("\n1.Add Contact  2.View Contacts  3.Update Contact  4.Delete Contact  5.Exit");
                int choice = Integer.parseInt(br.readLine());

                if (choice == 1) {
                    System.out.print("Enter Name: ");
                    String name = br.readLine();

                    System.out.print("Enter Phone: ");
                    String phone = br.readLine();

                    System.out.print("Enter Email: ");
                    String email = br.readLine();

                    PreparedStatement ps = con.prepareStatement("INSERT INTO contacts (name, phone, email) VALUES (?,?,?)");
                    ps.setString(1, name);
                    ps.setString(2, phone);
                    ps.setString(3, email);
                    ps.executeUpdate();
                    System.out.println("✅ Contact Added!");

                } else if (choice == 2) {
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM contacts");

                    System.out.println("\n📒 Contact List:");
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + ". " +
                                           rs.getString("name") + " | " +
                                           rs.getString("phone") + " | " +
                                           rs.getString("email"));
                    }

                } else if (choice == 3) {
                    System.out.print("Enter Contact ID to update: ");
                    int id = Integer.parseInt(br.readLine());

                    System.out.print("Enter new Phone: ");
                    String phone = br.readLine();

                    PreparedStatement ps = con.prepareStatement("UPDATE contacts SET phone=? WHERE id=?");
                    ps.setString(1, phone);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                    System.out.println("✅ Contact Updated!");

                } else if (choice == 4) {
                    System.out.print("Enter Contact ID to delete: ");
                    int id = Integer.parseInt(br.readLine());

                    PreparedStatement ps = con.prepareStatement("DELETE FROM contacts WHERE id=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    System.out.println("✅ Contact Deleted!");

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
