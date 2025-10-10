import java.io.*;
import java.sql.*;
import java.util.*;

public class TaskManagementMySQL {
    // Database connection details
    static final String URL = "jdbc:mysql://localhost:3306/taskdb";
    static final String USER = "root";          // replace with your MySQL username
    static final String PASSWORD = "yourpassword"; // replace with your MySQL password

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("\n===== TASK MANAGEMENT API (MySQL) =====");
                System.out.println("1. Create Task");
                System.out.println("2. View All Tasks");
                System.out.println("3. Update Task");
                System.out.println("4. Delete Task");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        createTask(con, br);
                        break;
                    case 2:
                        viewTasks(con);
                        break;
                    case 3:
                        updateTask(con, br);
                        break;
                    case 4:
                        deleteTask(con, br);
                        break;
                    case 5:
                        con.close();
                        System.out.println("Exiting... Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🟢 Create a new task
    static void createTask(Connection con, BufferedReader br) throws IOException, SQLException {
        System.out.print("Enter task title: ");
        String title = br.readLine();
        System.out.print("Enter task status (Pending/Completed): ");
        String status = br.readLine();

        String query = "INSERT INTO tasks (title, status) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, title);
        ps.setString(2, status);
        ps.executeUpdate();
        System.out.println("✅ Task added successfully!");
    }

    // 🔵 Read all tasks
    static void viewTasks(Connection con) throws SQLException {
        String query = "SELECT * FROM tasks";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("\n--- All Tasks ---");
        boolean hasData = false;
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                               " | Title: " + rs.getString("title") +
                               " | Status: " + rs.getString("status"));
            hasData = true;
        }
        if (!hasData) System.out.println("No tasks found!");
    }

    // 🟡 Update task
    static void updateTask(Connection con, BufferedReader br) throws IOException, SQLException {
        System.out.print("Enter Task ID to update: ");
        int id = Integer.parseInt(br.readLine());
        System.out.print("Enter new title: ");
        String title = br.readLine();
        System.out.print("Enter new status: ");
        String status = br.readLine();

        String query = "UPDATE tasks SET title=?, status=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, title);
        ps.setString(2, status);
        ps.setInt(3, id);
        int rows = ps.executeUpdate();

        if (rows > 0)
            System.out.println("✅ Task updated successfully!");
        else
            System.out.println("❌ Task not found!");
    }

    // 🔴 Delete task
    static void deleteTask(Connection con, BufferedReader br) throws IOException, SQLException {
        System.out.print("Enter Task ID to delete: ");
        int id = Integer.parseInt(br.readLine());

        String query = "DELETE FROM tasks WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();

        if (rows > 0)
            System.out.println("🗑️ Task deleted successfully!");
        else
            System.out.println("❌ Task not found!");
    }
}
