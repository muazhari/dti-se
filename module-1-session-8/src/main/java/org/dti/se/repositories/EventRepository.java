package org.dti.se.repositories;


import org.dti.se.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
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
                        """)
                .bind("id", id)
                .map((row, rowMetadata) -> Event
                        .builder()
                        .id(row.get("id", UUID.class))
                        .name(row.get("name", String.class))
                        .description(row.get("description", String.class))
                        .creatorUserId(row.get("creator_user_id", UUID.class))
                        .createdAt(row.get("created_at", OffsetDateTime.class))
                        .updatedAt(row.get("updated_at", OffsetDateTime.class))
                        .build()
                )
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("Event not found")));
    }

    public Mono<Void> save(Event event) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_EVENT (id, name, description, creator_user_id, created_at, updated_at)
                        VALUES (:id, :name, :description, :creatorUserId, :createdAt, :updatedAt)
                        """)
                .bind("id", event.getId())
                .bind("name", event.getName())
                .bind("description", event.getDescription())
                .bind("creatorUserId", event.getCreatorUserId())
                .bind("createdAt", event.getCreatedAt())
                .bind("updatedAt", event.getUpdatedAt())
                .fetch()
                .rowsUpdated()
                .then();
    }
}
