package org.dti.se.module3session9.inners.models.dao;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "account")
public class Account extends Dao {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String pin;
    private String profileImageUrl;
}
