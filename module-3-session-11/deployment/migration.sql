CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "account" CASCADE;
CREATE TABLE "account"
(
    id                UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role_id           TEXT        NOT NULL,
    name              TEXT        NOT NULL,
    email             TEXT UNIQUE NOT NULL,
    password          TEXT        NOT NULL,
    pin               TEXT        NOT NULL,
    profile_image_url TEXT        NOT NULL,
    created_at        TIMESTAMPTZ      DEFAULT NOW(),
    updated_at        TIMESTAMPTZ      DEFAULT NOW(),
    CONSTRAINT check_role CHECK (role_id IN ('user', 'admin'))
);


DROP TABLE IF EXISTS "forgot_password" CASCADE;
CREATE TABLE "forgot_password"
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    account_id UUID REFERENCES "account" (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    token      TEXT UNIQUE                                                        NOT NULL,
    expired_at TIMESTAMPTZ                                                        NOT NULL,
    created_at TIMESTAMPTZ      DEFAULT NOW(),
    updated_at TIMESTAMPTZ      DEFAULT NOW()
);

DROP TABLE IF EXISTS "wallet" CASCADE;
CREATE TABLE "wallet"
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    account_id UUID REFERENCES "account" (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    name       TEXT                                                               NOT NULL,
    amount     NUMERIC          DEFAULT 0.00,
    is_main    BOOLEAN          DEFAULT FALSE,
    created_at TIMESTAMPTZ      DEFAULT NOW(),
    updated_at TIMESTAMPTZ      DEFAULT NOW()
);

-- Quick retrieval and ensure only one main wallet per user.
CREATE UNIQUE INDEX unique_main_wallet_per_user ON "wallet" (account_id) WHERE is_main = TRUE;

DROP TABLE IF EXISTS "pocket" CASCADE;
CREATE TABLE "pocket"
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    wallet_id   UUID REFERENCES "wallet" (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    name        TEXT                                                              NOT NULL,
    emoji       TEXT,
    description TEXT,
    budget      NUMERIC          DEFAULT 0.00,
    spent       NUMERIC          DEFAULT 0.00,
    created_at  TIMESTAMPTZ      DEFAULT NOW(),
    updated_at  TIMESTAMPTZ      DEFAULT NOW()
);

DROP TABLE IF EXISTS "transaction" CASCADE;
CREATE TABLE "transaction"
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    wallet_id        UUID REFERENCES "wallet" (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    amount           NUMERIC                                                           NOT NULL,
    transaction_type TEXT                                                              NOT NULL,
    description      TEXT                                                              NULL,
    transaction_date TIMESTAMPTZ      DEFAULT NOW(),
    created_at       TIMESTAMPTZ      DEFAULT NOW(),
    updated_at       TIMESTAMPTZ      DEFAULT NOW(),
    CONSTRAINT check_transaction_type CHECK (transaction_type IN ('income', 'expense'))
);

DROP TABLE IF EXISTS "goal" CASCADE;
CREATE TABLE "goal"
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    wallet_id  UUID REFERENCES "wallet" (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    name       TEXT                                                              NOT NULL,
    income     NUMERIC                                                           NOT NULL,
    expense    NUMERIC                                                           NOT NULL,
    deadline   TIMESTAMPTZ,
    created_at TIMESTAMPTZ      DEFAULT NOW(),
    updated_at TIMESTAMPTZ      DEFAULT NOW()
);


-- Quick transaction retrieval by date.
CREATE INDEX transactions_date_idx ON "transaction" (transaction_date DESC);


-- Populate fake data
INSERT INTO "account" (role_id, name, email, password, pin, profile_image_url)
VALUES ('admin', 'name0', 'email0@mail.com', 'password0', '1234', 'https://placehold.co/150x150'),
       ('user', 'name1', 'email1@mail.com', 'password1', '1234', 'https://placehold.co/150x150');

INSERT INTO "wallet" (account_id, name, amount, is_main)
VALUES ((select id from "account" where email = 'email0@mail.com'), 'name0', 1000.00, TRUE),
       ((select id from "account" where email = 'email1@mail.com'), 'name1', 2000.00, TRUE);

INSERT INTO "pocket" (wallet_id, name, emoji, description, budget, spent)
VALUES ((select id from "wallet" where name = 'name0'), 'name0', '🤑', 'description0', 1000.00, 0.00),
       ((select id from "wallet" where name = 'name1'), 'name1', '🤑', 'description1', 2000.00, 0.00);

INSERT INTO "transaction" (wallet_id, amount, transaction_type, description, transaction_date)
VALUES ((select id from "wallet" where name = 'name0'), 100.00, 'income', 'description0', NOW()),
       ((select id from "wallet" where name = 'name1'), 200.00, 'income', 'description1', NOW());

INSERT INTO "goal" (wallet_id, name, income, expense, deadline)
VALUES ((select id from "wallet" where name = 'name0'), 'name0', 1000.00, 0.00, NOW()),
       ((select id from "wallet" where name = 'name1'), 'name1', 2000.00, 0.00, NOW());


select *
from "account";