package org.dti.se.module1session10.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Material extends Dao {
    private UUID id;
    private String typeId;
    private String name;
    private String description;
    private Boolean isBorrowed;
    private Map<String, String> metadata;
}
