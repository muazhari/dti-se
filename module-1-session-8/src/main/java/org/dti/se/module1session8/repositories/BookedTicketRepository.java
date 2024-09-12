package org.dti.se.module1session8.repositories;


import org.dti.se.module1session8.models.BookedTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.LinkedHashMap;
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
                            LIMIT 1
                        """)
                .bind("id", id)
                .mapProperties(BookedTicket.class)
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

    public Mono<BookedTicket> findOneByTicketId(UUID ticketId) {
        return databaseClient
                .sql("""
                            SELECT * FROM table_booked_ticket
                            WHERE ticket_id = :ticketId
                            LIMIT 1
                        """)
                .bind("ticketId", ticketId)
                .map(row -> BookedTicket
                        .builder()
                        .id(row.get("id", UUID.class))
                        .ticketId(row.get("ticket_id", UUID.class))
                        .userId(row.get("user_id", UUID.class))
                        .isConfirmed(row.get("is_confirmed", Boolean.class))
                        .details(new LinkedHashMap<>())
                        .createdAt(Objects.requireNonNull(row.get("created_at", OffsetDateTime.class)))
                        .updatedAt(Objects.requireNonNull(row.get("updated_at", OffsetDateTime.class)))
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
                            String key = row.get("MAP_KEY", String.class);
                            String value = row.get("MAP_VALUE", String.class);
                            bookedTicket.getDetails().put(key, value);
                            return bookedTicket;
                        })
                        .all()
                        .then(Mono.just(bookedTicket))
                        .switchIfEmpty(Mono.error(new RuntimeException("Booked ticket not found")))
                );
    }

    public Mono<Void> saveOne(BookedTicket bookedTicket) {
        OffsetDateTime now = OffsetDateTime.now();
        return databaseClient
                .sql("""
                        INSERT INTO table_booked_ticket (id, ticket_id, user_id, is_confirmed, created_at, updated_at)
                        VALUES (:id, :ticketId, :userId, :isConfirmed, :createdAt, :updatedAt)
                        """)
                .bind("id", bookedTicket.getId())
                .bind("ticketId", bookedTicket.getTicketId())
                .bind("userId", bookedTicket.getUserId())
                .bind("isConfirmed", bookedTicket.getIsConfirmed())
                .bind("createdAt", now)
                .bind("updatedAt", now)
                .fetch()
                .rowsUpdated()
                .then(Flux
                        .fromIterable(bookedTicket.getDetails().entrySet())
                        .index()
                        .flatMap(entry -> databaseClient
                                .sql("""
                                        INSERT INTO TABLE_BOOKED_TICKET_DETAIL (id, booked_ticket_id, map_key, map_value, map_index, created_at, updated_at)
                                        VALUES (:id, :bookedTicketId, :mapKey, :mapValue, :mapIndex, :createdAt, :updatedAt)
                                        """)
                                .bind("id", UUID.randomUUID())
                                .bind("bookedTicketId", bookedTicket.getId())
                                .bind("mapKey", entry.getT2().getKey())
                                .bind("mapValue", entry.getT2().getValue())
                                .bind("mapIndex", entry.getT1())
                                .bind("createdAt", now)
                                .bind("updatedAt", now)
                                .fetch()
                                .rowsUpdated()
                        ).then()
                );
    }

    public Mono<Void> updateOne(BookedTicket bookedTicket) {
        return databaseClient
                .sql("""
                        UPDATE table_booked_ticket
                        SET ticket_id = :ticketId, user_id = :userId, is_confirmed = :isConfirmed, updated_at = :updatedAt, created_at = :createdAt
                        WHERE id = :id
                        """)
                .bind("id", bookedTicket.getId())
                .bind("ticketId", bookedTicket.getTicketId())
                .bind("userId", bookedTicket.getUserId())
                .bind("isConfirmed", bookedTicket.getIsConfirmed())
                .bind("updatedAt", bookedTicket.getUpdatedAt())
                .bind("createdAt", bookedTicket.getCreatedAt())
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Void> deleteOne(UUID id) {
        return databaseClient
                .sql("""
                        DELETE FROM table_booked_ticket
                        WHERE id = :id
                        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }

}
