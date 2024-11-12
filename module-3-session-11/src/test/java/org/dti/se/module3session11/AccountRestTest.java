package org.dti.se.module3session11;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.inners.models.valueobjects.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

import java.time.ZonedDateTime;
import java.util.UUID;

public class AccountRestTest extends TestConfiguration {
    Account authenticatedAccount;
    Session authenticatedSession;
    String authorization;

    @BeforeEach
    public void beforeEach() {
        super.setup();

        this.authenticatedAccount = super.register().getData();
        this.authenticatedSession = super.login(authenticatedAccount).getData();
        this.authorization = String.format("Bearer %s", this.authenticatedSession.getAccessToken());
        this.webTestClient = this.webTestClient
                .mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorization)
                .build();
    }

    @AfterEach
    public void afterEach() {
//        super.logout();

//        super.teardown();
    }

    @Test
    public void testSaveOne() {
        ZonedDateTime now = ZonedDateTime.now();
        Account accountCreator = Account
                .builder()
                .id(UUID.randomUUID())
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
//                    assert body.getData().equals(accountCreator);
                });

        fakeAccounts.add(accountCreator);
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
//                    assert body.getData().equals(realAccount);
                });
    }

    @Test
    public void testPatchOneById() {
        Account realAccount = fakeAccounts.getFirst();
        Account accountPatcher = Account
                .builder()
                .id(realAccount.getId())
                .roleId("user")
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(String.format("password-%s", UUID.randomUUID()))
                .pin(String.format("pin-%s", UUID.randomUUID()))
                .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID()))
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
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
                    assert body.getMessage().equals("Account updated.");
                    assert body.getData().getId().equals(realAccount.getId());
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
//                    assert body.getData().equals(realAccount);
                });
    }

}
