package org.dti.se.module3session9.inners.models.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "transaction")
public class Transaction extends Dao {
    private UUID id;
    private UUID walletId;
    private BigDecimal amount;
    private String transactionType;
    private String description;
    private ZonedDateTime transactionDate;
}
