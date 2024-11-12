package org.dti.se.module3session11.inners.usecases;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.outers.deliveries.filters.ReactiveAuthenticationManagerImpl;
import org.dti.se.module3session11.outers.deliveries.filters.ServerSecurityContextRepositoryImpl;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountCredentialsInvalidException;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountExistsException;
import org.dti.se.module3session11.outers.repositories.ones.AccountRepository;
import org.dti.se.module3session11.outers.repositories.twos.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Stream;

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

    @Autowired
    private ServerSecurityContextRepositoryImpl serverSecurityContextRepositoryImpl;

    public Mono<Session> loginByEmailAndPassword(ServerWebExchange exchange, String email, String password) {
        return accountRepository
                .findFirstByEmailAndPassword(email, password)
                .switchIfEmpty(Mono.error(new AccountCredentialsInvalidException()))
                .flatMap(account -> {
                    ZonedDateTime now = ZonedDateTime.now();
                    ZonedDateTime accessTokenExpiredAt = now.plusMinutes(5);
                    ZonedDateTime refreshTokenExpiredAt = now.plusDays(3);
                    Session session = Session
                            .builder()
                            .accountId(account.getId())
                            .accessToken(jwtUseCase.generate(account, accessTokenExpiredAt))
                            .refreshToken(jwtUseCase.generate(account, refreshTokenExpiredAt))
                            .accessTokenExpiredAt(accessTokenExpiredAt)
                            .refreshTokenExpiredAt(refreshTokenExpiredAt)
                            .build();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            account,
                            session,
                            Stream.of(account.getRoleId()).map(SimpleGrantedAuthority::new).toList()
                    );
                    SecurityContextImpl securityContext = new SecurityContextImpl(authentication);
                    return serverSecurityContextRepositoryImpl
                            .save(exchange, securityContext)
                            .thenReturn(session);
                });
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

    public Mono<Void> logout(ServerWebExchange exchange, Session session) {
        return Mono
                .fromCallable(() -> new UsernamePasswordAuthenticationToken(null, session))
                .map(SecurityContextImpl::new)
                .flatMap(context -> serverSecurityContextRepositoryImpl.save(exchange, context))
                .then();
    }

}
