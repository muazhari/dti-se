package org.dti.se.module3session11.outers.deliveries.rests;

import org.dti.se.module3session11.inners.models.entities.Account;
import org.dti.se.module3session11.inners.models.valueobjects.ResponseBody;
import org.dti.se.module3session11.inners.usecases.AccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(value = "/authentications")
public class AuthenticationRest {
    @Autowired
    private AccountUseCase accountUseCase;


    @PatchMapping(value = "/{id}")
    public Mono<ResponseEntity<ResponseBody<Account>>> patchOneById(
            @PathVariable(value = "id") UUID id,
            @RequestBody Account account
    ) {
        return accountUseCase
                .patchOneById(id, account)
                .map(updatedAccount -> ResponseBody
                        .<Account>builder()
                        .message("Account patched.")
                        .data(updatedAccount)
                        .build()
                        .toEntity(HttpStatus.OK)
                )
                .onErrorResume(e -> Mono
                        .fromCallable(() -> switch (e.getClass().getSimpleName()) {
                            case "AccountNotFoundException" -> ResponseBody
                                    .<Account>builder()
                                    .message("Account not found")
                                    .build()
                                    .toEntity(HttpStatus.NOT_FOUND);
                            default -> ResponseBody
                                    .<Account>builder()
                                    .message("Internal server error")
                                    .build()
                                    .toEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                        })
                );
    }


}
