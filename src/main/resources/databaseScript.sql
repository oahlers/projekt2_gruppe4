DROP DATABASE IF EXISTS ProductList;
CREATE DATABASE ProductList;
USE ProductList;

-- Products table (uændret)
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

-- Users table (uændret)
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

INSERT INTO users (username, password) VALUES
                                           ('user1', 'password1'),
                                           ('user2', 'password2');

-- Wishlists table (ny)
CREATE TABLE wishlists (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           user_id INT NOT NULL,
                           name VARCHAR(100) NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Wishlist_products table (ny - mange-til-mange relation)
CREATE TABLE wishlist_products (
                                   wishlist_id INT NOT NULL,
                                   product_id INT NOT NULL,
                                   PRIMARY KEY (wishlist_id, product_id),
                                   FOREIGN KEY (wishlist_id) REFERENCES wishlists(id) ON DELETE CASCADE,
                                   FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Testdata til wishlists og wishlist_products
INSERT INTO wishlists (user_id, name) VALUES
                                          (1, 'user1s drikkeliste'),
                                          (2, 'user2s favoritdrikke');

INSERT INTO wishlist_products (wishlist_id, product_id) VALUES
                                                            (1, 1), -- Øl i user1s drikkeliste
                                                            (1, 2), -- Cider i user1s drikkeliste
                                                            (2, 3), -- Faxe i user2s favoritdrikke
                                                            (2, 4); -- Cola i user2s favoritdrikke