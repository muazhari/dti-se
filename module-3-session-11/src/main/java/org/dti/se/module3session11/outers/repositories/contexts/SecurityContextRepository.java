package org.dti.se.module3session11.outers.repositories.contexts;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.inners.usecases.JwtUseCase;
import org.dti.se.module3session11.outers.exceptions.jwt.AccessTokenExpiredException;
import org.dti.se.module3session11.outers.exceptions.jwt.RefreshTokenExpiredException;
import org.dti.se.module3session11.outers.repositories.one.AccountRepository;
import org.dti.se.module3session11.outers.repositories.two.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.UUID;

@Repository
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private JwtUseCase jwtUseCase;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Mono<Authentication> authenticate(String accessToken) {
        return Mono
                .from(jwtUseCase.verify(accessToken))
                .filter(decodedJwt -> ZonedDateTime.now().isBefore(ChronoZonedDateTime.from(decodedJwt.getExpiresAt().toInstant())))
                .map(decodedJwt -> decodedJwt.getClaim("account_id").asString())
                .map(UUID::fromString)
                .flatMap(accountId -> Mono
                        .zip(
                                accountRepository.findFirstById(accountId),
                                sessionRepository.getByAccountId(accountId)
                        )
                )
                .map(tuple -> (Authentication) new UsernamePasswordAuthenticationToken(
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT1().getAuthorities()
                ))
                .switchIfEmpty(Mono.error(new AccessTokenExpiredException()));
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono
                .fromCallable(exchange::getRequest)
                .mapNotNull(serverHttpRequest -> serverHttpRequest.getHeaders().getFirst("Authorization"))
                .filter(authorizationHeader -> !authorizationHeader.isBlank() && !authorizationHeader.isEmpty())
                .map(authorizationHeader -> authorizationHeader.split(" ")[1])
                .flatMap(this::authenticate)
                .filter(authentication -> {
                    Session session = (Session) authentication.getCredentials();
                    return ZonedDateTime.now().isBefore(session.getRefreshTokenExpiredAt());
                })
                .map(authentication -> {
                    Account account = (Account) authentication.getPrincipal();
                    Session session = (Session) authentication.getCredentials();
                    return jwtUseCase
                            .generate(account, session.getAccessTokenExpiredAt().plusMinutes(5))
                            .map(session::setAccessToken)
                            .flatMap(sessionRepository::setByAccountId);
                })
                .switchIfEmpty(Mono.error(new RefreshTokenExpiredException()))
                .then();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return Mono
                .fromCallable(exchange::getRequest)
                .mapNotNull(serverHttpRequest -> serverHttpRequest.getHeaders().getFirst("Authorization"))
                .filter(authorizationHeader -> !authorizationHeader.isBlank() && !authorizationHeader.isEmpty())
                .map(authorizationHeader -> authorizationHeader.split(" ")[1])
                .flatMap(this::authenticate)
                .map(SecurityContextImpl::new);
    }
}
