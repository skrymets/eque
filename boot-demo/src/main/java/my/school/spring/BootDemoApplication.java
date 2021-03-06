package my.school.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BootDemoApplication {

    public static void main(String[] args) {
        Object[] sources = {
            BootDemoApplication.class
                //, GroovyController.class
        };
        SpringApplication.run(sources, args);
    }
}
