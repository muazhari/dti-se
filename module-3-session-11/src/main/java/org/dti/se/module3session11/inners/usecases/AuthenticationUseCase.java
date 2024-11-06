package org.dti.se.module3session11.inners.usecases;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountExistException;
import org.dti.se.module3session11.outers.repositories.two.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class AuthenticationUseCase {

    @Autowired
    AccountUseCase accountUseCase;

    @Autowired
    JwtUseCase jwtUseCase;

    @Autowired
    SessionRepository sessionRepository;

    public Mono<Session> loginByEmailAndPassword(String email, String password) {
        return accountUseCase
                .findOneByEmailAndPassword(email, password)
                .flatMap(account -> {
                    ZonedDateTime now = ZonedDateTime.now();
                    return Mono.zip(
                            Mono.just(now),
                            Mono.just(account),
                            jwtUseCase.generate(account, now.plusMinutes(5)),
                            jwtUseCase.generate(account, now.plusDays(3))
                    );
                })
                .map(tuple -> Session
                        .builder()
                        .accountId(tuple.getT2().getId())
                        .accessToken(tuple.getT3())
                        .refreshToken(tuple.getT4())
                        .accessTokenExpiredAt(tuple.getT1().plusMinutes(5))
                        .refreshTokenExpiredAt(tuple.getT1().plusDays(3))
                        .build()
                )
                .flatMap(session -> sessionRepository
                        .setByAccountId(session)
                        .thenReturn(session)
                );
    }

    public Mono<Session> registerByEmailAndPassword(Account accountToRegister) {
        return accountUseCase
                .findOneByEmail(accountToRegister.getEmail())
                .flatMap(account -> Mono.error(new AccountExistException()))
                .switchIfEmpty(Mono.just(accountToRegister))
                .flatMap(account -> accountUseCase.saveOne((Account) account))
                .flatMap(account -> {
                    ZonedDateTime now = ZonedDateTime.now();
                    return Mono.zip(
                            Mono.just(now),
                            Mono.just(account),
                            jwtUseCase.generate(account, now.plusMinutes(5)),
                            jwtUseCase.generate(account, now.plusDays(3))
                    );
                })
                .map(tuple -> Session
                        .builder()
                        .accountId(tuple.getT2().getId())
                        .accessToken(tuple.getT3())
                        .refreshToken(tuple.getT4())
                        .accessTokenExpiredAt(tuple.getT1().plusMinutes(5))
                        .refreshTokenExpiredAt(tuple.getT1().plusDays(3))
                        .build()
                )
                .flatMap(session -> sessionRepository
                        .setByAccountId(session)
                        .thenReturn(session)
                );
    }

    public Mono<Session> logoutByAccessToken(String accessToken) {
        return jwtUseCase
                .verify(accessToken)
                .map(decodedJwt -> decodedJwt.getClaim("account_id").as(UUID.class))
                .flatMap(sessionRepository::getByAccountId)
                .flatMap(session -> Mono
                        .zip(
                                Mono.just(session),
                                sessionRepository.deleteByAccountId(session.getAccountId())
                        )
                )
                .map(Tuple2::getT1);
    }
}
