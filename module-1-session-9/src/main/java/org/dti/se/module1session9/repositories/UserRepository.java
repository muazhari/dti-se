package org.dti.se.module1session9.repositories;


import org.dti.se.module1session9.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

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
                            LIMIT 1
                        """)
                .bind("id", id)
                .mapProperties(User.class)
                .one();
    }


    public Mono<User> findOneByEmail(String email) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_USER
                            WHERE email = :email
                            LIMIT 1
                        """)
                .bind("email", email)
                .mapProperties(User.class)
                .one();
    }


    public Mono<User> findOneByEmailAndPassword(String email, String password) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_USER
                            WHERE email = :email AND password = :password
                            LIMIT 1
                        """)
                .bind("email", email)
                .bind("password", password)
                .mapProperties(User.class)
                .one();
    }

    public Mono<Void> saveOne(User user) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_USER (id, email, password, created_at, updated_at)
                        VALUES (:id, :email, :password, :createdAt, :updatedAt)
                        """)
                .bindProperties(user)
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Void> updateOne(User user) {
        return databaseClient
                .sql("""
                        UPDATE TABLE_USER
                        SET name =  email = :email, password = :password, updated_at = :updatedAt, created_at = :createdAt
                        WHERE id = :id
                        """)
                .bindProperties(user)
                .fetch()
                .rowsUpdated()
                .then();
    }


    public Mono<Void> deleteOne(UUID id) {
        return databaseClient
                .sql("""
                        DELETE FROM TABLE_USER
                        WHERE id = :id
                        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}
