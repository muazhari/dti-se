package org.dti.se.repositories;


import org.dti.se.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public class TicketRepository {

    @Autowired
    @Qualifier("oneDatastoreClient")
    DatabaseClient databaseClient;

    public Mono<Ticket> findOneById(UUID id) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_TICKET
                            WHERE id = :id
                        """)
                .bind("id", id)
                .map((row, rowMetadata) -> Ticket
                        .builder()
                        .id(row.get("id", UUID.class))
                        .eventId(row.get("event_id", UUID.class))
                        .name(row.get("name", String.class))
                        .description(row.get("description", String.class))
                        .price(row.get("price", Number.class))
                        .createdAt(row.get("created_at", OffsetDateTime.class))
                        .updatedAt(row.get("updated_at", OffsetDateTime.class))
                        .build()
                )
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("Ticket not found")));
    }

    public Mono<Void> save(Ticket ticket) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_TICKET (id, event_id, name, description, price, created_at, updated_at)
                        VALUES (:id, :eventId, :name, :description, :price, :createdAt, :updatedAt)
                        """)
                .bind("id", ticket.getId())
                .bind("eventId", ticket.getEventId())
                .bind("name", ticket.getName())
                .bind("description", ticket.getDescription())
                .bind("price", ticket.getPrice())
                .bind("createdAt", ticket.getCreatedAt())
                .bind("updatedAt", ticket.getUpdatedAt())
                .fetch()
                .rowsUpdated()
                .then();
    }
}
