CREATE TABLE purchases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_cost INT NOT NULL DEFAULT 0,
    date DATETIME NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_purchases_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id)
);

ALTER TABLE purchased_books
DROP FOREIGN KEY fk_purchased_books_customer,
    CHANGE COLUMN customer_id purchase_id BIGINT NOT NULL,
    RENAME TO purchased_items;

ALTER TABLE purchased_items
    ADD CONSTRAINT fk_purchased_items_purchase
        FOREIGN KEY (purchase_id) REFERENCES purchases(id);
