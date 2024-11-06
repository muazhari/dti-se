package org.dti.se.module3session11.outers.deliveries.filters;

import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ExceptionHandlerFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain
                .filter(exchange)
                .onErrorResume(e -> {
                    ResponseEntity response = switch (e.getClass().getSimpleName()) {
                        case "AccessTokenExpiredException" -> ResponseBody
                                .builder()
                                .message("Access token expired.")
                                .build()
                                .toEntity(HttpStatus.UNAUTHORIZED);
                        default -> ResponseBody
                                .builder()
                                .message("Internal server error.")
                                .build()
                                .toEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                    };

                    exchange.getResponse().setStatusCode(response.getStatusCode());
                    exchange.getResponse().getHeaders().setContentType(response.getHeaders().getContentType());
                    return exchange
                            .getResponse()
                            .writeWith(Mono
                                    .just(exchange
                                            .getResponse()
                                            .bufferFactory()
                                            .wrap(response.getBody() != null ? response.getBody().toString().getBytes() : new byte[0])
                                    )
                            );
                });
    }
}
