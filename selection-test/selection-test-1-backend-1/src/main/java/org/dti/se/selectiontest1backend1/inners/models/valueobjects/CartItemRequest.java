package org.dti.se.selectiontest1backend1.inners.models.valueobjects;

import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.selectiontest1backend1.inners.models.Model;
import org.dti.se.selectiontest1backend1.inners.models.entities.CartItem;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CartItemRequest extends Model {
    private UUID productId;
    private Double quantity;
}
