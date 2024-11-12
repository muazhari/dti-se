package org.dti.se.module3session11;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.LoginByEmailAndPasswordRequest;
import org.dti.se.module3session11.inners.models.valueobjects.RegisterByEmailAndPasswordRequest;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.dti.se.module3session11.outers.repositories.ones.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebFlux
@AutoConfigureWebTestClient
public class TestConfiguration {
    protected final ArrayList<Account> fakeAccounts = new ArrayList<>();
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected AccountRepository accountRepository;

    public void setup() {
        for (int i = 0; i < 4; i++) {
            ZonedDateTime now = ZonedDateTime.now();
            Account newAccount = Account
                    .builder()
                    .id(UUID.randomUUID())
                    .roleId(List.of("user", "admin").get(i % 2))
                    .name(String.format("name-%s", UUID.randomUUID()))
                    .email(String.format("email-%s", UUID.randomUUID()))
                    .password(String.format("password-%s", UUID.randomUUID()))
                    .pin(String.format("pin-%s", UUID.randomUUID()))
                    .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID()))
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            fakeAccounts.add(newAccount);
        }

        StepVerifier
                .create(accountRepository.saveAll(fakeAccounts))
                .expectNextCount(fakeAccounts.size())
                .verifyComplete();
    }

    public void teardown() {
        StepVerifier
                .create(accountRepository.deleteAllById(fakeAccounts.stream().map(Account::getId).toList()))
                .expectNextCount(0)
                .verifyComplete();
    }

    protected ResponseBody<Account> register() {
        RegisterByEmailAndPasswordRequest requestBody = RegisterByEmailAndPasswordRequest
                .builder()
                .roleId("user")
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(String.format("password-%s", UUID.randomUUID()))
                .pin(String.format("pin-%s", UUID.randomUUID()))
                .build();

        return this.webTestClient
                .post()
                .uri("/authentications/registers/email-and-password")
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
                    assert body.getData().getRoleId().equals(requestBody.getRoleId());
                    assert body.getData().getName().equals(requestBody.getName());
                    assert body.getData().getEmail().equals(requestBody.getEmail());
                    assert body.getData().getPassword().equals(requestBody.getPassword());
                    assert body.getData().getPin().equals(requestBody.getPin());
                    assert body.getData().getProfileImageUrl() != null;
                    assert body.getData().getCreatedAt().isBefore(ZonedDateTime.now());
                    assert body.getData().getUpdatedAt().isBefore(ZonedDateTime.now());
                })
                .returnResult()
                .getResponseBody();
    }

    protected ResponseBody<Session> login(Account account) {
        LoginByEmailAndPasswordRequest requestBody = LoginByEmailAndPasswordRequest
                .builder()
                .email(account.getEmail())
                .password(account.getPassword())
                .build();

        return this.webTestClient
                .post()
                .uri("/authentications/logins/email-and-password")
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
                    assert body.getData().getAccessTokenExpiredAt().isAfter(ZonedDateTime.now());
                    assert body.getData().getRefreshTokenExpiredAt().isAfter(ZonedDateTime.now());
                })
                .returnResult()
                .getResponseBody();
    }

    protected ResponseBody<Void> logout() {
        return this.webTestClient
                .post()
                .uri("/authentications/logouts/access-token")
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
