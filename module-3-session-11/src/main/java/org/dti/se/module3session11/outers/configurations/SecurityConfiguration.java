package org.dti.se.module3session11.outers.configurations;

import org.dti.se.module3session11.outers.repositories.contexts.ReactiveAuthenticationManagerImpl;
import org.dti.se.module3session11.outers.repositories.contexts.ServerSecurityContextRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Autowired
    ServerSecurityContextRepositoryImpl serverSecurityContextRepositoryImpl;

    @Autowired
    ReactiveAuthenticationManagerImpl reactiveAuthenticationManagerImpl;


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .authenticationManager(reactiveAuthenticationManagerImpl)
                .securityContextRepository(serverSecurityContextRepositoryImpl)
                .authorizeExchange(authorizeExchange -> authorizeExchange
                        .pathMatchers("/authentications/**").permitAll()
                        .pathMatchers("/authorizations/**").permitAll()
                        .pathMatchers("/webjars/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyExchange().authenticated()
                )
                .build();
    }
}
