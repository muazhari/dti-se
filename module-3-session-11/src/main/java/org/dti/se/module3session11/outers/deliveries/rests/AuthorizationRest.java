package org.dti.se.module3session11.outers.deliveries.rests;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.inners.usecases.AuthorizationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/authentications")
public class AuthorizationRest {
    @Autowired
    private AuthorizationUseCase authorizationUseCase;


    @PostMapping(value = "/authorizations/refresh-session")
    public Mono<ResponseEntity<ResponseBody<Session>>> refreshSession(
            @RequestBody Session session
    ) {
        return authorizationUseCase
                .refreshSession(session)
                .map(newSession -> ResponseBody
                        .<Session>builder()
                        .message("Session refreshed.")
                        .data(newSession)
                        .build()
                        .toEntity(HttpStatus.OK)
                )
                .onErrorResume(TokenExpiredException.class, e -> Mono
                        .just(ResponseBody
                                .<Session>builder()
                                .message("Session expired.")
                                .build()
                                .toEntity(HttpStatus.UNAUTHORIZED)
                        )
                )
                .onErrorResume(JWTVerificationException.class, e -> Mono
                        .just(ResponseBody
                                .<Session>builder()
                                .message("Session verification failed.")
                                .build()
                                .toEntity(HttpStatus.UNAUTHORIZED)
                        )
                )
                .onErrorResume(e -> Mono
                        .fromCallable(() -> ResponseBody
                                .<Session>builder()
                                .message("Internal server error.")
                                .exception(e)
                                .build()
                                .toEntity(HttpStatus.INTERNAL_SERVER_ERROR)
                        )
                );
    }


}
