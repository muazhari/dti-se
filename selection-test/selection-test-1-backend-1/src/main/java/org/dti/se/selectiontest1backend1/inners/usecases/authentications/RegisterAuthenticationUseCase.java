package org.dti.se.selectiontest1backend1.inners.usecases.authentications;

import org.dti.se.selectiontest1backend1.inners.models.entities.Account;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.authentications.RegisterByEmailAndPasswordRequest;
import org.dti.se.selectiontest1backend1.outers.configurations.SecurityConfiguration;
import org.dti.se.selectiontest1backend1.outers.exceptions.accounts.AccountExistsException;
import org.dti.se.selectiontest1backend1.outers.repositories.ones.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RegisterAuthenticationUseCase {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SecurityConfiguration securityConfiguration;

    public Mono<Account> registerByEmailAndPassword(RegisterByEmailAndPasswordRequest request) {
        return accountRepository
                .findFirstByEmail(request.getEmail())
                .flatMap(foundAccount -> Mono.error(new AccountExistsException()))
                .thenReturn(Account
                        .builder()
                        .id(UUID.randomUUID())
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(securityConfiguration.encode(request.getPassword()))
                        .phone(request.getPhone())
                        .dob(request.getDob())
                        .profileImageUrl(null)
                        .build()
                )
                .flatMap(accountRepository::save);
    }

}
