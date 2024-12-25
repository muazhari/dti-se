package org.dti.se.selectiontest1backend1;

import org.dti.se.selectiontest1backend1.inners.models.entities.Account;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.ResponseBody;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.Session;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.authentications.LoginByEmailAndPasswordRequest;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.authentications.RegisterByEmailAndPasswordRequest;
import org.dti.se.selectiontest1backend1.outers.configurations.SecurityConfiguration;
import org.dti.se.selectiontest1backend1.outers.repositories.ones.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebFlux
@AutoConfigureWebTestClient
public class TestConfiguration {

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected SecurityConfiguration securityConfiguration;

    @Autowired
    @Qualifier("oneTemplate")
    protected R2dbcEntityTemplate oneTemplate;

    protected ArrayList<Account> fakeAccounts = new ArrayList<>();

    protected String rawPassword = String.format("password-%s", UUID.randomUUID());
    protected Account authenticatedAccount;
    protected Session authenticatedSession;

    public void configure() {
        this.webTestClient = this.webTestClient
                .mutate()
                .responseTimeout(Duration.ofSeconds(5))
                .build();
    }

    public void populate() {
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
        for (int i = 0; i < 4; i++) {
            Account newAccount = Account
                    .builder()
                    .id(UUID.randomUUID())
                    .name(String.format("name-%s", UUID.randomUUID()))
                    .email(String.format("email-%s", UUID.randomUUID()))
                    .password(securityConfiguration.encode(rawPassword))
                    .phone(String.format("phone-%s", UUID.randomUUID()))
                    .dob(now)
                    .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID())) // Added profile image
                    .build();
            fakeAccounts.add(newAccount);
        }
        StepVerifier.create(accountRepository.saveAll(fakeAccounts)).expectNextCount(fakeAccounts.size()).verifyComplete();
    }


    public void depopulate() {
        StepVerifier.create(accountRepository.deleteAll(fakeAccounts)).verifyComplete();
    }

    public void auth() {
        authenticatedAccount = register().getData();
        fakeAccounts.add(authenticatedAccount);
        authenticatedSession = login(authenticatedAccount).getData();
        String authorization = String.format("Bearer %s", authenticatedSession.getAccessToken());
        webTestClient = webTestClient
                .mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorization)
                .build();
    }

    public void auth(Account account) {
        authenticatedAccount = account;
        authenticatedSession = login(account).getData();
        String authorization = String.format("Bearer %s", authenticatedSession.getAccessToken());
        webTestClient = webTestClient
                .mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorization)
                .build();
    }

    public void deauth() {
        logout(authenticatedSession);
    }

    protected ResponseBody<Account> register() {
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
        RegisterByEmailAndPasswordRequest requestBody = RegisterByEmailAndPasswordRequest
                .builder()
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(rawPassword)
                .phone(String.format("phone-%s", UUID.randomUUID()))
                .dob(now)
                .build();

        return webTestClient
                .post()
                .uri("/authentications/registers/email-password")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ResponseBody<Account>>() {
                })
                .value(body -> {
                    assert body != null;
                    assert body.getMessage().equals("Register succeed.");
                    assert body.getData() != null;
                    assert body.getData().getId() != null;
                    assert body.getData().getName().equals(requestBody.getName());
                    assert body.getData().getEmail().equals(requestBody.getEmail());
                    assert securityConfiguration.matches(requestBody.getPassword(), body.getData().getPassword());
                    assert body.getData().getPhone().equals(requestBody.getPhone());
                    assert body.getData().getDob().equals(requestBody.getDob());
                })
                .returnResult()
                .getResponseBody();
    }

    protected ResponseBody<Session> login(Account account) {
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
        LoginByEmailAndPasswordRequest requestBody = LoginByEmailAndPasswordRequest
                .builder()
                .email(account.getEmail())
                .password(rawPassword)
                .build();

        return webTestClient
                .post()
                .uri("/authentications/logins/email-password")
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ResponseBody<Session>>() {
                })
                .value(body -> {
                    assert body != null;
                    assert body.getMessage().equals("Login succeed.");
                    assert body.getData() != null;
                    assert body.getData().getAccessToken() != null;
                    assert body.getData().getRefreshToken() != null;
                    assert body.getData().getAccessTokenExpiredAt().isAfter(now);
                    assert body.getData().getRefreshTokenExpiredAt().isAfter(now);
                })
                .returnResult()
                .getResponseBody();
    }

    protected ResponseBody<Void> logout(Session session) {
        return this.webTestClient
                .post()
                .uri("/authentications/logouts/session")
                .bodyValue(session)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ResponseBody<Void>>() {
                })
                .value(body -> {
                    assert body != null;
                    assert body.getMessage().equals("Logout succeed.");
                })
                .returnResult()
                .getResponseBody();
    }

}
