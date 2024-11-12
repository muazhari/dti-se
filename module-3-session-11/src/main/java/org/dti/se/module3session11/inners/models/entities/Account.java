package org.dti.se.module3session11.inners.models.entities;

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
@Table(name = "account")
public class Account extends Model {
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    @Id
    private UUID id;
    private String roleId;
    private String name;
    private String email;
    private String password;
    private String pin;
    private String profileImageUrl;
}
