package org.dti.se.module3session11.inners.models.valueobjects;

import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.module3session11.inners.models.Model;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterByEmailAndPasswordRequest extends Model {
    private String roleId;
    private String name;
    private String email;
    private String password;
    private String pin;
}
