package org.dti.se.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.SortedMap;
import java.util.UUID;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BookedTicket extends Dao {
    private UUID id;
    private UUID userId;
    private UUID ticketId;
    private Boolean isConfirmed;
    private SortedMap<String, String> details;
}
