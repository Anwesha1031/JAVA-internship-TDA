import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class AnimalPolymorphism {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter animal type (dog/cat): ");
        String type = reader.readLine();

        System.out.print("Enter animal name: ");
        String name = reader.readLine();

        Animal myAnimal;

        if ("dog".equalsIgnoreCase(type)) {
            myAnimal = new Dog(name);
        } else if ("cat".equalsIgnoreCase(type)) {
            myAnimal = new Cat(name);
        } else {
            myAnimal = new Animal(name);
        }

        myAnimal.makeSound(); // Demonstrates overriding and polymorphism
    }
}
