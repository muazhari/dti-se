package org.dti.se.module3session11.inners.models.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.module3session11.inners.models.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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
    @Id
    private UUID id;
    private String roleId;
    private String name;
    private String email;
    private String password;
    private String pin;
    private String profileImageUrl;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
}
