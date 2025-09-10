import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloWorldSpringBoot {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldSpringBoot.class, args);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World! Welcome to Spring Boot API";
    }
}
