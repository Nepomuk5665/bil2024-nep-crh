DROP TABLE IF EXISTS product;

CREATE TABLE product (
    product_id UUID PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    price NUMERIC(10, 2)
);