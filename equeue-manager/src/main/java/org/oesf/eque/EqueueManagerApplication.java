package org.oesf.eque;

import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EqueueManagerApplication {

    public static void main(String[] args) {

        String[] extArgs = {
            "--spring.profiles.active=debug"
        };

        String[] all = Stream.concat(
                Arrays.stream(args),
                Arrays.stream(extArgs)
        ).toArray(String[]::new);

        SpringApplication.run(EqueueManagerApplication.class, all);
    }
}
