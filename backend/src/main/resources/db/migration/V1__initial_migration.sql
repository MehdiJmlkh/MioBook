CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    address_id BIGINT NOT NULL,
    CONSTRAINT fk_users_address
        FOREIGN KEY (address_id) REFERENCES addresses(id)
);

CREATE TABLE admins (
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_admins_user
        FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_customers_user
        FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    pen_name VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    born DATE NOT NULL,
    died DATE,
    image_link VARCHAR(255) NOT NULL,
    admin_id BIGINT NOT NULL,
    CONSTRAINT fk_authors_admin
        FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    publisher VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    price INT NOT NULL,
    synopsis TEXT NOT NULL,
    content TEXT NOT NULL,
    total_buys BIGINT DEFAULT 0,
    author_id BIGINT NOT NULL,
    admin_id BIGINT NOT NULL,
    CONSTRAINT fk_books_author
        FOREIGN KEY (author_id) REFERENCES authors(id),
    CONSTRAINT fk_books_admin
        FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE genres (
    id TINYINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book_genres (
    book_id BIGINT NOT NULL,
    genre_id TINYINT NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    CONSTRAINT fk_book_genres_book
        FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT fk_book_genres_genre
        FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE wallets (
    customer_id BIGINT PRIMARY KEY,
    balance INT DEFAULT 0,
    CONSTRAINT fk_wallets_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rate TINYINT NOT NULL,
    comment TEXT NOT NULL,
    date DATE NOT NULL,
    customer_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    CONSTRAINT fk_reviews_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_reviews_book
        FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

CREATE TABLE purchased_books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_borrowed BOOLEAN NOT NULL DEFAULT FALSE,
    borrow_days TINYINT,
    price INT NOT NULL,
    date DATETIME NOT NULL,
    book_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_purchased_books_book
        FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE RESTRICT,
    CONSTRAINT fk_purchased_books_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_borrowed BOOLEAN NOT NULL DEFAULT FALSE,
    borrow_days TINYINT,
    price INT NOT NULL,
    book_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_cart_items_book
        FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_cart_items_customer
        FOREIGN KEY (customer_id) REFERENCES customers(id)
);
