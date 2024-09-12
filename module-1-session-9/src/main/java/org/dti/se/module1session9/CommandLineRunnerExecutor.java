package org.dti.se.module1session9;

import org.dti.se.module1session9.userinterfaces.AuthenticationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Profile("!test")
@Component
public class CommandLineRunnerExecutor implements CommandLineRunner {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public HashMap<String, Object> context() {
        return new HashMap<>();
    }


    @Autowired
    AuthenticationInterface authenticationInterface;

    @Override
    public void run(String... args) {
        authenticationInterface.render();
    }
}
