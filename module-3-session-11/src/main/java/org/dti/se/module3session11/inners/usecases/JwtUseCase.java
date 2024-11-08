package org.dti.se.module3session11.inners.usecases;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.outers.exceptions.jwt.DecodeFailedException;
import org.dti.se.module3session11.outers.exceptions.jwt.VerifyFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Service
@Configuration
public class JwtUseCase {

    @Autowired
    private Environment environment;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(Objects.requireNonNull(environment.getProperty("jwt.secret")));
    }

    public String generate(Account account, ZonedDateTime expirationTime) {

        ZonedDateTime now = ZonedDateTime.now();
        return JWT
                .create()
                .withIssuer("server")
                .withClaim("account_id", account.getId().toString())
                .withExpiresAt(Date.from(expirationTime.toInstant()))
                .withIssuedAt(Date.from(now.toInstant()))
                .sign(algorithm());
    }

    public DecodedJWT verify(String token) {
        return JWT
                .require(algorithm())
                .withIssuer("server")
                .build()
                .verify(token);
    }

    public DecodedJWT decode(String token) {
        return JWT.decode(token);
    }
}
