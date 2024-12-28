package org.dti.se.selectiontest1backend1.inners.models.valueobjects;

import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.selectiontest1backend1.inners.models.Model;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CartViewRequest extends Model {
    private UUID userId;
    private UUID cartId;
}
