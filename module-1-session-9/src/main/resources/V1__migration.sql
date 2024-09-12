drop table if exists table_user cascade;
create table table_user
(
    id         UUID PRIMARY KEY         NOT NULL,
    name       TEXT                     NOT NULL,
    email      TEXT                     NOT NULL,
    password   TEXT                     NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

drop table if exists table_event cascade;
create table table_event
(
    id              UUID PRIMARY KEY         NOT NULL,
    name            TEXT                     NOT NULL,
    description     TEXT                     NOT NULL,
    creator_user_id UUID                     NOT NULL REFERENCES table_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITH TIME ZONE NOT NULL
);

drop table if exists table_ticket cascade;
create table table_ticket
(
    id          UUID PRIMARY KEY         NOT NULL,
    event_id    UUID                     NOT NULL REFERENCES table_event (id) ON DELETE CASCADE ON UPDATE CASCADE,
    price       NUMERIC                  NOT NULL,
    quantity    NUMERIC                  NOT NULL,
    name        TEXT                     NOT NULL,
    description TEXT                     NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL
);


drop table if exists table_booked_ticket cascade;
create table table_booked_ticket
(
    id           UUID PRIMARY KEY         NOT NULL,
    ticket_id    UUID                     NOT NULL REFERENCES table_ticket (id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id      UUID                     NOT NULL REFERENCES table_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    is_confirmed BOOLEAN                  NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE NOT NULL
);

drop table if exists table_booked_ticket_detail cascade;
create table table_booked_ticket_detail
(
    id               UUID PRIMARY KEY         NOT NULL,
    booked_ticket_id UUID                     NOT NULL REFERENCES table_booked_ticket (id) ON DELETE CASCADE ON UPDATE CASCADE,
    map_key          TEXT                     NOT NULL,
    map_value        TEXT                     NOT NULL,
    map_index        NUMERIC                  NOT NULL,
    created_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITH TIME ZONE NOT NULL
);



