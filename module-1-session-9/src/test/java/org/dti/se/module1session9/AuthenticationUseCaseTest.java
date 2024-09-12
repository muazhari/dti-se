package org.dti.se.module1session9;

import org.dti.se.module1session9.exceptions.CredentialWrongException;
import org.dti.se.module1session9.exceptions.EmailExistsException;
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
public class AuthenticationUseCaseTest {

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
    }

    @AfterEach
    public void tearDown() {
        for (User user : userFakes) {
            StepVerifier
                    .create(userRepository.deleteOne(user.getId()))
                    .expectNextCount(0)
                    .verifyComplete();
        }
    }

    @Test
    public void testRegister() {
        String email = "email3_" + UUID.randomUUID();
        String password = "password3";

        StepVerifier
                .create(authenticationUseCase.register(email, password))
                .assertNext(registeredUser -> {
                    userFakes.add(registeredUser);
                    OffsetDateTime now = OffsetDateTime.now();
                    assert registeredUser.getEmail().equals(email);
                    assert registeredUser.getPassword().equals(password);
                    assert registeredUser.getCreatedAt().isBefore(now);
                    assert registeredUser.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();
    }

    @Test
    public void testRegisterWithExistingEmail() {
        User user = userFakes.get(0);

        StepVerifier
                .create(authenticationUseCase.register(user.getEmail(), user.getPassword()))
                .expectError(EmailExistsException.class)
                .verifyThenAssertThat();
    }

    @Test
    public void testLogin() {
        User user = userFakes.get(0);

        StepVerifier
                .create(authenticationUseCase.login(user.getEmail(), user.getPassword()))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    public void testLoginWithWrongPassword() {
        User user = userFakes.get(0);

        StepVerifier
                .create(authenticationUseCase.login(user.getEmail(), user.getPassword() + "wrong"))
                .expectError(CredentialWrongException.class)
                .verifyThenAssertThat();
    }
}
