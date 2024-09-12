drop table if exists table_user cascade;
create table table_user
(
    id         UUID PRIMARY KEY         NOT NULL,
    email      TEXT                     NOT NULL UNIQUE,
    password   TEXT                     NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

drop table if exists table_task cascade;
create table table_task
(
    id          UUID PRIMARY KEY         NOT NULL,
    user_id     UUID                     NOT NULL REFERENCES table_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    name        TEXT                     NOT NULL,
    description TEXT                     NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL
);
