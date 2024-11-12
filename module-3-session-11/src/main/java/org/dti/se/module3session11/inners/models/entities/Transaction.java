package org.dti.se.module3session11.inners.models.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.module3session11.inners.models.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "transaction")
public class Transaction extends Model {
    @Id
    private UUID id;
    private UUID walletId;
    private BigDecimal amount;
    private String transactionType;
    private String description;
    private ZonedDateTime transactionDate;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
}
