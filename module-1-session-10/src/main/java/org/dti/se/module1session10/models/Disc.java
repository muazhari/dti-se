package org.dti.se.module1session10.models;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Disc extends Material {
    @Builder.Default
    private String typeId = MaterialType.DISC;
}
