package org.dti.se.module1session9.userinterfaces;

import org.dti.se.module1session9.models.Task;
import org.dti.se.module1session9.models.User;
import org.dti.se.module1session9.usecases.AuthenticationUseCase;
import org.dti.se.module1session9.usecases.TaskUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Profile("!test")
@Component
public class TaskInterface {


    @Autowired
    AuthenticationUseCase authenticationUseCase;

    @Autowired
    TaskUseCase taskUseCase;

    @Autowired
    Scanner scanner;

    @Autowired
    @Qualifier("context")
    HashMap<String, Object> context;

    User user;
    List<Task> tasks;

    public void render() {
        user = (User) context.get("user");

        while (true) {
            System.out.println("Task Interface");
            System.out.println("1. View tasks");
            System.out.println("2. Create task");
            System.out.println("3. Update task");
            System.out.println("4. Delete task");
            System.out.println("0. Logout");
            String option = scanner.nextLine();

            if (option.equals("1")) {
                viewTasks();
                System.out.println("Press enter to continue...");
                scanner.nextLine();
            } else if (option.equals("2")) {
                System.out.println("Enter task name: ");
                String name = scanner.nextLine();
                System.out.println("Enter task description: ");
                String description = scanner.nextLine();
                Task task = taskUseCase.createOne(user.getId(), name, description).block();
                System.out.println("Task created.");
            } else if (option.equals("3")) {
                viewTasks();
                System.out.println("Enter task index: ");
                int index = Integer.parseInt(scanner.nextLine());
                Task task = tasks.get(index);
                System.out.println("Enter task name: ");
                String name = scanner.nextLine();
                System.out.println("Enter task description: ");
                String description = scanner.nextLine();
                task = taskUseCase.updateOne(task.getId(), name, description).block();
                System.out.println("Task updated.");
            } else if (option.equals("4")) {
                viewTasks();
                System.out.println("Enter task index: ");
                int index = Integer.parseInt(scanner.nextLine());
                Task task = tasks.get(index);
                taskUseCase.deleteOne(task.getId()).block();
                System.out.println("Task deleted.");
            } else if (option.equals("0")) {
                context.remove("user");
                break;
            }
        }
    }

    public void viewTasks() {
        tasks = taskUseCase.findManyByUserId(user.getId()).collectList().block();
        for (int index = 0; index < Objects.requireNonNull(tasks).size(); index++) {
            Task task = tasks.get(index);
            System.out.println("Index: " + index);
            System.out.println("Task ID: " + task.getId());
            System.out.println("Task Name: " + task.getName());
            System.out.println("Task Description: " + task.getDescription());
            System.out.println("Task Created At: " + task.getCreatedAt());
            System.out.println("Task Updated At: " + task.getUpdatedAt());
            System.out.println();
        }
    }
}
