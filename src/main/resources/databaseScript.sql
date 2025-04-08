DROP DATABASE IF EXISTS ProductList;
CREATE DATABASE ProductList;
USE ProductList;

CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description VARCHAR(100) NOT NULL,
                          price DOUBLE NOT NULL
);

INSERT INTO products (name, description, price) VALUES
                                                         ('Øl', 'Den kan drikkes', 18);

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

INSERT INTO users (username, password) VALUES
                                           ('user1', 'password1');

CREATE TABLE wishlists (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           user_id INT NOT NULL,
                           name VARCHAR(100) NOT NULL,
                           description VARCHAR(255),
                           pincode VARCHAR(255),
                           share_token VARCHAR(100) UNIQUE,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE wishlist_products (
                                   wishlist_id INT NOT NULL,
                                   product_id INT NOT NULL,
                                   PRIMARY KEY (wishlist_id, product_id),
                                   FOREIGN KEY (wishlist_id) REFERENCES wishlists(id) ON DELETE CASCADE,
                                   FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

