package org.dti.se.module3session9;

import org.dti.se.module3session9.inners.models.dao.Book;
import org.dti.se.module3session9.inners.usecases.LibraryUseCase;
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
public class MaterialUseCaseTest {

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
    public void testFindOneById() {
        Material material = materialFakes.get(0);
        StepVerifier
                .create(materialUseCase.findOneById(material.getId()))
                .expectNext(material)
                .verifyComplete();
    }

    @Test
    public void testFindOneByIdNotFound() {
        UUID id = UUID.randomUUID();
        StepVerifier
                .create(materialUseCase.findOneById(id))
                .expectError(MaterialNotFoundException.class)
                .verifyThenAssertThat();
    }

    @Test
    public void testFindManyByTypeId() {
        StepVerifier
                .create(materialUseCase.findManyByTypeId(MaterialType.BOOK).collectList())
                .assertNext(foundMaterials -> {
                    for (Material material : foundMaterials) {
                        assert material.getTypeId().equals(MaterialType.BOOK);
                    }
                })
                .verifyComplete();

        StepVerifier
                .create(materialUseCase.findManyByTypeId(MaterialType.DISC).collectList())
                .assertNext(foundMaterials -> {
                    for (Material material : foundMaterials) {
                        assert material.getTypeId().equals(MaterialType.DISC);
                    }
                })
                .verifyComplete();
    }

    @Test
    public void testCreateOne() {
        Book book = Book
                .builder()
                .typeId(MaterialType.BOOK)
                .name("name2")
                .description("description2")
                .metadata(Map.of("key0", "value0", "key1", "value1"))
                .build();

        Disc disc = Disc
                .builder()
                .typeId(MaterialType.DISC)
                .name("name3")
                .description("description3")
                .metadata(Map.of("key0", "value0", "key1", "value1"))
                .build();

        StepVerifier
                .create(materialUseCase.createOne(book.getTypeId(), book.getName(), book.getDescription(), book.getMetadata()))
                .assertNext(createdBook -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    assert createdBook.getTypeId().equals(book.getTypeId());
                    assert createdBook.getName().equals(book.getName());
                    assert createdBook.getDescription().equals(book.getDescription());
                    assert createdBook.getIsBorrowed().equals(false);
                    assert createdBook.getMetadata().equals(book.getMetadata());
                    assert createdBook.getCreatedAt().isBefore(now);
                    assert createdBook.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();

        StepVerifier
                .create(materialUseCase.createOne(disc.getTypeId(), disc.getName(), disc.getDescription(), disc.getMetadata()))
                .assertNext(createdDisc -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    assert createdDisc.getTypeId().equals(disc.getTypeId());
                    assert createdDisc.getName().equals(disc.getName());
                    assert createdDisc.getDescription().equals(disc.getDescription());
                    assert createdDisc.getIsBorrowed().equals(false);
                    assert createdDisc.getMetadata().equals(disc.getMetadata());
                    assert createdDisc.getCreatedAt().isBefore(now);
                    assert createdDisc.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();
    }

    @Test
    public void testUpdateOneById() {

        UUID bookIdToUpdate = materialFakes.get(0).getId();
        Book book = Book
                .builder()
                .typeId(MaterialType.BOOK)
                .name("name0" + UUID.randomUUID())
                .description("description2" + UUID.randomUUID())
                .isBorrowed(false)
                .metadata(Map.of(
                        "key0" + UUID.randomUUID(), "value0" + UUID.randomUUID(),
                        "key1" + UUID.randomUUID(), "value1" + UUID.randomUUID()))
                .build();

        UUID discIdToUpdate = materialFakes.get(1).getId();
        Disc disc = Disc
                .builder()
                .typeId(MaterialType.DISC)
                .name("name1" + UUID.randomUUID())
                .description("description3" + UUID.randomUUID())
                .isBorrowed(true)
                .metadata(Map.of(
                        "key0" + UUID.randomUUID(), "value0" + UUID.randomUUID(),
                        "key1" + UUID.randomUUID(), "value1" + UUID.randomUUID()))
                .build();

        StepVerifier
                .create(materialUseCase.updateOneById(bookIdToUpdate, book.getName(), book.getDescription(), book.getIsBorrowed(), book.getMetadata()))
                .assertNext(updatedBook -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    assert updatedBook.getTypeId().equals(book.getTypeId());
                    assert updatedBook.getName().equals(book.getName());
                    assert updatedBook.getDescription().equals(book.getDescription());
                    assert updatedBook.getMetadata().equals(book.getMetadata());
                    assert updatedBook.getCreatedAt().isBefore(now);
                    assert updatedBook.getUpdatedAt().isBefore(now);
                })
                .verifyComplete();

        StepVerifier
                .create(materialUseCase.updateOneById(discIdToUpdate, disc.getName(), disc.getDescription(), disc.getIsBorrowed(), disc.getMetadata()))
                .assertNext(updatedDisc -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    assert updatedDisc.getTypeId().equals(disc.getTypeId());
                    assert updatedDisc.getName().equals(disc.getName());
                    assert updatedDisc.getDescription().equals(disc.getDescription());
                    assert updatedDisc.getMetadata().equals(disc.getMetadata());
                    assert updatedDisc.getCreatedAt().isBefore(now);
                    assert updatedDisc.getUpdatedAt().isBefore(now);
                });
    }

    @Test
    public void testDeleteOne() {
        Material material = materialFakes.get(0);
        StepVerifier
                .create(materialUseCase.deleteOneById(material.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }

}
