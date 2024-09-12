package org.dti.se.module1session8;

import org.dti.se.module1session8.models.BookedTicket;
import org.dti.se.module1session8.models.Event;
import org.dti.se.module1session8.models.Ticket;
import org.dti.se.module1session8.models.User;
import org.dti.se.module1session8.repositories.BookedTicketRepository;
import org.dti.se.module1session8.repositories.EventRepository;
import org.dti.se.module1session8.repositories.TicketRepository;
import org.dti.se.module1session8.repositories.UserRepository;
import org.dti.se.module1session8.usecases.TicketUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.util.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TicketUseCaseTest {

    @Autowired
    private TicketUseCase ticketUseCase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookedTicketRepository bookedTicketRepository;

    User user;
    Event event;
    Ticket ticket;
    BookedTicket bookedTicket;

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();

        user = User
                .builder()
                .id(UUID.randomUUID())
                .name("name1")
                .email("email1")
                .password("password1")
                .createdAt(now)
                .updatedAt(now)
                .build();

        event = Event
                .builder()
                .id(UUID.randomUUID())
                .name("name1")
                .description("description1")
                .creatorUserId(user.getId())
                .createdAt(now)
                .updatedAt(now)
                .build();


        ticket = Ticket
                .builder()
                .id(UUID.randomUUID())
                .eventId(event.getId())
                .name("name1")
                .description("description1")
                .price(1.0)
                .quantity(10.0)
                .createdAt(now)
                .updatedAt(now)
                .build();

        bookedTicket = BookedTicket
                .builder()
                .id(UUID.randomUUID())
                .ticketId(ticket.getId())
                .userId(user.getId())
                .isConfirmed(false)
                .details(new LinkedHashMap<>(Map.of("key1", "value1", "key2", "value2")))
                .createdAt(now)
                .updatedAt(now)
                .build();

        StepVerifier
                .create(userRepository.saveOne(user))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(eventRepository.saveOne(event))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(ticketRepository.saveOne(ticket))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(bookedTicketRepository.saveOne(bookedTicket))
                .expectNextCount(0)
                .verifyComplete();
    }

    @AfterEach
    public void tearDown() {
        StepVerifier
                .create(userRepository.deleteOne(user.getId()))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(eventRepository.deleteOne(event.getId()))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(ticketRepository.deleteOne(ticket.getId()))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(bookedTicketRepository.deleteOne(bookedTicket.getId()))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void createOneTicket() {
        Ticket ticket = Ticket
                .builder()
                .id(UUID.randomUUID())
                .eventId(event.getId())
                .name("name1")
                .description("description1")
                .price(1.0)
                .quantity(10.0)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        StepVerifier
                .create(ticketUseCase.createOneTicket(ticket))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(ticketRepository.findOneById(ticket.getId()))
                .expectNext(ticket)
                .verifyComplete();
    }

    @Test
    public void bookOneTicket() {
        LinkedHashMap<String, String> details = new LinkedHashMap<>();
        details.put("key1", "value1");
        details.put("key2", "value2");

        StepVerifier
                .create(ticketUseCase.bookOneTicket(ticket.getId(), user.getId(), details))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(bookedTicketRepository.findOneByTicketId(ticket.getId()))
                .assertNext(bookOneTicket -> {
                    assert bookOneTicket.getTicketId().equals(ticket.getId());
                    assert bookOneTicket.getUserId().equals(user.getId());
                    assert bookOneTicket.getDetails().equals(details);
                })
                .verifyComplete();

        StepVerifier
                .create(ticketRepository.findOneById(ticket.getId()))
                .assertNext(bookOneTicket -> {
                    assert bookOneTicket.getId().equals(ticket.getId());
                    assert bookOneTicket.getEventId().equals(ticket.getEventId());
                    assert bookOneTicket.getName().equals(ticket.getName());
                    assert bookOneTicket.getDescription().equals(ticket.getDescription());
                    assert bookOneTicket.getPrice().equals(ticket.getPrice());
                    assert bookOneTicket.getQuantity().equals(ticket.getQuantity() - 1);
                    assert bookOneTicket.getUpdatedAt().isAfter(ticket.getUpdatedAt());
                    assert bookOneTicket.getCreatedAt().equals(ticket.getCreatedAt());
                })
                .verifyComplete();
    }

    @Test
    public void confirmOneTicket() {
        StepVerifier
                .create(ticketUseCase.confirmOneTicket(ticket.getId(), user.getId()))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(bookedTicketRepository.findOneByTicketId(ticket.getId()))
                .assertNext(bookOneTicket -> {
                    assert bookOneTicket.getTicketId().equals(ticket.getId());
                    assert bookOneTicket.getUserId().equals(user.getId());
                    assert bookOneTicket.getIsConfirmed();
                })
                .verifyComplete();
    }
}
