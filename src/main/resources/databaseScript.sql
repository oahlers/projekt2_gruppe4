DROP DATABASE IF EXISTS ProductList;
CREATE DATABASE ProductList;
USE ProductList;

CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description VARCHAR(100) NOT NULL,
                          price DOUBLE NOT NULL,
                          img VARCHAR(255)
);

INSERT INTO products (name, description, price, img) VALUES
                                                         ('Øl', 'Den kan drikkes', 18, 'øl.jpg'),
                                                         ('Cider', 'Den kan drikkes', 18, 'cider.jpeg'),
                                                         ('Faxe', 'Den kan drikkes', 18, 'faxe.jpg'),
                                                         ('Cola', 'Den kan drikkes', 18, 'cola.jpg'),
                                                         ('Sprite', 'Den kan drikkes', 18, 'sprite.webp');

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(100) NOT NULL
);

INSERT INTO users (username, password) VALUES
                                           ('user1', 'password1'),
                                           ('user2', 'password2');