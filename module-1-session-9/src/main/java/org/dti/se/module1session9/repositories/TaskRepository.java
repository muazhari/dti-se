package org.dti.se.module1session9.repositories;


import org.dti.se.module1session9.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class TaskRepository {

    @Autowired
    @Qualifier("oneDatastoreClient")
    DatabaseClient databaseClient;


    public Flux<Task> findManyByUserId(UUID userId) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_TASK
                            WHERE USER_ID = :userId
                        """)
                .bind("userId", userId)
                .mapProperties(Task.class)
                .all();
    }

    public Mono<Task> findOneById(UUID id) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_TASK
                            WHERE ID = :id
                            LIMIT 1
                        """)
                .bind("id", id)
                .mapProperties(Task.class)
                .one();
    }

    public Mono<Void> saveOne(Task task) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_TASK (ID, USER_ID, NAME, DESCRIPTION, CREATED_AT, UPDATED_AT)
                        VALUES (:id, :userId, :name, :description, :createdAt, :updatedAt)
                        """)
                .bindProperties(task)
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Void> updateOne(Task task) {
        return databaseClient
                .sql("""
                        UPDATE TABLE_TASK
                        SET USER_ID = :userId, NAME = :name, DESCRIPTION = :description, UPDATED_AT = :updatedAt, CREATED_AT = :createdAt
                        WHERE ID = :id
                        """)
                .bindProperties(task)
                .fetch()
                .rowsUpdated()
                .then();
    }


    public Mono<Void> deleteOne(UUID id) {
        return databaseClient
                .sql("""
                        DELETE FROM TABLE_TASK
                        WHERE id = :id
                        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}
