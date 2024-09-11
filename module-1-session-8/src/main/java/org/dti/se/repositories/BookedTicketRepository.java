package org.dti.se.repositories;


import org.dti.se.models.BookedTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.TreeMap;
import java.util.UUID;

@Repository
public class BookedTicketRepository {

    @Autowired
    @Qualifier("oneDatastoreClient")
    DatabaseClient databaseClient;

    public Mono<BookedTicket> findOneById(UUID id) {
        return databaseClient
                .sql("""
                            SELECT * FROM table_booked_ticket
                            WHERE id = :id
                        """)
                .bind("id", id)
                .map((row, rowMetadata) -> BookedTicket
                        .builder()
                        .id(row.get("id", UUID.class))
                        .ticketId(row.get("ticket_id", UUID.class))
                        .userId(row.get("user_id", UUID.class))
                        .isConfirmed(row.get("is_confirmed", Boolean.class))
                        .details(new TreeMap<>())
                        .createdAt(row.get("created_at", OffsetDateTime.class))
                        .updatedAt(row.get("updated_at", OffsetDateTime.class))
                        .build()
                )
                .one()
                .flatMap(bookedTicket -> databaseClient
                        .sql("""
                                SELECT * FROM TABLE_BOOKED_TICKET_DETAIL
                                WHERE booked_ticket_id = :bookedTicketId
                                ORDER BY MAP_INDEX
                                """)
                        .bind("bookedTicketId", bookedTicket.getId())
                        .map((row, rowMetadata) -> {
                            String key = row.get("key", String.class);
                            String value = row.get("value", String.class);
                            bookedTicket.getDetails().put(key, value);
                            return bookedTicket;
                        })
                        .one())
                .switchIfEmpty(Mono.error(new RuntimeException("Booked ticket not found")));
    }

    public Mono<Void> save(BookedTicket bookedTicket) {
        return databaseClient
                .sql("""
                        INSERT INTO table_booked_ticket (id, ticket_id, user_id, is_confirmed, created_at, updated_at)
                        VALUES (:id, :ticketId, :userId, :isConfirmed, :createdAt, :updatedAt)
                        """)
                .bind("id", bookedTicket.getId())
                .bind("ticketId", bookedTicket.getTicketId())
                .bind("userId", bookedTicket.getUserId())
                .bind("isConfirmed", bookedTicket.getIsConfirmed())
                .bind("createdAt", bookedTicket.getCreatedAt())
                .bind("updatedAt", bookedTicket.getUpdatedAt())
                .fetch()
                .rowsUpdated()
                .then();
    }
}
