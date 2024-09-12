package org.dti.se.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends Dao {
    private UUID id;
    private UUID eventId;
    private String name;
    private String description;
    private Double price;
    private Double quantity;
}
