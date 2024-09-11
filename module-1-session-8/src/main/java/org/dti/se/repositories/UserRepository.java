package org.dti.se.repositories;


import org.dti.se.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    @Qualifier("oneDatastoreClient")
    DatabaseClient databaseClient;

    public Mono<User> findOneById(UUID id) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_USER
                            WHERE id = :id
                        """)
                .bind("id", id)
                .map((row, rowMetadata) -> User
                        .builder()
                        .id(row.get("id", UUID.class))
                        .name(row.get("name", String.class))
                        .email(row.get("email", String.class))
                        .password(row.get("password", String.class))
                        .createdAt(row.get("created_at", OffsetDateTime.class))
                        .updatedAt(row.get("updated_at", OffsetDateTime.class))
                        .build()
                )
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<Void> save(User user) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_USER (id, name, email, password, created_at, updated_at)
                        VALUES (:id, :name, :email, :password, :createdAt, :updatedAt)
                        """)
                .bind("id", user.getId())
                .bind("name", user.getName())
                .bind("email", user.getEmail())
                .bind("password", user.getPassword())
                .bind("createdAt", user.getCreatedAt())
                .bind("updatedAt", user.getUpdatedAt())
                .fetch()
                .rowsUpdated()
                .then();
    }
}
