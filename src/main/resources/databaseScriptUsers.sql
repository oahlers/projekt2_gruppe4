DROP DATABASE IF EXISTS users;

CREATE DATABASE users;

USE users;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(100) NOT NULL
);


INSERT INTO users (username, password) VALUES
                                           ('Alice', 'password123'),
                                           ('Bob', 'securePass'),
                                           ('Charlie', 'qwerty'),
                                           ('Dave', '123456'),
                                           ('Eve', 'letmein');
