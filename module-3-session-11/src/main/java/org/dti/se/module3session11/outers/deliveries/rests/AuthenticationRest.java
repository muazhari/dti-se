package org.dti.se.module3session11.outers.deliveries.rests;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.LoginByEmailAndPasswordRequest;
import org.dti.se.module3session11.inners.models.valueobjects.RegisterByEmailAndPasswordRequest;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.inners.usecases.AuthenticationUseCase;
import org.dti.se.module3session11.inners.usecases.JwtUseCase;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountCredentialsInvalidException;
import org.dti.se.module3session11.outers.exceptions.accounts.AccountExistsException;
import org.dti.se.module3session11.outers.repositories.ones.AccountRepository;
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
public class AuthenticationRest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtUseCase jwtUseCase;
    @Autowired
    private AuthenticationUseCase authenticationUseCase;

    @PostMapping(value = "/logins/email-password")
    public Mono<ResponseEntity<ResponseBody<Session>>> loginByEmailAndPassword(
            @RequestBody LoginByEmailAndPasswordRequest request
    ) {
        return authenticationUseCase
                .loginByEmailAndPassword(request.getEmail(), request.getPassword())
                .map(session -> ResponseBody
                        .<Session>builder()
                        .message("Login succeed.")
                        .data(session)
                        .build()
                        .toEntity(HttpStatus.OK)
                )
                .onErrorResume(AccountCredentialsInvalidException.class, e -> Mono
                        .just(ResponseBody
                                .<Session>builder()
                                .message("Account credentials invalid.")
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

    @PostMapping(value = "/registers/email-password")
    public Mono<ResponseEntity<ResponseBody<Account>>> registerByEmailAndPassword(
            @RequestBody RegisterByEmailAndPasswordRequest request
    ) {
        return authenticationUseCase
                .registerByEmailAndPassword(request)
                .map(account -> ResponseBody
                        .<Account>builder()
                        .message("Register succeed.")
                        .data(account)
                        .build()
                        .toEntity(HttpStatus.CREATED)
                )
                .onErrorResume(AccountExistsException.class, e -> Mono
                        .just(ResponseBody
                                .<Account>builder()
                                .message("Account exists.")
                                .build()
                                .toEntity(HttpStatus.CONFLICT)
                        )
                )
                .onErrorResume(e -> Mono
                        .fromCallable(() -> ResponseBody
                                .<Account>builder()
                                .message("Internal server error.")
                                .exception(e)
                                .build()
                                .toEntity(HttpStatus.INTERNAL_SERVER_ERROR)
                        )
                );
    }

    @PostMapping(value = "/logouts/access-token")
    public Mono<ResponseEntity<ResponseBody<Void>>> logoutByAccessToken(
            @RequestBody Session session
    ) {
        return authenticationUseCase
                .logout(session)
                .thenReturn(ResponseBody
                        .<Void>builder()
                        .message("Logout succeed.")
                        .build()
                        .toEntity(HttpStatus.OK)
                )
                .onErrorResume(TokenExpiredException.class, e -> Mono
                        .just(ResponseBody
                                .<Void>builder()
                                .message("Access token expired.")
                                .build()
                                .toEntity(HttpStatus.UNAUTHORIZED)
                        )
                )
                .onErrorResume(JWTVerificationException.class, e -> Mono
                        .just(ResponseBody
                                .<Void>builder()
                                .message("Session verification failed.")
                                .build()
                                .toEntity(HttpStatus.UNAUTHORIZED)
                        )
                )
                .onErrorResume(e -> Mono
                        .fromCallable(() -> ResponseBody
                                .<Void>builder()
                                .message("Internal server error.")
                                .exception(e)
                                .build()
                                .toEntity(HttpStatus.INTERNAL_SERVER_ERROR)
                        )
                );
    }

}
