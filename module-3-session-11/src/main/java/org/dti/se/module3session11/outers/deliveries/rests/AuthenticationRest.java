package org.dti.se.module3session11.outers.deliveries.rests;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.LoginByEmailAndPasswordRequest;
import org.dti.se.module3session11.inners.models.valueobjects.RegisterByEmailAndPasswordRequest;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.inners.usecases.AuthenticationUseCase;
import org.dti.se.module3session11.inners.usecases.JwtUseCase;
import org.dti.se.module3session11.outers.repositories.contexts.ServerSecurityContextRepositoryImpl;
import org.dti.se.module3session11.outers.repositories.ones.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(value = "/authentications")
public class AuthenticationRest {
    @Autowired
    private AuthenticationUseCase authenticationUseCase;

    @Autowired
    ServerSecurityContextRepositoryImpl serverSecurityContextRepositoryImpl;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JwtUseCase jwtUseCase;

    @PostMapping(value = "/logins/email-and-password")
    public Mono<ResponseEntity<ResponseBody<Session>>> loginByEmailAndPassword(
            @RequestBody LoginByEmailAndPasswordRequest request,
            ServerWebExchange exchange
    ) {
        return authenticationUseCase
                .loginByEmailAndPassword(request.getEmail(), request.getPassword())
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
                    Authentication authentication = new UsernamePasswordAuthenticationToken(account, session);
                    SecurityContextImpl securityContext = new SecurityContextImpl(authentication);

                    return serverSecurityContextRepositoryImpl
                            .save(exchange, securityContext)
                            .thenReturn(session);
                })
                .map(session -> ResponseBody
                        .<Session>builder()
                        .message("Login succeed.")
                        .data(session)
                        .build()
                        .toEntity(HttpStatus.OK)
                )
                .onErrorResume(e -> Mono
                        .fromCallable(() -> switch (e.getClass().getSimpleName()) {
                            case "AccountCredentialsInvalidException" -> ResponseBody
                                    .<Session>builder()
                                    .message("Account credentials invalid.")
                                    .build()
                                    .toEntity(HttpStatus.NOT_FOUND);
                            default -> ResponseBody
                                    .<Session>builder()
                                    .message("Internal server error")
                                    .build()
                                    .toEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                        })
                );
    }

    @PostMapping(value = "/registers/email-and-password")
    public Mono<ResponseEntity<ResponseBody<Account>>> registerByEmailAndPassword(
            @RequestBody RegisterByEmailAndPasswordRequest request
    ) {
        return authenticationUseCase
                .registerByEmailAndPassword(Account
                        .builder()
                        .roleId(request.getRoleId())
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .pin(request.getPin())
                        .build()
                )
                .map(account -> ResponseBody
                        .<Account>builder()
                        .message("Register succeed.")
                        .data(account)
                        .build()
                        .toEntity(HttpStatus.CREATED)
                )
                .onErrorResume(e -> Mono
                        .fromCallable(() -> switch (e.getClass().getSimpleName()) {
                            case "AccountExistsException" -> ResponseBody
                                    .<Account>builder()
                                    .message("Account already exists.")
                                    .build()
                                    .toEntity(HttpStatus.CONFLICT);
                            default -> ResponseBody
                                    .<Account>builder()
                                    .message("Internal server error")
                                    .build()
                                    .toEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                        })
                );
    }

    @PostMapping(value = "/logouts/access-token")
    public Mono<ResponseEntity<ResponseBody<Void>>> logoutByAccessToken(
            ServerWebExchange exchange
    ) {
        return serverSecurityContextRepositoryImpl
                .save(exchange, new SecurityContextImpl())
                .thenReturn(ResponseBody
                        .<Void>builder()
                        .message("Logout succeed.")
                        .build()
                        .toEntity(HttpStatus.OK)
                )
                .onErrorResume(e -> Mono
                        .fromCallable(() -> switch (e.getClass().getSimpleName()) {
                            case "AccessTokenInvalidException" -> ResponseBody
                                    .<Void>builder()
                                    .message("Access token invalid.")
                                    .build()
                                    .toEntity(HttpStatus.BAD_REQUEST);
                            case "AccessTokenExpiredException" -> ResponseBody
                                    .<Void>builder()
                                    .message("Access token expired.")
                                    .build()
                                    .toEntity(HttpStatus.NOT_FOUND);
                            default -> ResponseBody
                                    .<Void>builder()
                                    .message("Internal server error.")
                                    .build()
                                    .toEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                        })
                );
    }

}
