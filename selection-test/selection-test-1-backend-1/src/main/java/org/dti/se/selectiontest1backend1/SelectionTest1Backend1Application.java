package org.dti.se.selectiontest1backend1;

import org.dti.se.selectiontest1backend1.outers.configurations.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SelectionTest1Backend1Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SelectionTest1Backend1Application.class, args);
    }

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Override
    public void run(String... args) throws Exception {
        String rawPassword = "password";
        String encodedPassword = securityConfiguration.encode(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
    }
}
