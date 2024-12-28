package org.dti.se.selectiontest1backend1.inners.usecases.carts;

import org.dti.se.selectiontest1backend1.inners.models.entities.Cart;
import org.dti.se.selectiontest1backend1.inners.models.entities.CartItem;
import org.dti.se.selectiontest1backend1.inners.models.valueobjects.*;
import org.dti.se.selectiontest1backend1.outers.repositories.ones.CartItemRepository;
import org.dti.se.selectiontest1backend1.outers.repositories.ones.CartRepository;
import org.dti.se.selectiontest1backend1.outers.repositories.ones.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicCartUseCase {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;


    public Mono<CartResponse> add(CartAddRequest request) {
        return cartRepository
                .findById(request.getCartId())
                .switchIfEmpty(cartRepository.save(Cart.builder().id(request.getCartId()).userId(request.getUserId()).build()))
                .flatMap(cartItems -> cartItemRepository.saveAll(request.getItems().stream().map(item -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setProductId(item.getProductId());
                    return cartItem;
                }).toList()).collect(Collectors.toList()))
                .map(cartItems -> {
                    CartResponse response = new CartResponse();
                    response.setCartId(request.getCartId());
                    response.setUserId(request.getUserId());
                    response.setItems(cartItems.stream().map(item -> {
                        CartItemRequest cartItemRequest = new CartItemRequest();
                        cartItemRequest.setProductId(item.getProductId());
                        return cartItemRequest;
                    }).toList());
                    return response;
                });
    }


    public Mono<CartResponse> remove(CartRemoveRequest request) {
        return cartItemRepository
                .deleteAllByProductIdIn(request.getItems().stream().map(CartItemRequest::getProductId).collect(Collectors.toList()))
                .collect(Collectors.toList())
                .map(cartItems -> {
                    CartResponse response = new CartResponse();
                    response.setCartId(request.getCartId());
                    response.setUserId(request.getUserId());
                    response.setItems(cartItems.stream().map(item -> {
                        CartItemRequest cartItemRequest = new CartItemRequest();
                        cartItemRequest.setProductId(item.getProductId());
                        return cartItemRequest;
                    }).toList());
                    return response;
                });
    }

    public Mono<CartResponse> view(CartViewRequest request) {
        return cartItemRepository
                .findAll()
                .collect(Collectors.toList())
                .map(cartItems -> {
                    CartResponse response = new CartResponse();
                    response.setCartId(request.getCartId());
                    response.setUserId(request.getUserId());
                    response.setItems(cartItems.stream().map(item -> {
                        CartItemRequest cartItemRequest = new CartItemRequest();
                        cartItemRequest.setProductId(item.getProductId());
                        return cartItemRequest;
                    }).toList());
                    return response;
                });
    }

}
