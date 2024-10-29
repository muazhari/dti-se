package org.dti.se.module1session10.repositories;


import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.dti.se.module1session10.models.Book;
import org.dti.se.module1session10.models.Disc;
import org.dti.se.module1session10.models.Material;
import org.dti.se.module1session10.models.MaterialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Repository
public class MaterialRepository {

    @Autowired
    @Qualifier("oneDatastoreClient")
    DatabaseClient databaseClient;

    public Material rowMapper(Row row, RowMetadata rowMetadata) {
        String rowTypeId = Objects.requireNonNull(row.get("type_id", String.class));
        return switch (rowTypeId) {
            case MaterialType.DISC -> Disc
                    .builder()
                    .id(row.get("id", UUID.class))
                    .typeId(rowTypeId)
                    .name(row.get("name", String.class))
                    .description(row.get("description", String.class))
                    .isBorrowed(row.get("is_borrowed", Boolean.class))
                    .metadata(new HashMap<>())
                    .createdAt(Objects.requireNonNull(row.get("created_at", OffsetDateTime.class)))
                    .updatedAt(Objects.requireNonNull(row.get("updated_at", OffsetDateTime.class)))
                    .build();
            case MaterialType.BOOK -> Book
                    .builder()
                    .id(row.get("id", UUID.class))
                    .typeId(rowTypeId)
                    .name(row.get("name", String.class))
                    .description(row.get("description", String.class))
                    .isBorrowed(row.get("is_borrowed", Boolean.class))
                    .metadata(new HashMap<>())
                    .createdAt(Objects.requireNonNull(row.get("created_at", OffsetDateTime.class)))
                    .updatedAt(Objects.requireNonNull(row.get("updated_at", OffsetDateTime.class)))
                    .build();
            default -> throw new IllegalArgumentException("Unknown material type: " + rowTypeId);
        };
    }

    public Flux<Material> findManyByTypeId(String typeId) {
        return databaseClient
                .sql("""
                        SELECT * FROM TABLE_MATERIAL
                        WHERE TYPE_ID = :typeId
                        """)
                .bind("typeId", typeId)
                .map(this::rowMapper)
                .all()
                .flatMap(foundMaterial -> databaseClient
                        .sql("""
                                SELECT * FROM TABLE_MATERIAL_METADATA
                                WHERE MATERIAL_ID = :materialId
                                """)
                        .bind("materialId", foundMaterial.getId())
                        .map((row, rowMetadata) -> {
                            String key = row.get("MAP_KEY", String.class);
                            String value = row.get("MAP_VALUE", String.class);
                            foundMaterial.getMetadata().put(key, value);
                            return foundMaterial;
                        })
                        .all()
                        .then(Mono.just(foundMaterial))
                );


    }

    public Mono<Material> findOneById(UUID id) {
        return databaseClient
                .sql("""
                        SELECT * FROM TABLE_MATERIAL
                        WHERE ID = :id
                        LIMIT 1
                        """)
                .bind("id", id)
                .map(this::rowMapper)
                .one()
                .flatMap(foundMaterial -> databaseClient
                        .sql("""
                                SELECT * FROM TABLE_MATERIAL_METADATA
                                WHERE MATERIAL_ID = :materialId
                                """)
                        .bind("materialId", foundMaterial.getId())
                        .map((row, rowMetadata) -> {
                            String key = row.get("MAP_KEY", String.class);
                            String value = row.get("MAP_VALUE", String.class);
                            foundMaterial.getMetadata().put(key, value);
                            return foundMaterial;
                        })
                        .all()
                        .then(Mono.just(foundMaterial))
                );
    }

    public Mono<Void> saveOne(Material material) {
        return databaseClient
                .sql("""
                        INSERT INTO TABLE_MATERIAL (ID, TYPE_ID, NAME, DESCRIPTION, IS_BORROWED, CREATED_AT, UPDATED_AT)
                        VALUES (:id, :typeId, :name, :description, :isBorrowed, :createdAt, :updatedAt)
                        """)
                .bind("id", material.getId())
                .bind("typeId", material.getTypeId())
                .bind("name", material.getName())
                .bind("description", material.getDescription())
                .bind("isBorrowed", material.getIsBorrowed())
                .bind("createdAt", material.getCreatedAt())
                .bind("updatedAt", material.getUpdatedAt())
                .fetch()
                .rowsUpdated()
                .then(Flux
                        .fromIterable(material.getMetadata().entrySet())
                        .flatMap(entry -> {
                            OffsetDateTime now = OffsetDateTime.now();
                            return databaseClient
                                    .sql("""
                                            INSERT INTO TABLE_MATERIAL_METADATA (ID, MATERIAL_ID, MAP_KEY, MAP_VALUE, CREATED_AT, UPDATED_AT)
                                            VALUES (:id, :materialId, :mapKey, :mapValue, :createdAt, :updatedAt)
                                            """)
                                    .bind("id", UUID.randomUUID())
                                    .bind("materialId", material.getId())
                                    .bind("mapKey", entry.getKey())
                                    .bind("mapValue", entry.getValue())
                                    .bind("createdAt", now)
                                    .bind("updatedAt", now)
                                    .fetch()
                                    .rowsUpdated();
                        })
                        .then()
                );
    }

    public Mono<Void> updateOneById(UUID idToUpdate, Material material) {
        return databaseClient
                .sql("""
                        UPDATE TABLE_MATERIAL
                        SET ID = :id, TYPE_ID = :typeId, NAME = :name, DESCRIPTION = :description, IS_BORROWED = :isBorrowed, UPDATED_AT = :updatedAt, CREATED_AT = :createdAt
                        WHERE ID = :idToUpdate
                        """)
                .bind("id", material.getId())
                .bind("typeId", material.getTypeId())
                .bind("name", material.getName())
                .bind("description", material.getDescription())
                .bind("isBorrowed", material.getIsBorrowed())
                .bind("createdAt", material.getCreatedAt())
                .bind("updatedAt", material.getUpdatedAt())
                .bind("idToUpdate", idToUpdate)
                .fetch()
                .rowsUpdated()
                .then(Flux
                        .fromIterable(material.getMetadata().entrySet())
                        .flatMap(entry -> databaseClient
                                .sql("""
                                        UPDATE TABLE_MATERIAL_METADATA
                                        SET MAP_VALUE = :mapValue, UPDATED_AT = :updatedAt
                                        WHERE MATERIAL_ID = :materialId AND MAP_KEY = :mapKey
                                        """)
                                .bind("materialId", material.getId())
                                .bind("mapKey", entry.getKey())
                                .bind("mapValue", entry.getValue())
                                .bind("updatedAt", OffsetDateTime.now())
                                .fetch()
                                .rowsUpdated()
                        )
                        .then()
                );
    }

    public Mono<Void> deleteOneById(UUID id) {
        return databaseClient
                .sql("""
                        DELETE FROM TABLE_MATERIAL
                        WHERE ID = :id
                        """)
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .then();
    }
}
