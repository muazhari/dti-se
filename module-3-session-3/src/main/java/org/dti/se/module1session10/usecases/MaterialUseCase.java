package org.dti.se.module1session10.usecases;

import org.dti.se.module1session10.exceptions.MaterialInvalidTypeException;
import org.dti.se.module1session10.exceptions.MaterialNotFoundException;
import org.dti.se.module1session10.models.Book;
import org.dti.se.module1session10.models.Disc;
import org.dti.se.module1session10.models.Material;
import org.dti.se.module1session10.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class MaterialUseCase {

    @Autowired
    private MaterialRepository materialRepository;


    public Mono<Material> findOneById(UUID id) {
        return materialRepository
                .findOneById(id)
                .switchIfEmpty(Mono.error(new MaterialNotFoundException()));
    }

    public Flux<Material> findManyByTypeId(String typeId) {
        return materialRepository.findManyByTypeId(typeId);
    }

    public Mono<Material> createOne(String typeId, String name, String description, Map<String, String> metadata) {
        return Mono.fromCallable(() -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    return switch (typeId) {
                        case "disc" -> Disc
                                .builder()
                                .id(UUID.randomUUID())
                                .typeId(typeId)
                                .name(name)
                                .description(description)
                                .isBorrowed(false)
                                .metadata(metadata)
                                .createdAt(now)
                                .updatedAt(now)
                                .build();
                        case "book" -> Book
                                .builder()
                                .id(UUID.randomUUID())
                                .typeId(typeId)
                                .name(name)
                                .description(description)
                                .isBorrowed(false)
                                .metadata(metadata)
                                .createdAt(now)
                                .updatedAt(now)
                                .build();
                        default -> throw new MaterialInvalidTypeException();
                    };
                })
                .flatMap((material) -> materialRepository
                        .saveOne(material)
                        .thenReturn(material)
                );
    }

    public Mono<Material> updateOneById(UUID id, String name, String description, Boolean isBorrowed, Map<String, String> metadata) {
        return materialRepository
                .findOneById(id)
                .flatMap(material -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    material
                            .setName(name)
                            .setDescription(description)
                            .setIsBorrowed(isBorrowed)
                            .setMetadata(metadata)
                            .setUpdatedAt(now);
                    return materialRepository
                            .updateOneById(id, material)
                            .thenReturn(material);
                });
    }

    public Mono<Void> deleteOneById(UUID id) {
        return materialRepository.deleteOneById(id);
    }

}
