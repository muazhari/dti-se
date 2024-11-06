package org.dti.se.module3session11.outers.repositories.two;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;


@Repository
public class SessionRepository {

    @Autowired
    ReactiveRedisTemplate<String, String> stringTemplate;

    public Mono<Boolean> setByAccountId(Session session) {
        return stringTemplate
                .opsForValue()
                .set(
                        session.getAccountId().toString(),
                        session.toJsonString(),
                        Duration.between(
                                ZonedDateTime.now().toInstant(),
                                session.getAccessTokenExpiredAt().toInstant()
                        )
                );
    }


    public Mono<Session> getByAccountId(UUID accountId) {
        return stringTemplate
                .opsForValue()
                .get(accountId.toString())
                .handle((jsonString, sink) -> {
                    try {
                        sink.next(Jackson2ObjectMapperBuilder.json().build().readValue(jsonString, Session.class));
                    } catch (JsonProcessingException e) {
                        sink.error(new RuntimeException(e));
                    }
                });
    }


    public Mono<Boolean> deleteByAccountId(UUID accountId) {
        return stringTemplate
                .opsForValue()
                .delete(accountId.toString());
    }

}
