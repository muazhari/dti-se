package org.dti.se.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.SortedMap;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class BookedTicket {
    private UUID id;
    private UUID userId;
    private UUID ticketId;
    private Boolean isConfirmed;
    private SortedMap<String, String> details;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime createdAt;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime updatedAt;
}
