package org.dti.se.module1session8.repositories;


import org.dti.se.module1session8.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class EventRepository {

    @Autowired
    @Qualifier("oneDatastoreClient")
    DatabaseClient databaseClient;

    public Mono<Event> findOneById(UUID id) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_EVENT
                            WHERE id = :id
                            LIMIT 1
                        """)
                .bind("id", id)
                .mapProperties(Event.class)
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("Event not found")));
    }

    public Mono<Void> saveOne(Event event) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_EVENT (id, name, description, creator_user_id, created_at, updated_at)
                        VALUES (:id, :name, :description, :creatorUserId, :createdAt, :updatedAt)
                        """)
                .bindProperties(event)
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Void> updateOne(Event event) {
        return databaseClient
                .sql("""
                        UPDATE TABLE_EVENT
                        SET name = :name, description = :description, creator_user_id = :creatorUserId, updated_at = :updatedAt, created_at = :createdAt
                        WHERE id = :id
                        """)
                .bindProperties(event)
                .fetch()
                .rowsUpdated()
                .then();
    }


    public Mono<Void> deleteOne(UUID id) {
        return databaseClient
                .sql("""
                        DELETE FROM TABLE_EVENT
                        WHERE id = :id
                        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}
