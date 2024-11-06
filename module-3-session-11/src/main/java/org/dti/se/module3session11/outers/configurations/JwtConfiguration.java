package org.dti.se.module3session11.outers.configurations;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class JwtConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(Objects.requireNonNull(environment.getProperty("jwt.secret")));
    }
}
