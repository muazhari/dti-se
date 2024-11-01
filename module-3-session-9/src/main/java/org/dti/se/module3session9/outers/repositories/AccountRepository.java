package org.dti.se.module3session9.outers.repositories;

import org.dti.se.module3session9.inners.models.dao.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface AccountRepository extends R2dbcRepository<Account, UUID> {

    Mono<Account> findFirstByEmail(String email);

    Mono<Account> findFirstByEmailAndPassword(String email, String password);
}