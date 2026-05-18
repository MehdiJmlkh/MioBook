CREATE TABLE carts (
    customer_id BIGINT PRIMARY KEY,
    CONSTRAINT fk_carts_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

ALTER TABLE cart_items
DROP FOREIGN KEY fk_cart_items_customer,
    CHANGE COLUMN customer_id cart_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_cart_items_cart
        FOREIGN KEY (cart_id) REFERENCES carts(customer_id) ON DELETE CASCADE;
