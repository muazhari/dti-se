package org.dti.se.module3session11.outers.repositories.contexts;

import org.dti.se.module3session11.inners.usecases.JwtUseCase;
import org.dti.se.module3session11.outers.exceptions.jwt.AccessTokenExpiredException;
import org.dti.se.module3session11.outers.exceptions.jwt.VerifyFailedException;
import org.dti.se.module3session11.outers.repositories.ones.AccountRepository;
import org.dti.se.module3session11.outers.repositories.twos.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class ReactiveAuthenticationManagerImpl implements ReactiveAuthenticationManager {
    @Autowired
    private JwtUseCase jwtUseCase;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono
                .fromCallable(() -> (String) authentication.getCredentials())
                .map(accessToken -> jwtUseCase.verify(accessToken))
                .onErrorResume(e -> Mono.error(new VerifyFailedException()))
                .filter(decodedJwt -> ZonedDateTime.now().isBefore(
                                ZonedDateTime.ofInstant(
                                        decodedJwt.getExpiresAt().toInstant(),
                                        ZoneId.systemDefault()
                                )
                        )
                )
                .switchIfEmpty(Mono.error(new AccessTokenExpiredException()))
                .map(decodedJwt -> decodedJwt.getClaim("account_id").asString())
                .map(UUID::fromString)
                .flatMap(accountId -> Mono
                        .zip(
                                accountRepository.findFirstById(accountId),
                                sessionRepository.getByAccessToken((String) authentication.getCredentials())
                        )
                )
                .map(tuple -> (Authentication) new UsernamePasswordAuthenticationToken(
                        tuple.getT1(),
                        tuple.getT2(),
                        Stream.of(tuple.getT1().getRoleId()).map(SimpleGrantedAuthority::new).toList()
                ));
    }
}
