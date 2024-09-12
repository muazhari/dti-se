package org.dti.se.module1session9.usecases;

import org.dti.se.module1session9.exceptions.TaskNotFoundException;
import org.dti.se.module1session9.models.Task;
import org.dti.se.module1session9.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TaskUseCase {

    @Autowired
    private TaskRepository taskRepository;

    public Mono<Task> findOneById(UUID taskId) {
        return taskRepository
                .findOneById(taskId)
                .switchIfEmpty(Mono.error(new TaskNotFoundException()));
    }

    public Flux<Task> findManyByUserId(UUID userId) {
        return taskRepository.findManyByUserId(userId);
    }

    public Mono<Task> createOne(UUID userId, String name, String description) {
        OffsetDateTime now = OffsetDateTime.now();
        Task task = Task
                .builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .name(name)
                .description(description)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return taskRepository
                .saveOne(task)
                .thenReturn(task);
    }

    public Mono<Task> updateOne(UUID taskId, String name, String description) {
        return taskRepository
                .findOneById(taskId)
                .flatMap(task -> {
                    OffsetDateTime now = OffsetDateTime.now();
                    Task updatedTask = Task
                            .builder()
                            .id(taskId)
                            .userId(task.getUserId())
                            .name(name)
                            .description(description)
                            .createdAt(task.getCreatedAt())
                            .updatedAt(now)
                            .build();
                    return taskRepository
                            .updateOne(updatedTask)
                            .thenReturn(updatedTask);
                });
    }

    public Mono<Void> deleteOne(UUID taskId) {
        return taskRepository.deleteOne(taskId);
    }

}
