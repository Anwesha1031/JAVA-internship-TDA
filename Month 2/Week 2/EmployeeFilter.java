import java.io.*;
import java.util.*;
import java.util.stream.*;

// Employee class
class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return name + " (₹" + salary + ")";
    }
}

public class EmployeeFilter {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Employee> employees = new ArrayList<>();

        System.out.print("Enter number of employees: ");
        int n = Integer.parseInt(br.readLine());

        // Take employee input
        for (int i = 0; i < n; i++) {
            System.out.print("Enter employee name: ");
            String name = br.readLine();

            System.out.print("Enter salary of " + name + ": ");
            double salary = Double.parseDouble(br.readLine());

            employees.add(new Employee(name, salary));
        }

        // Take filtering range
        System.out.print("Enter minimum salary: ");
        double min = Double.parseDouble(br.readLine());

        System.out.print("Enter maximum salary: ");
        double max = Double.parseDouble(br.readLine());

        // Use streams to filter employees by salary
        List<Employee> filtered = employees.stream()
                                           .filter(e -> e.getSalary() >= min && e.getSalary() <= max)
                                           .collect(Collectors.toList());

        System.out.println("\nEmployees with salary between ₹" + min + " and ₹" + max + ":");
        filtered.forEach(System.out::println);
    }
}
