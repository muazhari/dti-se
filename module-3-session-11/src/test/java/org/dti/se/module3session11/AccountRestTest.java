package org.dti.se.module3session11;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.outers.repositories.one.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.test.StepVerifier;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRestTest extends TestConfiguration {
    @Autowired
    private AccountRepository accountRepository;

    private final ArrayList<Account> fakeAccounts = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        for (int i = 0; i < 30; i++) {
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

    @AfterEach
    public void afterEach() {
        StepVerifier
                .create(accountRepository.deleteAllById(fakeAccounts.stream().map(Account::getId).toList()))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void testSaveOne() {
        ZonedDateTime now = ZonedDateTime.now();
        Account newAccount = Account
                .builder()
                .id(UUID.randomUUID())
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
                .bodyValue(newAccount)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ResponseEntity<ResponseBody<Account>>>() {
                })
                .value(responseEntity -> {
                    assert responseEntity.getStatusCode().equals(HttpStatus.CREATED);
                    assert responseEntity.getBody() != null;
                    assert responseEntity.getBody().getData().equals(newAccount);
                });

        fakeAccounts.add(newAccount);
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
                .expectBody(new ParameterizedTypeReference<ResponseEntity<ResponseBody<Account>>>() {
                })
                .value(responseEntity -> {
                    assert responseEntity.getStatusCode().equals(HttpStatus.OK);
                    assert responseEntity.getBody() != null;
                    assert responseEntity.getBody().getData().equals(realAccount);
                });
    }

    @Test
    public void testPatchOneById() {
        Account realAccount = fakeAccounts.getFirst();
        Account accountPatcher = Account
                .builder()
                .name(String.format("name-%s", UUID.randomUUID()))
                .email(String.format("email-%s", UUID.randomUUID()))
                .password(String.format("password-%s", UUID.randomUUID()))
                .pin(String.format("pin-%s", UUID.randomUUID()))
                .profileImageUrl(String.format("profileImageUrl-%s", UUID.randomUUID()))
                .build();

        webTestClient
                .patch()
                .uri("/accounts/{id}", realAccount.getId())
                .bodyValue(accountPatcher)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ResponseEntity<ResponseBody<Account>>>() {
                })
                .value(responseEntity -> {
                    assert responseEntity.getStatusCode().equals(HttpStatus.OK);
                    assert responseEntity.getBody() != null;
                    assert responseEntity.getBody().getData().getId().equals(realAccount.getId());
                    assert responseEntity.getBody().getData().getName().equals(accountPatcher.getName());
                    assert responseEntity.getBody().getData().getEmail().equals(accountPatcher.getEmail());
                    assert responseEntity.getBody().getData().getPassword().equals(accountPatcher.getPassword());
                    assert responseEntity.getBody().getData().getPin().equals(accountPatcher.getPin());
                    assert responseEntity.getBody().getData().getProfileImageUrl().equals(accountPatcher.getProfileImageUrl());
                    assert responseEntity.getBody().getData().getCreatedAt().equals(realAccount.getCreatedAt());
                    assert responseEntity.getBody().getData().getUpdatedAt().isAfter(realAccount.getUpdatedAt());
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
                .expectBody(new ParameterizedTypeReference<ResponseEntity<ResponseBody<Account>>>() {
                })
                .value(responseEntity -> {
                    assert responseEntity.getStatusCode().equals(HttpStatus.OK);
                    assert responseEntity.getBody() != null;
                    assert responseEntity.getBody().getData().equals(realAccount);
                });
    }

}
