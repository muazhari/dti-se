-- extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";

-- ddl
DROP TABLE IF EXISTS cart CASCADE;
CREATE TABLE cart (
    id UUID PRIMARY KEY,
    user_id UUID
);


DROP TABLE IF EXISTS cart_item CASCADE;
CREATE TABLE cart_item (
    id UUID PRIMARY KEY,
    cart_id UUID,
    product_id UUID
);

DROP TABLE IF EXISTS product CASCADE;
CREATE TABLE product (
    id UUID PRIMARY KEY,
    name TEXT,
    price NUMERIC
);


INSERT into cart (id, user_id) VALUES (uuid_generate_v4(), uuid_generate_v4());



INSERT into product (id, name, price) VALUES (uuid_generate_v4(), 'product1', 100);
select * from cart;

select * from product;