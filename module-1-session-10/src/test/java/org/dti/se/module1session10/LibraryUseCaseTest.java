package org.dti.se.module1session10;

import org.dti.se.module1session10.models.Book;
import org.dti.se.module1session10.models.Disc;
import org.dti.se.module1session10.models.Material;
import org.dti.se.module1session10.models.MaterialType;
import org.dti.se.module1session10.repositories.MaterialRepository;
import org.dti.se.module1session10.repositories.MaterialTypeRepository;
import org.dti.se.module1session10.usecases.LibraryUseCase;
import org.dti.se.module1session10.usecases.MaterialUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class LibraryUseCaseTest {

    @Autowired
    private MaterialTypeRepository materialTypeRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialUseCase materialUseCase;

    @Autowired
    private LibraryUseCase libraryUseCase;

    List<Material> materialFakes = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();

        materialFakes.addAll(List.of(
                Book
                        .builder()
                        .id(UUID.randomUUID())
                        .typeId(MaterialType.BOOK)
                        .name("name0")
                        .description("description0")
                        .isBorrowed(false)
                        .metadata(Map.of("key0", "value0", "key1", "value1"))
                        .createdAt(now)
                        .updatedAt(now)
                        .build(),
                Disc
                        .builder()
                        .id(UUID.randomUUID())
                        .typeId(MaterialType.DISC)
                        .name("name1")
                        .description("description1")
                        .isBorrowed(true)
                        .metadata(Map.of("key0", "value0", "key1", "value1"))
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        ));

        for (Material material : materialFakes) {
            StepVerifier
                    .create(materialRepository.saveOne(material))
                    .expectNextCount(0)
                    .verifyComplete();
        }
    }

    @AfterEach
    public void tearDown() {
        for (Material material : materialFakes) {
            StepVerifier
                    .create(materialRepository.deleteOneById(material.getId()))
                    .expectNextCount(0)
                    .verifyComplete();
        }
    }

    @Test
    public void testBorrowOneMaterial() {
        Material material = materialFakes.get(0);
        StepVerifier
                .create(libraryUseCase.borrowOneMaterialById(material.getId()))
                .assertNext(returnedMaterial -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    assert returnedMaterial.getIsBorrowed().equals(true);
                    assert returnedMaterial.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();
    }

    @Test
    public void testReturnOneMaterial() {
        Material material = materialFakes.get(1);
        StepVerifier
                .create(libraryUseCase.returnOneMaterialById(material.getId()))
                .assertNext(returnedMaterial -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    assert returnedMaterial.getIsBorrowed().equals(false);
                    assert returnedMaterial.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();
    }

}
