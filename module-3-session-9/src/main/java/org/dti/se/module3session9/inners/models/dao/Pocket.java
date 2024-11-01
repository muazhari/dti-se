package org.dti.se.module3session9.inners.models.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "pocket")
public class Pocket extends Dao {
    private UUID id;
    private UUID walletId;
    private String name;
    private String emoji;
    private String description;
    private BigDecimal budget;
    private BigDecimal spent;
}
