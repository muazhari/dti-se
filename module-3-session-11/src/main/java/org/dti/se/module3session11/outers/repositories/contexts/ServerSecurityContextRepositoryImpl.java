package org.dti.se.module3session11.outers.repositories.contexts;

import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.outers.exceptions.jwt.AccessTokenInvalidException;
import org.dti.se.module3session11.outers.repositories.twos.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Repository
public class ServerSecurityContextRepositoryImpl implements ServerSecurityContextRepository {


    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ReactiveAuthenticationManagerImpl reactiveAuthenticationManagerImpl;

    public Mono<String> getAccessToken(ServerWebExchange exchange) {
        return Mono
                .fromCallable(exchange::getRequest)
                .mapNotNull(serverHttpRequest -> serverHttpRequest.getHeaders().getFirst("Authorization"))
                .filter(authorizationHeader -> !authorizationHeader.isBlank() && !authorizationHeader.isEmpty())
                .filter(authorizationHeader -> authorizationHeader.startsWith("Bearer "))
                .switchIfEmpty(Mono.error(new AccessTokenInvalidException()))
                .map(authorizationHeader -> authorizationHeader.split(" ")[1]);
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        if (context.getAuthentication() == null) {
            return this.load(exchange)
                    .map(validatedContext -> (Session) validatedContext.getAuthentication().getCredentials())
                    .map(session -> sessionRepository.deleteByAccessToken(session.getAccessToken()))
                    .then();
        } else {
            return Mono
                    .fromCallable(context::getAuthentication)
                    .map(authentication -> (Session) authentication.getCredentials())
                    .map(session -> sessionRepository.setByAccessToken(session))
                    .then();
        }
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return this.getAccessToken(exchange)
                .map(accessToken -> new UsernamePasswordAuthenticationToken(null, accessToken))
                .flatMap(authentication -> reactiveAuthenticationManagerImpl.authenticate(authentication))
                .map(SecurityContextImpl::new);
    }
}
