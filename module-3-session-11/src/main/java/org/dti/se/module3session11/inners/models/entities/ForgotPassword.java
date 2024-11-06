package org.dti.se.module3session11.inners.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.module3session11.inners.models.Model;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "forgot_password")
@Entity
public class ForgotPassword extends Model {
    @Id
    private UUID id;
    private UUID accountId;
    private String token;
    private ZonedDateTime expiredAt;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
}
