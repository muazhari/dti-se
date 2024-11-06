package org.dti.se.module3session11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class Module3Session11Application {

    public static void main(String[] args) {
        SpringApplication.run(Module3Session11Application.class, args);
    }

}
