package org.dti.se.selectiontest1backend1;

import org.dti.se.selectiontest1backend1.inners.models.entities.Account;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class AccountRestTest extends TestConfiguration {

    @BeforeEach
    public void beforeEach() {
        configure();
        populate();
        auth();
    }

    @AfterEach
    public void afterEach() {
        deauth();
        depopulate();
    }

    @Test
    public void testSaveOne() {
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
        Account accountCreator = Account
                .builder()
                .id(UUID.randomUUID())
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(String.format("password-%s", UUID.randomUUID()))
                .phone(String.format("phone-%s", UUID.randomUUID()))
                .dob(now)
                .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID()))
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
                    assert body.getData().getName().equals(accountCreator.getName());
                    assert body.getData().getEmail().equals(accountCreator.getEmail());
                    assert securityConfiguration.matches(accountCreator.getPassword(), body.getData().getPassword());
                    assert body.getData().getPhone().equals(accountCreator.getPhone());
                    assert body.getData().getDob().equals(accountCreator.getDob());
                    assert body.getData().getProfileImageUrl().equals(accountCreator.getProfileImageUrl());
                    fakeAccounts.add(body.getData());
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
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS);
        Account realAccount = fakeAccounts.getFirst();
        Account accountPatcher = Account
                .builder()
                .id(realAccount.getId())
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(String.format("password-%s", UUID.randomUUID()))
                .phone(String.format("phone-%s", UUID.randomUUID()))
                .dob(now)
                .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID()))
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
                    assert Objects.equals(body.getData().getId(), accountPatcher.getId());
                    assert body.getData().getName().equals(accountPatcher.getName());
                    assert body.getData().getEmail().equals(accountPatcher.getEmail());
                    assert securityConfiguration.matches(accountPatcher.getPassword(), body.getData().getPassword());
                    assert body.getData().getPhone().equals(accountPatcher.getPhone());
                    assert body.getData().getDob().equals(accountPatcher.getDob());
                    assert body.getData().getProfileImageUrl().equals(accountPatcher.getProfileImageUrl());
                    fakeAccounts.set(fakeAccounts.indexOf(realAccount), body.getData());
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
                    fakeAccounts.remove(realAccount);
                });
    }
}
