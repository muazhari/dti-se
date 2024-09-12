package org.dti.se.module1session8.repositories;


import org.dti.se.module1session8.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

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
                            LIMIT 1
                        """)
                .bind("id", id)
                .mapProperties(Ticket.class)
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("Ticket not found")));
    }

    public Mono<Void> saveOne(Ticket ticket) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_TICKET (id, event_id, name, description, price, quantity, created_at, updated_at)
                        VALUES (:id, :eventId, :name, :description, :price, :quantity, :createdAt, :updatedAt)
                        """)
                .bindProperties(ticket)
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Void> updateOne(Ticket ticket) {
        return databaseClient
                .sql("""
                        UPDATE TABLE_TICKET
                        SET event_id = :eventId, name = :name, description = :description, price = :price, quantity = :quantity, updated_at = :updatedAt, created_at = :createdAt
                        WHERE id = :id
                        """)
                .bindProperties(ticket)
                .fetch()
                .rowsUpdated()
                .then();
    }


    public Mono<Void> deleteOne(UUID id) {
        return databaseClient
                .sql("""
                        DELETE FROM TABLE_TICKET
                        WHERE id = :id
                        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}
