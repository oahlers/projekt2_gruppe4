
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


-- Dummy data, Det er de samme objekter som bruges i InitData.
INSERT INTO products (name, description, price, img) VALUES
                                                         ('Øl', 'Den kan drikkes', 18, 'øl.jpg'),
                                                         ('Cider', 'Den kan drikkes', 18, 'cider.jpeg'),
                                                         ('Faxe', 'Den kan drikkes', 18, 'faxe.jpg'),
                                                         ('Cola', 'Den kan drikkes', 18, 'cola.jpg'),
                                                         ('Sprite', 'Den kan drikkes', 18, 'sprite.webp');