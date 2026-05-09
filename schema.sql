-- SQL script to create the bookstore database schema

CREATE DATABASE IF NOT EXISTS bookstore_db;
USE bookstore_db;

-- Table: users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Table: books
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    cover_image VARCHAR(500)
);

-- Table: orders
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_amount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Table: order_items
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

-- Initial data (optional)
-- Password is 'admin123' encoded with BCrypt
INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7u41W3u', 'ROLE_ADMIN');
-- Password is 'user123' encoded with BCrypt
INSERT INTO users (username, password, role) VALUES ('user', '$2a$10$pLXI9y2K.N4.l9Q.r7.6.O.r7.6.O.r7.6.O.r7.6.O.r7.6.O.r7.6.O', 'ROLE_USER');

INSERT INTO books (title, author, price, quantity, cover_image) VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 15.99, 10, 'https://images.unsplash.com/photo-1543005157-865a50d459c7?q=80&w=1000');
INSERT INTO books (title, author, price, quantity, cover_image) VALUES ('To Kill a Mockingbird', 'Harper Lee', 12.50, 5, 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=1000');
