package org.dti.se.selectiontest1backend1.inners.models.valueobjects.authentications;

import lombok.*;
import lombok.experimental.Accessors;
import org.dti.se.selectiontest1backend1.inners.models.Model;


@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginByEmailAndPasswordRequest extends Model {
    private String email;
    private String password;
}
