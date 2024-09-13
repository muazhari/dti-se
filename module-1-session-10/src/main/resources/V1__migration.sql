drop table if exists table_material_type cascade;
create table table_material_type
(
    id          TEXT PRIMARY KEY         NOT NULL,
    description TEXT                     NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL
);


drop table if exists table_material cascade;
create table table_material
(
    id          UUID PRIMARY KEY         NOT NULL,
    type_id     TEXT                     NOT NULL REFERENCES table_material_type (id) ON DELETE CASCADE ON UPDATE CASCADE,
    name        TEXT                     NOT NULL,
    description TEXT                     NOT NULL,
    is_borrowed BOOLEAN                  NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

drop table if exists table_material_metadata cascade;
create table table_material_metadata
(
    id          UUID PRIMARY KEY         NOT NULL,
    material_id UUID                     NOT NULL REFERENCES table_material (id) ON DELETE CASCADE ON UPDATE CASCADE,
    map_key     TEXT                     NOT NULL,
    map_value   TEXT                     NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL
);

INSERT INTO table_material_type (id, description, created_at, updated_at)
VALUES ('book', 'description0', now(), now()),
       ('disc', 'description1', now(), now());