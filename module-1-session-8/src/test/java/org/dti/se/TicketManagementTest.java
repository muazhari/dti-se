package org.dti.se;

import org.dti.se.models.BookedTicket;
import org.dti.se.models.Event;
import org.dti.se.models.Ticket;
import org.dti.se.models.User;
import org.dti.se.repositories.BookedTicketRepository;
import org.dti.se.repositories.EventRepository;
import org.dti.se.repositories.TicketRepository;
import org.dti.se.repositories.UserRepository;
import org.dti.se.usecases.TicketManagement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    private ConcurrentHashMap<Long, HashMap<String, Object>> contexts = new ConcurrentHashMap<>();

    @BeforeEach
    public void setUp() {
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
                .quantity(10.0)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        BookedTicket bookedTicket = BookedTicket
                .builder()
                .id(UUID.randomUUID())
                .ticketId(ticket.getId())
                .userId(user.getId())
                .isConfirmed(false)
                .details(new TreeMap<>(Map.of("key1", "value1", "key2", "value2")))
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        Long threadId = Thread.currentThread().threadId();
        HashMap<String, Object> context = new HashMap<>();
        contexts.put(threadId, context);

        context.put("user", user);
        context.put("event", event);
        context.put("ticket", ticket);
        context.put("bookedTicket", bookedTicket);

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
        HashMap<String, Object> context = contexts.get(Thread.currentThread().threadId());

        User user = (User) context.get("user");
        Event event = (Event) context.get("event");
        Ticket ticket = (Ticket) context.get("ticket");
        BookedTicket bookedTicket = (BookedTicket) context.get("bookedTicket");

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
        HashMap<String, Object> context = contexts.get(Thread.currentThread().threadId());
        Event event = (Event) context.get("event");

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
                .create(ticketManagement.createOneTicket(ticket))
                .expectNextCount(0)
                .verifyComplete();

        StepVerifier
                .create(ticketRepository.findOneById(ticket.getId()))
                .expectNext(ticket)
                .verifyComplete();
    }

    @Test
    public void bookOneTicket() {
        HashMap<String, Object> context = contexts.get(Thread.currentThread().threadId());
        User user = (User) context.get("user");
        Event event = (Event) context.get("event");
        Ticket ticket = (Ticket) context.get("ticket");

        SortedMap<String, String> details = new TreeMap<>();
        details.put("key1", "value1");
        details.put("key2", "value2");

        StepVerifier
                .create(ticketManagement.bookOneTicket(ticket.getId(), user.getId(), details))
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
        HashMap<String, Object> context = contexts.get(Thread.currentThread().threadId());
        User user = (User) context.get("user");
        Event event = (Event) context.get("event");
        Ticket ticket = (Ticket) context.get("ticket");
        BookedTicket bookedTicket = (BookedTicket) context.get("bookedTicket");

        StepVerifier
                .create(ticketManagement.confirmOneTicket(ticket.getId(), user.getId()))
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
