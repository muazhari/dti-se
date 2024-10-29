CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "account" CASCADE;
CREATE TABLE "account"
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name         TEXT,
    phone_number TEXT
);

DROP TABLE IF EXISTS "product" CASCADE;
CREATE TABLE "product"
(
    id    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name  TEXT,
    price NUMERIC,
    stock INTEGER
);

DROP TABLE IF EXISTS "product_metadata" CASCADE;
CREATE TABLE "product_metadata"
(
    product_id UUID REFERENCES "product" (id),
    key        TEXT,
    value      TEXT,
    PRIMARY KEY (product_id, key)
);

DROP TABLE IF EXISTS "product_snapshot" CASCADE;
CREATE TABLE "product_snapshot"
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id UUID REFERENCES "product" (id),
    name       TEXT,
    price      NUMERIC,
    stock      INTEGER
);

DROP TABLE IF EXISTS "product_metadata_snapshot" CASCADE;
CREATE TABLE "product_metadata_snapshot"
(
    product_snapshot_id UUID REFERENCES "product_snapshot" (id),
    key                 TEXT,
    value               TEXT,
    PRIMARY KEY (product_snapshot_id, key)
);

DROP TABLE IF EXISTS "order" CASCADE;
CREATE TABLE "order"
(
    id               UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_number   TEXT UNIQUE,
    seller_id        UUID REFERENCES "account" (id),
    buyer_id         UUID REFERENCES "account" (id),
    purchase_date    TIMESTAMPTZ,
    shipping_address TEXT,
    payment_method   TEXT,
    total_amount     NUMERIC,
    service_fee      NUMERIC,
    app_fee          NUMERIC
);

DROP TABLE IF EXISTS "order_item" CASCADE;
CREATE TABLE "order_item"
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id            UUID REFERENCES "order" (id),
    product_snapshot_id UUID REFERENCES "product_snapshot" (id),
    quantity            INTEGER
);

DROP TABLE IF EXISTS "shipping_info" CASCADE;
CREATE TABLE "shipping_info"
(
    order_id       UUID PRIMARY KEY REFERENCES "order" (id),
    courier        TEXT,
    shipping_cost  NUMERIC,
    insurance_cost NUMERIC
);

DROP TABLE IF EXISTS "payment_method" CASCADE;
CREATE TABLE "payment_method"
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT
);

DROP TABLE IF EXISTS "payment" CASCADE;
CREATE TABLE "payment"
(
    order_id       UUID PRIMARY KEY REFERENCES "order" (id),
    payment_date   TIMESTAMPTZ,
    payment_method UUID REFERENCES "payment_method" (id)
);

DROP TABLE IF EXISTS "promotion" CASCADE;
CREATE TABLE "promotion"
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code            TEXT UNIQUE,
    discount_amount NUMERIC,
    gopay_coins     NUMERIC
);

DROP TABLE IF EXISTS "order_promotion" CASCADE;
CREATE TABLE "order_promotion"
(
    order_id     UUID REFERENCES "order" (id),
    promotion_id UUID REFERENCES "promotion" (id),
    PRIMARY KEY (order_id, promotion_id)
);

--Insert data based on the invoice shown, as an example:

INSERT INTO "account" (name, phone_number)
VALUES ('COC Komputer', NULL),
       ('Sum Ting Wong', '6281312341234');

INSERT INTO "product" (name, price, stock)
VALUES ('SAPPHIRE NITRO+ Radeon RX 7900 XTX 24GB', 20500000, 100);

INSERT INTO "product_metadata" (product_id, key, value)
VALUES ((SELECT id FROM "product" WHERE name = 'SAPPHIRE NITRO+ Radeon RX 7900 XTX 24GB'), 'weight', '5 kg');

INSERT INTO "product_snapshot" (product_id, name, price, stock)
SELECT id, name, price, stock
FROM "product"
WHERE name = 'SAPPHIRE NITRO+ Radeon RX 7900 XTX 24GB';

INSERT INTO "product_metadata_snapshot" (product_snapshot_id, key, value)
SELECT ps.id, pm.key, pm.value
FROM "product_metadata" pm,
     "product_snapshot" ps
WHERE ps.name = 'SAPPHIRE NITRO+ Radeon RX 7900 XTX 24GB';

INSERT INTO "order" (invoice_number, seller_id, buyer_id, purchase_date, shipping_address, payment_method, total_amount,
                     service_fee, app_fee)
SELECT 'INV/20330111/MPL/3694776524',
       s.id,
       b.id,
       '2024-05-22T08:50:00+07:00',
       'Digital Park, Sambau, Kecamatan Nongsa, Kota Batam, Kepulauan Riau 29466',
       'BCA Virtual Account',
       20685000,
       1000,
       1000
FROM "account" s,
     "account" b
WHERE s.name = 'COC Komputer'
  AND b.name = 'Sum Ting Wong';

INSERT INTO "order_item" (order_id, product_snapshot_id, quantity)
SELECT o.id, ps.id, 1
FROM "order" o,
     "product_snapshot" ps
WHERE o.invoice_number = 'INV/20330111/MPL/3694776524'
  AND ps.name = 'SAPPHIRE NITRO+ Radeon RX 7900 XTX 24GB';

INSERT INTO "shipping_info" (order_id, courier, shipping_cost, insurance_cost)
SELECT o.id, 'J&T - Reguler', 126000, 57700
FROM "order" o
WHERE o.invoice_number = 'INV/20330111/MPL/3694776524';

INSERT INTO "promotion" (code, discount_amount, gopay_coins)
VALUES ('DDDT845', NULL, 986385);

INSERT INTO "order_promotion" (order_id, promotion_id)
SELECT o.id, p.id
FROM "order" o,
     "promotion" p
WHERE o.invoice_number = 'INV/20330111/MPL/3694776524'
  AND p.code = 'DDDT845';

INSERT INTO "payment_method" (name)
VALUES ('BCA Virtual Account'),
       ('GoPay Coins');

INSERT INTO "payment" (order_id, payment_date, payment_method)
SELECT o.id, '2024-05-22T08:50:00+07:00', pm.id
FROM "order" o,
     "payment_method" pm
WHERE o.invoice_number = 'INV/20330111/MPL/3694776524'
  AND pm.name = 'BCA Virtual Account';

--Select all data from the tables:
SELECT *
FROM "account"
         INNER JOIN "order" ON "account".id in ("order".seller_id, "order".buyer_id)
         INNER JOIN "order_item" ON "order".id = "order_item".order_id
         INNER JOIN "product_snapshot" ON "order_item".product_snapshot_id = "product_snapshot".id
         INNER JOIN "product_metadata_snapshot"
                    ON "product_snapshot".id = "product_metadata_snapshot".product_snapshot_id
         INNER JOIN "product" ON "product_snapshot".product_id = "product".id
         INNER JOIN "shipping_info" ON "order".id = "shipping_info".order_id
         INNER JOIN "order_promotion" ON "order".id = "order_promotion".order_id
         INNER JOIN "promotion" ON "order_promotion".promotion_id = "promotion".id
         INNER JOIN "payment" ON "order".id = "payment".order_id;

