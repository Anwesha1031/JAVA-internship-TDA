import java.io.*;
import java.util.*;

// Simulating a Service Layer
class TaskService {
    private List<String> tasks = new ArrayList<>();

    public String addTask(String task) {
        tasks.add(task);
        return "Task added: " + task;
    }

    public String getAllTasks() {
        if (tasks.isEmpty()) return "No tasks available.";
        return "Tasks: " + tasks.toString();
    }

    public String deleteTask(String task) {
        if (tasks.remove(task)) {
            return "Task deleted: " + task;
        } else {
            return "Task not found!";
        }
    }
}

// Simulated Unit Test using BufferedReader (mock style)
public class TaskServiceTestDemo {
    public static void main(String[] args) throws IOException {
        TaskService mockService = new TaskService(); // Simulating dependency

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("=== Task Service Test Demo ===");
        System.out.println("1. Add Task\n2. View Tasks\n3. Delete Task\n4. Run Mock Test\n5. Exit");

        while (true) {
            System.out.print("\nEnter choice: ");
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String t = br.readLine();
                    System.out.println(mockService.addTask(t));
                    break;

                case 2:
                    System.out.println(mockService.getAllTasks());
                    break;

                case 3:
                    System.out.print("Enter task to delete: ");
                    String d = br.readLine();
                    System.out.println(mockService.deleteTask(d));
                    break;

                case 4:
                    // Mock Test Simulation (like using Mockito)
                    System.out.println("\nRunning mock test cases...");
                    String result1 = mockService.addTask("MockTask1");
                    assert result1.contains("Task added");
                    System.out.println("✔ addTask() test passed!");

                    String result2 = mockService.deleteTask("MockTask1");
                    assert result2.contains("deleted");
                    System.out.println("✔ deleteTask() test passed!");

                    System.out.println("All mock test cases passed successfully!");
                    break;

                case 5:
                    System.out.println("Exiting program.");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
