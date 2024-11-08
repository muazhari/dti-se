package org.dti.se.module3session11.inners.usecases;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountCredentialsInvalidException;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountExistsException;
import org.dti.se.module3session11.outers.repositories.contexts.ServerSecurityContextRepositoryImpl;
import org.dti.se.module3session11.outers.repositories.ones.AccountRepository;
import org.dti.se.module3session11.outers.repositories.twos.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class AuthenticationUseCase {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JwtUseCase jwtUseCase;

    @Autowired
    SessionRepository sessionRepository;


    public Mono<Account> loginByEmailAndPassword(String email, String password) {
        return accountRepository
                .findFirstByEmailAndPassword(email, password)
                .switchIfEmpty(Mono.error(new AccountCredentialsInvalidException()));
    }

    public Mono<Account> registerByEmailAndPassword(Account accountToRegister) {
        return accountRepository
                .findFirstByEmail(accountToRegister.getEmail())
                .flatMap(account -> Mono.error(new AccountExistsException()))
                .switchIfEmpty(Mono.just(accountToRegister))
                .map(account -> ((Account) account)
                        .setId(UUID.randomUUID())
                        .setProfileImageUrl("https://placehold.co/200x200")
                        .setCreatedAt(ZonedDateTime.now())
                        .setUpdatedAt(ZonedDateTime.now())
                )
                .flatMap(accountRepository::save);
    }

}
