package org.dti.se.module1session8.usecases;

import org.dti.se.module1session8.models.BookedTicket;
import org.dti.se.module1session8.models.Ticket;
import org.dti.se.module1session8.repositories.BookedTicketRepository;
import org.dti.se.module1session8.repositories.EventRepository;
import org.dti.se.module1session8.repositories.TicketRepository;
import org.dti.se.module1session8.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.UUID;

@Service
public class TicketUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookedTicketRepository bookedTicketRepository;

    public Mono<Void> createOneTicket(Ticket ticketToCreate) {
        return ticketRepository.saveOne(ticketToCreate);
    }

    public Mono<Void> bookOneTicket(UUID ticketId, UUID userId, LinkedHashMap<String, String> details) {
        OffsetDateTime now = OffsetDateTime.now();
        return Mono
                .zip(ticketRepository.findOneById(ticketId), userRepository.findOneById(userId))
                .flatMap(tuple -> {
                    if (tuple.getT1().getQuantity() <= 0) {
                        return Mono.error(new RuntimeException("Ticket is sold out"));
                    }

                    tuple.getT1().setQuantity(tuple.getT1().getQuantity() - 1);
                    tuple.getT1().setUpdatedAt(now);
                    return ticketRepository
                            .updateOne(tuple.getT1())
                            .then(Mono.just(tuple));
                })
                .flatMap(tuple -> {
                    BookedTicket bookedTicket = BookedTicket
                            .builder()
                            .id(UUID.randomUUID())
                            .ticketId(ticketId)
                            .userId(userId)
                            .isConfirmed(false)
                            .details(details)
                            .createdAt(now)
                            .updatedAt(now)
                            .build();
                    return bookedTicketRepository
                            .saveOne(bookedTicket)
                            .then(Mono.just(tuple));
                })
                .then();
    }

    public Mono<Void> confirmOneTicket(UUID ticketId, UUID userId) {
        return Mono
                .zip(bookedTicketRepository.findOneByTicketId(ticketId), userRepository.findOneById(userId))
                .flatMap(tuple -> {
                    if (!tuple.getT1().getUserId().equals(userId)) {
                        return Mono.error(new RuntimeException("Ticket is not booked by this user"));
                    }

                    tuple.getT1().setIsConfirmed(true);
                    tuple.getT1().setUpdatedAt(OffsetDateTime.now());
                    return bookedTicketRepository
                            .updateOne(tuple.getT1())
                            .then(Mono.just(tuple));
                })
                .then();
    }
}
