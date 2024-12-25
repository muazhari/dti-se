package org.dti.se.selectiontest1backend1.outers.deliveries.filters;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.dti.se.selectiontest1backend1.outers.configurations.SecurityConfiguration;
import org.dti.se.selectiontest1backend1.outers.repositories.twos.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationWebFilterImpl extends AuthenticationWebFilter {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    SecurityConfiguration securityConfiguration;

    public AuthenticationWebFilterImpl(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        setServerAuthenticationConverter(exchange -> getAccessToken(exchange)
                .flatMap(accessToken -> sessionRepository.getByAccessToken(accessToken))
                .map(session -> new UsernamePasswordAuthenticationToken(null, session))
                .flatMap(authenticationManager::authenticate)
                .onErrorResume(TokenExpiredException.class, exception -> {
                    exchange
                            .getResponse()
                            .setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
                    return Mono.empty();
                })

        );
        setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.anyExchange());
    }

    public Mono<String> getAccessToken(ServerWebExchange exchange) {
        return Mono
                .fromCallable(exchange::getRequest)
                .mapNotNull(serverHttpRequest -> serverHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authorizationHeader -> !authorizationHeader.isBlank())
                .filter(authorizationHeader -> authorizationHeader.startsWith("Bearer "))
                .map(authorizationHeader -> authorizationHeader.split(" ")[1]);
    }
}
