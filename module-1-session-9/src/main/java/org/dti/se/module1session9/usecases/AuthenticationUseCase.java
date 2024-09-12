package org.dti.se.module1session9.usecases;

import org.dti.se.module1session9.exceptions.CredentialWrongException;
import org.dti.se.module1session9.exceptions.EmailExistsException;
import org.dti.se.module1session9.models.User;
import org.dti.se.module1session9.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;


@Service
public class AuthenticationUseCase {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> register(String email, String password) {
        OffsetDateTime now = OffsetDateTime.now();
        return userRepository
                .findOneByEmail(email)
                .<User>flatMap(user -> Mono.error(new EmailExistsException()))
                .switchIfEmpty(Mono.just(User
                        .builder()
                        .id(UUID.randomUUID())
                        .email(email)
                        .password(password)
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
                ))
                .flatMap((user) -> userRepository
                        .saveOne(user)
                        .thenReturn(user)
                );
    }

    public Mono<User> login(String email, String password) {
        return userRepository
                .findOneByEmailAndPassword(email, password)
                .switchIfEmpty(Mono.error(new CredentialWrongException()));
    }

}
