package org.dti.se.module3session11;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class AccountRestTest extends TestConfiguration {
    Account authenticatedAccount;
    Session authenticatedSession;

    @BeforeEach
    public void beforeEach() {
        super.setup();
        this.authenticatedAccount = super.register().getData();
        this.authenticatedSession = super.login(authenticatedAccount).getData();
        String authorization = String.format("Bearer %s", this.authenticatedSession.getAccessToken());
        this.webTestClient = this.webTestClient
                .mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorization)
                .build();
    }

    @AfterEach
    public void afterEach() {
        super.logout(this.authenticatedSession);
        super.teardown();
    }

    @Test
    public void testSaveOne() {
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
        Account accountCreator = Account
                .builder()
                .roleId("user")
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(String.format("password-%s", UUID.randomUUID()))
                .pin(String.format("pin-%s", UUID.randomUUID()))
                .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID()))
                .createdAt(now)
                .updatedAt(now)
                .build();

        webTestClient
                .post()
                .uri("/accounts")
                .bodyValue(accountCreator)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ResponseBody<Account>>() {
                })
                .value(body -> {
                    assert body != null;
                    assert body.getMessage().equals("Account saved.");
                    assert body.getData() != null;
                    assert body.getData().getId() != null;
                    assert body.getData().getRoleId().equals(accountCreator.getRoleId());
                    assert body.getData().getName().equals(accountCreator.getName());
                    assert body.getData().getEmail().equals(accountCreator.getEmail());
                    assert body.getData().getPassword().equals(accountCreator.getPassword());
                    assert body.getData().getPin().equals(accountCreator.getPin());
                    assert body.getData().getProfileImageUrl().equals(accountCreator.getProfileImageUrl());
                    assert body.getData().getCreatedAt().equals(accountCreator.getCreatedAt());
                    assert body.getData().getUpdatedAt().equals(accountCreator.getUpdatedAt());
                });
    }

    @Test
    public void testFindOneById() {
        Account realAccount = fakeAccounts.getFirst();

        webTestClient
                .get()
                .uri("/accounts/{id}", realAccount.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ResponseBody<Account>>() {
                })
                .value(body -> {
                    assert body != null;
                    assert body.getMessage().equals("Account found.");
                    assert body.getData() != null;
                    assert body.getData().equals(realAccount);
                });
    }

    @Test
    public void testPatchOneById() {
        Account realAccount = fakeAccounts.getFirst();
        Account accountPatcher = Account
                .builder()
                .roleId("user")
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(String.format("password-%s", UUID.randomUUID()))
                .pin(String.format("pin-%s", UUID.randomUUID()))
                .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID()))
                .createdAt(OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .updatedAt(OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS))
                .build();

        webTestClient
                .patch()
                .uri("/accounts/{id}", realAccount.getId())
                .bodyValue(accountPatcher)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ResponseBody<Account>>() {
                })
                .value(body -> {
                    assert body != null;
                    assert body.getMessage().equals("Account patched.");
                    assert body.getData() != null;
                    assert body.getData().getId() != null;
                    assert body.getData().getRoleId().equals(accountPatcher.getRoleId());
                    assert body.getData().getName().equals(accountPatcher.getName());
                    assert body.getData().getEmail().equals(accountPatcher.getEmail());
                    assert body.getData().getPassword().equals(accountPatcher.getPassword());
                    assert body.getData().getPin().equals(accountPatcher.getPin());
                    assert body.getData().getProfileImageUrl().equals(accountPatcher.getProfileImageUrl());
                    assert body.getData().getCreatedAt().equals(accountPatcher.getCreatedAt());
                    assert body.getData().getUpdatedAt().equals(accountPatcher.getUpdatedAt());
                });
    }

    @Test
    public void testDeleteOneById() {
        Account realAccount = fakeAccounts.getFirst();

        webTestClient
                .delete()
                .uri("/accounts/{id}", realAccount.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ResponseBody<Account>>() {
                })
                .value(body -> {
                    assert body != null;
                    assert body.getMessage().equals("Account deleted.");
                    assert body.getData() == null;
                });

    }
}
