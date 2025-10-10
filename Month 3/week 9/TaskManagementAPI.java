import java.io.*;
import java.util.*;

class Task {
    int id;
    String title;
    String status;

    // Constructor
    Task(int id, String title, String status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }
}

public class TaskManagementAPI {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Task> tasks = new ArrayList<>();
        int nextId = 1;

        while (true) {
            System.out.println("\n===== TASK MANAGEMENT API =====");
            System.out.println("1. POST   /tasks     → Create a new task");
            System.out.println("2. GET    /tasks     → View all tasks");
            System.out.println("3. PUT    /tasks/{id}→ Update a task");
            System.out.println("4. DELETE /tasks/{id}→ Delete a task");
            System.out.println("5. EXIT");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1: // Create (POST)
                    System.out.println("\n--- Create Task ---");
                    System.out.print("Enter task title: ");
                    String title = br.readLine();
                    Task newTask = new Task(nextId++, title, "Pending");
                    tasks.add(newTask);
                    System.out.println("HTTP 201 Created → Task added successfully!");
                    break;

                case 2: // Read (GET)
                    System.out.println("\n--- All Tasks ---");
                    if (tasks.isEmpty()) {
                        System.out.println("HTTP 204 No Content → No tasks found!");
                    } else {
                        for (Task t : tasks) {
                            System.out.println("ID: " + t.id + " | Title: " + t.title + " | Status: " + t.status);
                        }
                        System.out.println("HTTP 200 OK → Tasks retrieved successfully!");
                    }
                    break;

                case 3: // Update (PUT)
                    System.out.print("\nEnter Task ID to update: ");
                    int updateId = Integer.parseInt(br.readLine());
                    boolean found = false;

                    for (Task t : tasks) {
                        if (t.id == updateId) {
                            System.out.print("Enter new title: ");
                            t.title = br.readLine();
                            System.out.print("Enter new status (Pending/Completed): ");
                            t.status = br.readLine();
                            System.out.println("HTTP 200 OK → Task updated successfully!");
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        System.out.println("HTTP 404 Not Found → Task not found!");
                    break;

                case 4: // Delete (DELETE)
                    System.out.print("\nEnter Task ID to delete: ");
                    int deleteId = Integer.parseInt(br.readLine());
                    boolean removed = tasks.removeIf(t -> t.id == deleteId);
                    if (removed)
                        System.out.println("HTTP 200 OK → Task deleted successfully!");
                    else
                        System.out.println("HTTP 404 Not Found → Task not found!");
                    break;

                case 5: // Exit
                    System.out.println("HTTP 200 OK → Exiting API simulation...");
                    return;

                default:
                    System.out.println("HTTP 400 Bad Request → Invalid choice!");
            }
        }
    }
}
