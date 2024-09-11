package org.dti.se.usecases;

import org.dti.se.models.Ticket;
import org.dti.se.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.SortedMap;
import java.util.UUID;

@Service
public class TicketManagement {

    @Autowired
    private TicketRepository ticketRepository;

    public Mono<Void> createTicket(Ticket ticketToCreate) {
        return ticketRepository.save(ticketToCreate);
    }

    public Mono<Void> bookTicket(UUID ticketId, UUID userId, SortedMap<String, String> details) {
        // Logic to book a ticket.
        return null;
    }

    public Mono<Void> confirmTicket(UUID ticketId, UUID userId) {
        // Logic to confirm a ticket.
        return null;
    }
}
