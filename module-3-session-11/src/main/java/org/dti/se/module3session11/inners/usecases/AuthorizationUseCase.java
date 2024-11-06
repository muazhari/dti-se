package org.dti.se.module3session11.inners.usecases;

import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.outers.repositories.two.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class AuthorizationUseCase {

    @Autowired
    AccountUseCase accountUseCase;

    @Autowired
    JwtUseCase jwtUseCase;

    @Autowired
    SessionRepository sessionRepository;

    public Mono<Session> refreshAccessToken(String refreshToken) {
        return jwtUseCase
                .verify(refreshToken)
                .map(decodedJwt -> decodedJwt.getClaim("account_id").as(UUID.class))
                .flatMap(accountId -> Mono
                        .zip(
                                accountUseCase.findOneById(accountId),
                                sessionRepository.getByAccountId(accountId)
                        )
                )
                .map(tuple -> Session
                        .builder()
                        .accountId(tuple.getT1().getId())
                        .accessToken(tuple.getT2().getAccessToken())
                        .refreshToken(tuple.getT2().getRefreshToken())
                        .accessTokenExpiredAt(ZonedDateTime.now().plusMinutes(5))
                        .refreshTokenExpiredAt(ZonedDateTime.now().plusDays(3))
                        .build()
                );
    }
}
