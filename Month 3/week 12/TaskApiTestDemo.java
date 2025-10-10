import java.io.*;
import java.util.*;

// Simulating a simple Task Management API
class TaskAPI {
    private List<String> tasks = new ArrayList<>();

    public String createTask(String taskName) {
        tasks.add(taskName);
        return "Task created: " + taskName;
    }

    public String getAllTasks() {
        if (tasks.isEmpty())
            return "No tasks found.";
        return "Tasks: " + tasks;
    }

    public String updateTask(String oldName, String newName) {
        int index = tasks.indexOf(oldName);
        if (index != -1) {
            tasks.set(index, newName);
            return "Task updated: " + oldName + " → " + newName;
        } else {
            return "Task not found.";
        }
    }

    public String deleteTask(String taskName) {
        if (tasks.remove(taskName))
            return "Task deleted: " + taskName;
        else
            return "Task not found.";
    }
}

// Main class to simulate testing the API
public class TaskApiTestDemo {
    public static void main(String[] args) throws IOException {
        TaskAPI api = new TaskAPI();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("=== Task Management API Test ===");
        System.out.println("1. Create Task\n2. View All Tasks\n3. Update Task\n4. Delete Task\n5. Run Unit Tests\n6. Exit");

        while (true) {
            System.out.print("\nEnter your choice: ");
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter new task name: ");
                    String t = br.readLine();
                    System.out.println(api.createTask(t));
                    break;

                case 2:
                    System.out.println(api.getAllTasks());
                    break;

                case 3:
                    System.out.print("Enter old task name: ");
                    String oldT = br.readLine();
                    System.out.print("Enter new task name: ");
                    String newT = br.readLine();
                    System.out.println(api.updateTask(oldT, newT));
                    break;

                case 4:
                    System.out.print("Enter task to delete: ");
                    String delT = br.readLine();
                    System.out.println(api.deleteTask(delT));
                    break;

                case 5:
                    // Simple Unit Test Simulation (like API testing)
                    System.out.println("\nRunning simulated unit tests...");

                    assert api.createTask("Task1").contains("created");
                    assert api.getAllTasks().contains("Task1");
                    assert api.updateTask("Task1", "Task1 Updated").contains("updated");
                    assert api.deleteTask("Task1 Updated").contains("deleted");

                    System.out.println("✔ All basic CRUD test cases passed!");
                    break;

                case 6:
                    System.out.println("Exiting program.");
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
