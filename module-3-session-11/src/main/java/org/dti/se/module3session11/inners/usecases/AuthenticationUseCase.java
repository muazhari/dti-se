package org.dti.se.module3session11.inners.usecases;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.outers.deliveries.filters.ReactiveAuthenticationManagerImpl;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountCredentialsInvalidException;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountExistsException;
import org.dti.se.module3session11.outers.exceptions.jwt.AccessTokenInvalidException;
import org.dti.se.module3session11.outers.repositories.ones.AccountRepository;
import org.dti.se.module3session11.outers.repositories.twos.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Autowired
    ReactiveAuthenticationManagerImpl reactiveAuthenticationManagerImpl;

    public Mono<Session> loginByEmailAndPassword(String email, String password) {
        return accountRepository
                .findFirstByEmailAndPassword(email, password)
                .switchIfEmpty(Mono.error(new AccountCredentialsInvalidException()))
                .map(account -> {
                    ZonedDateTime now = ZonedDateTime.now();
                    ZonedDateTime accessTokenExpiredAt = now.plusMinutes(5);
                    ZonedDateTime refreshTokenExpiredAt = now.plusDays(3);
                    return Session
                            .builder()
                            .accountId(account.getId())
                            .accessToken(jwtUseCase.generate(account, accessTokenExpiredAt))
                            .refreshToken(jwtUseCase.generate(account, refreshTokenExpiredAt))
                            .accessTokenExpiredAt(accessTokenExpiredAt)
                            .refreshTokenExpiredAt(refreshTokenExpiredAt)
                            .build();
                })
                .flatMap(session -> sessionRepository
                        .setByAccessToken(session)
                        .thenReturn(session)
                );
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

    public Mono<Void> logout(Session session) {
        return reactiveAuthenticationManagerImpl
                .authenticate(new UsernamePasswordAuthenticationToken(null, session))
                .then(sessionRepository.deleteByAccessToken(session.getAccessToken()))
                .then();
    }

}
