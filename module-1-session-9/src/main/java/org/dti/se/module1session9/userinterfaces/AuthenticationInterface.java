package org.dti.se.module1session9.userinterfaces;

import org.dti.se.module1session9.exceptions.CredentialWrongException;
import org.dti.se.module1session9.exceptions.EmailExistsException;
import org.dti.se.module1session9.models.User;
import org.dti.se.module1session9.usecases.AuthenticationUseCase;
import org.dti.se.module1session9.usecases.TaskUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Scanner;

@Profile("!test")
@Component
public class AuthenticationInterface {

    @Autowired
    AuthenticationUseCase authenticationUseCase;

    @Autowired
    TaskUseCase taskUseCase;

    @Autowired
    Scanner scanner;

    @Autowired
    TaskInterface taskInterface;

    @Autowired
    @Qualifier("context")
    HashMap<String, Object> context;

    public void render() {
        while (true) {
            System.out.println("Authentication Interface");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            String option = scanner.nextLine();

            if (option.equals("1")) {
                System.out.println("Enter email: ");
                String email = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();
                try {
                    User user = authenticationUseCase.register(email, password).block();
                    System.out.println("User created");
                } catch (EmailExistsException e) {
                    System.out.println("Email already exists");
                }
            } else if (option.equals("2")) {
                System.out.println("Enter email: ");
                String email = scanner.nextLine();
                System.out.println("Enter password: ");
                String password = scanner.nextLine();
                try {
                    User user = authenticationUseCase.login(email, password).block();
                    context.put("user", user);
                    taskInterface.render();
                } catch (CredentialWrongException e) {
                    System.out.println("Email or password is wrong");
                }
            } else if (option.equals("0")) {
                break;
            }
        }
    }
}
