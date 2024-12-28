package org.dti.se.selectiontest1backend1.outers.repositories.ones;

import aj.org.objectweb.asm.commons.Remapper;
import org.dti.se.selectiontest1backend1.inners.models.entities.Cart;
import org.dti.se.selectiontest1backend1.inners.models.entities.CartItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends R2dbcRepository<CartItem, UUID> {
    Flux<CartItem> deleteAllByProductIdIn(List<UUID> productIds);
}