package org.dti.se.module3session9.inners.models.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "session")
public class Session extends Dao {
    private UUID id;
    private UUID accountId;
    private String accessToken;
    private String refreshToken;
    private ZonedDateTime expiredAt;
}
