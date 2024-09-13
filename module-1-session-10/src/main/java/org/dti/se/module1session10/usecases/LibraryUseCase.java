package org.dti.se.module1session10.usecases;

import org.dti.se.module1session10.exceptions.MaterialBorrowedException;
import org.dti.se.module1session10.exceptions.MaterialNotBorrowedException;
import org.dti.se.module1session10.models.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class LibraryUseCase {

    @Autowired
    private MaterialUseCase materialUseCase;

    public Mono<Material> borrowOneMaterialById(UUID id) {
        return materialUseCase
                .findOneById(id)
                .flatMap(foundMaterial -> {
                    if (foundMaterial.getIsBorrowed().equals(true)) {
                        return Mono.error(new MaterialBorrowedException());
                    }
                    return materialUseCase
                            .updateOneById(
                                    id,
                                    foundMaterial.getName(),
                                    foundMaterial.getDescription(),
                                    true,
                                    foundMaterial.getMetadata()
                            );
                });
    }

    public Mono<Material> returnOneMaterialById(UUID id) {
        return materialUseCase
                .findOneById(id)
                .flatMap(foundMaterial -> {
                    if (foundMaterial.getIsBorrowed().equals(false)) {
                        return Mono.error(new MaterialNotBorrowedException());
                    }
                    return materialUseCase
                            .updateOneById(
                                    id,
                                    foundMaterial.getName(),
                                    foundMaterial.getDescription(),
                                    false,
                                    foundMaterial.getMetadata()
                            );
                });
    }

}
