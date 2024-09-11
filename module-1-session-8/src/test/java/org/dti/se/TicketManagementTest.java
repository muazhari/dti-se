package org.dti.se;

import org.dti.se.models.Event;
import org.dti.se.models.Ticket;
import org.dti.se.models.User;
import org.dti.se.repositories.BookedTicketRepository;
import org.dti.se.repositories.EventRepository;
import org.dti.se.repositories.TicketRepository;
import org.dti.se.repositories.UserRepository;
import org.dti.se.usecases.TicketManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.util.UUID;

@SpringBootTest
public class TicketManagementTest {

    @Autowired
    private TicketManagement ticketManagement;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookedTicketRepository bookedTicketRepository;

    @Test
    public void createTicket() {
        // Given
        User user = User
                .builder()
                .id(UUID.randomUUID())
                .name("name1")
                .email("email1")
                .password("password1")
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        Event event = Event
                .builder()
                .id(UUID.randomUUID())
                .name("name1")
                .description("description1")
                .creatorUserId(user.getId())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();


        Ticket ticket = Ticket
                .builder()
                .id(UUID.randomUUID())
                .eventId(event.getId())
                .name("name1")
                .description("description1")
                .price(1.0)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        // When
        StepVerifier
                .create(userRepository.save(user))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(eventRepository.save(event))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(ticketRepository.save(ticket))
                .expectNextCount(0)
                .verifyComplete();

        // Then
        StepVerifier
                .create(ticketRepository.findOneById(ticket.getId()))
                .expectNext(ticket)
                .verifyComplete();
    }
}
