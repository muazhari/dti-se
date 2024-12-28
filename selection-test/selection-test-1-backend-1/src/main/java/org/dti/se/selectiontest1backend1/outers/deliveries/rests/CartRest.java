package org.dti.se.selectiontest1backend1.outers.deliveries.rests;

import org.dti.se.selectiontest1backend1.inners.models.valueobjects.*;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.ResponseBody;
import org.dti.se.selectiontest1backend1.inners.usecases.carts.BasicCartUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RestController
@RequestMapping(value = "/carts")
public class CartRest {
    @Autowired
    private BasicCartUseCase basicCartUseCase;

    @PostMapping("/view")
    public Mono<ResponseEntity<ResponseBody<CartResponse>>> view(
            @RequestBody CartViewRequest request
    ) {
        return basicCartUseCase
                .view(request)
                .map(savedCart -> ResponseBody
                        .<CartResponse>builder()
                        .message("Cart viewed.")
                        .data(savedCart)
                        .build()
                        .toEntity(HttpStatus.CREATED)
                );
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<ResponseBody<CartResponse>>> add(
            @RequestBody CartAddRequest request
    ) {
        return basicCartUseCase
                .add(request)
                .map(savedCart -> ResponseBody
                        .<CartResponse>builder()
                        .message("Cart added.")
                        .data(savedCart)
                        .build()
                        .toEntity(HttpStatus.CREATED)
                );
    }

    @PostMapping("/remove")
    public Mono<ResponseEntity<ResponseBody<CartResponse>>> remove(
            @RequestBody CartRemoveRequest request
    ) {
        return basicCartUseCase
                .remove(request)
                .map(savedCart -> ResponseBody
                        .<CartResponse>builder()
                        .message("Cart removed.")
                        .data(savedCart)
                        .build()
                        .toEntity(HttpStatus.CREATED)
                );
    }


}

