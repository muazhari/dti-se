package org.dti.se.module3session11.inners.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.module3session11.inners.models.Model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "wallet")
@Entity
public class Wallet extends Model {
    @Id
    private UUID id;
    private UUID accountId;
    private String name;
    private BigDecimal amount;
    private Boolean isMain;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
}
