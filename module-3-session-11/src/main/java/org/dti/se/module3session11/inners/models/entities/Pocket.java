package org.dti.se.module3session11.inners.models.entities;

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
@Table(name = "pocket")
public class Pocket extends Model {
    @Id
    private UUID id;
    private UUID walletId;
    private String name;
    private String emoji;
    private String description;
    private BigDecimal budget;
    private BigDecimal spent;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
}
