package org.dti.se.module3session9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class Module3Session9Application {

    public static void main(String[] args) {
        SpringApplication.run(Module3Session9Application.class, args);
    }

}
