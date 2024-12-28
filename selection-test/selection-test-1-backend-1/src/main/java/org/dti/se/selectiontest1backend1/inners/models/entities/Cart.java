package org.dti.se.selectiontest1backend1.inners.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.selectiontest1backend1.inners.models.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "product")
public class Cart extends Model implements Persistable<UUID> {
    @Id
    private UUID id;
    private UUID userId;

    @Transient
    @Builder.Default
    @JsonIgnore
    public Boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
