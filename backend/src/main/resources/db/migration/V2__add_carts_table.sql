CREATE TABLE carts (
    customer_id BIGINT PRIMARY KEY,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

ALTER TABLE cart_items
DROP FOREIGN KEY cart_items_ibfk_2,
    CHANGE COLUMN customer_id cart_id BIGINT NOT NULL,
    ADD FOREIGN KEY (cart_id) REFERENCES carts(customer_id) ON DELETE CASCADE;
