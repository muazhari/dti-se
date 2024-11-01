package org.dti.se.module3session9.inners.usecases;

import org.dti.se.module3session9.inners.models.dao.Account;
import org.dti.se.module3session9.outers.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class AccountUseCase {

    @Autowired
    private AccountRepository accountRepository;


    public Mono<Account> saveOne(Account account) {
        return accountRepository.save(account);
    }


    public Mono<Account> findOneById(UUID id) {
        return accountRepository.findById(id);
    }

    public Mono<Account> findOneByEmail(String email) {
        return accountRepository.findFirstByEmail(email);
    }

    public Mono<Account> findOneByEmailAndPassword(String email, String password) {
        return accountRepository.findFirstByEmailAndPassword(email, password);
    }


    public Mono<Account> patchOneById(UUID id, Account account) {
        return accountRepository.findById(id)
                .map(accountToPatch -> accountToPatch.<Account>patchFrom(account))
                .onErrorResume(throwable -> Mono.empty())

                .flatMap(accountRepository::save);
    }

    public Mono<Void> deleteOneById(UUID id) {
        return accountRepository.deleteById(id);
    }

}
