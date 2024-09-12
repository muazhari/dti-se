package org.dti.se.module1session9.module1session9;

import org.dti.se.module1session9.exceptions.TaskNotFoundException;
import org.dti.se.module1session9.models.Task;
import org.dti.se.module1session9.models.User;
import org.dti.se.module1session9.repositories.TaskRepository;
import org.dti.se.module1session9.repositories.UserRepository;
import org.dti.se.module1session9.usecases.AuthenticationUseCase;
import org.dti.se.module1session9.usecases.TaskUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class TaskUseCaseTest {

    @Autowired
    private AuthenticationUseCase authenticationUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskUseCase taskUseCase;

    List<User> userFakes = new ArrayList<>();
    List<Task> taskFakes = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();

        userFakes.add(User
                .builder()
                .id(UUID.randomUUID())
                .email("email1_" + UUID.randomUUID())
                .password("password1")
                .createdAt(now)
                .updatedAt(now)
                .build());
        userFakes.add(User
                .builder()
                .id(UUID.randomUUID())
                .email("email2_" + UUID.randomUUID())
                .password("password2")
                .createdAt(now)
                .updatedAt(now)
                .build());

        for (User user : userFakes) {
            StepVerifier
                    .create(userRepository.saveOne(user))
                    .expectNextCount(0)
                    .verifyComplete();
        }

        taskFakes.add(Task
                .builder()
                .id(UUID.randomUUID())
                .userId(userFakes.get(0).getId())
                .name("name1")
                .description("description1")
                .createdAt(now)
                .updatedAt(now)
                .build());

        taskFakes.add(Task
                .builder()
                .id(UUID.randomUUID())
                .userId(userFakes.get(0).getId())
                .name("name2")
                .description("description2")
                .createdAt(now)
                .updatedAt(now)
                .build());

        for (Task task : taskFakes) {
            StepVerifier
                    .create(taskRepository.saveOne(task))
                    .expectNextCount(0)
                    .verifyComplete();
        }
    }

    @AfterEach
    public void tearDown() {
        for (Task task : taskFakes) {
            StepVerifier
                    .create(taskRepository.deleteOne(task.getId()))
                    .expectNextCount(0)
                    .verifyComplete();
        }

        for (User user : userFakes) {
            StepVerifier
                    .create(userRepository.deleteOne(user.getId()))
                    .expectNextCount(0)
                    .verifyComplete();
        }
    }

    @Test
    public void testFindOneById() {
        Task task = taskFakes.get(0);
        StepVerifier
                .create(taskUseCase.findOneById(task.getId()))
                .expectNext(task)
                .verifyComplete();
    }

    @Test
    public void testFindOneByIdNotFound() {
        UUID taskId = UUID.randomUUID();
        StepVerifier
                .create(taskUseCase.findOneById(taskId))
                .expectError(TaskNotFoundException.class)
                .verifyThenAssertThat();
    }

    @Test
    public void testFindManyByUserId() {
        User user = userFakes.get(0);
        StepVerifier
                .create(taskUseCase.findManyByUserId(user.getId()).collectList())
                .assertNext(foundTasks -> {
                    for (Task task : foundTasks) {
                        assert taskFakes.contains(task);
                    }
                })
                .verifyComplete();
    }

    @Test
    public void testCreateOne() {
        User user = userFakes.get(0);
        String name = "name3";
        String description = "description3";
        StepVerifier
                .create(taskUseCase.createOne(user.getId(), name, description))
                .assertNext(createdtask -> {
                    taskFakes.add(createdtask);
                    OffsetDateTime now = OffsetDateTime.now();
                    assert createdtask.getUserId().equals(user.getId());
                    assert createdtask.getName().equals(name);
                    assert createdtask.getDescription().equals(description);
                    assert createdtask.getCreatedAt().isBefore(now);
                    assert createdtask.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();
    }

    @Test
    public void testUpdateOne() {
        Task task = taskFakes.get(0);
        String name = "name3";
        String description = "description3";
        StepVerifier
                .create(taskUseCase.updateOne(task.getId(), name, description))
                .assertNext(updatedTask -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    assert updatedTask.getId().equals(task.getId());
                    assert updatedTask.getUserId().equals(task.getUserId());
                    assert updatedTask.getName().equals(name);
                    assert updatedTask.getDescription().equals(description);
                    assert updatedTask.getCreatedAt().equals(task.getCreatedAt());
                    assert updatedTask.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();
    }

    @Test
    public void testDeleteOne() {
        Task task = taskFakes.get(0);
        StepVerifier
                .create(taskUseCase.deleteOne(task.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }

}
