package org.dti.se.module3session11.inners.usecases;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.outers.exceptions.jwt.DecodeFailedException;
import org.dti.se.module3session11.outers.exceptions.jwt.VerifyFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtUseCase {

    @Autowired
    private Algorithm algorithm;

    public Mono<String> generate(Account account, ZonedDateTime expirationTime) {
        return Mono
                .fromCallable(() -> {
                    ZonedDateTime now = ZonedDateTime.now();
                    return JWT
                            .create()
                            .withIssuer("server")
                            .withClaim("account_id", account.getId().toString())
                            .withExpiresAt(Date.from(expirationTime.toInstant()))
                            .withIssuedAt(Date.from(now.toInstant()))
                            .sign(algorithm);
                });
    }

    public Mono<DecodedJWT> verify(String token) {
        return Mono
                .fromCallable(() -> JWT
                        .require(algorithm)
                        .withIssuer("server")
                        .build()
                )
                .map(verifier -> verifier.verify(token))
                .onErrorResume(e -> Mono.error(new VerifyFailedException(e)));
    }

    public Mono<DecodedJWT> decode(String token) {
        return Mono
                .fromCallable(() -> JWT.decode(token))
                .onErrorResume(e -> Mono.error(new DecodeFailedException(e)));
    }
}
