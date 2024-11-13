package org.dti.se.module3session11.inners.models.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.module3session11.inners.models.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "goal")
public class Goal extends Model {
    @Id
    private UUID id;
    private UUID walletId;
    private String name;
    private BigDecimal income;
    private BigDecimal expense;
    private OffsetDateTime deadline;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    protected OffsetDateTime createdAt;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    protected OffsetDateTime updatedAt;
}
