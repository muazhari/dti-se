package org.dti.se;

import org.dti.se.models.User;
import org.dti.se.repositories.UserRepository;
import org.dti.se.usecases.UserManagement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class TodoTest {

    @Autowired
    private UserManagement userManagement;

    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();

        user = User
                .builder()
                .id(UUID.randomUUID())
                .name("name1")
                .email("email1")
                .password("password1")
                .createdAt(now)
                .updatedAt(now)
                .build();

        StepVerifier
                .create(userRepository.saveOne(user))
                .expectNextCount(0)
                .verifyComplete();
    }

    @AfterEach
    public void tearDown() {
        StepVerifier
                .create(userRepository.deleteOne(user.getId()))
                .expectNextCount(0)
                .verifyComplete();

    }

}
