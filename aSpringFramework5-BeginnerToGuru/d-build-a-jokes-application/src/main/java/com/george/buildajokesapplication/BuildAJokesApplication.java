package com.george.buildajokesapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:chuck-config.xml")
public class BuildAJokesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuildAJokesApplication.class, args);

        // Custom Banner Link: http://patorjk.com/software/taag/#p=display&f=Big%20Money-ne&t=%20%20Spring%20%0A%20%20%20%20%20Jokes%0AApplication
    }

}
