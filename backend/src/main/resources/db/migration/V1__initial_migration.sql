CREATE TABLE addresses (
    id BIGINT PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    address_id BIGINT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);

CREATE TABLE admins (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES users(id)
);

CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
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
    FOREIGN KEY (admin_id) REFERENCES admins(id)
);

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    publisher VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    synopsis TEXT NOT NULL,
    content TEXT NOT NULL,
    total_buys BIGINT DEFAULT 0,
    author_id BIGINT NOT NULL,
    admin_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id),
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
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE wallets (
    customer_id BIGINT PRIMARY KEY,
    balance DECIMAL(10,2) DEFAULT 0,
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
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

CREATE TABLE purchased_books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_borrowed BOOLEAN NOT NULL DEFAULT FALSE,
    borrow_days TINYINT,
    price DECIMAL(10,2) NOT NULL,
    date DATETIME NOT NULL,
    book_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE RESTRICT,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_borrowed BOOLEAN NOT NULL DEFAULT FALSE,
    borrow_days TINYINT,
    price DECIMAL(10,2) NOT NULL,
    book_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_cart_items_customers
        FOREIGN KEY (customer_id) REFERENCES customers(id)
);
