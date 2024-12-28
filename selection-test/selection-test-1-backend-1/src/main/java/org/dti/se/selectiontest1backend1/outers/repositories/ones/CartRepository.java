package org.dti.se.selectiontest1backend1.outers.repositories.ones;

import org.dti.se.selectiontest1backend1.inners.models.entities.Cart;
import org.dti.se.selectiontest1backend1.inners.models.entities.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends R2dbcRepository<Cart, UUID> {

}