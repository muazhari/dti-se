package org.dti.se.module1session10.repositories;


import org.dti.se.module1session10.models.MaterialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class MaterialTypeRepository {

    @Autowired
    @Qualifier("oneDatastoreClient")
    DatabaseClient databaseClient;

    public Mono<MaterialType> findOneById(UUID id) {
        return databaseClient
                .sql("""
                            SELECT * FROM TABLE_MATERIAL_TYPE
                            WHERE id = :id
                            LIMIT 1
                        """)
                .bind("id", id)
                .mapProperties(MaterialType.class)
                .one()
                .switchIfEmpty(Mono.error(new RuntimeException("MaterialType not found")));
    }

    public Mono<Void> saveOne(MaterialType materialType) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_MATERIAL_TYPE (id, name, description, created_at, updated_at)
                        VALUES (:id, :name, :description, :createdAt, :updatedAt)
                        """)
                .bindProperties(materialType)
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Void> updateOneById(UUID idToUpdate, MaterialType materialType) {
        return databaseClient
                .sql("""
                        UPDATE TABLE_MATERIAL_TYPE
                        SET id = :id, name = :name, description = :description, updated_at = :updatedAt, created_at = :createdAt
                        WHERE id = :idToUpdate
                        """)
                .bindProperties(materialType)
                .bind("idToUpdate", idToUpdate)
                .fetch()
                .rowsUpdated()
                .then();
    }

    public Mono<Void> deleteOneById(UUID id) {
        return databaseClient
                .sql("""
                        DELETE FROM TABLE_MATERIAL_TYPE
                        WHERE id = :id
                        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}
