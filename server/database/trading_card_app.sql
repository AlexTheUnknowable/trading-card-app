-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS card_type CASCADE;
DROP TABLE IF EXISTS item CASCADE;
DROP TABLE IF EXISTS storeitem CASCADE;
DROP TABLE IF EXISTS carditem CASCADE;
DROP TABLE IF EXISTS type CASCADE;
DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE card (
    card_id SERIAL,
    name varchar(50) NOT NULL UNIQUE,
    rarity int NOT NULL,
    CONSTRAINT PK_card PRIMARY KEY (card_id)
);

CREATE TABLE type (
    type_id SERIAL,
    name varchar(10) NOT NULL,
    CONSTRAINT PK_type PRIMARY KEY (type_id)
);

CREATE TABLE carditem (
    carditem_id SERIAL,
    user_id int NOT NULL,
    card_id int NOT NULL,
    CONSTRAINT PK_carditem PRIMARY KEY (carditem_id),
    CONSTRAINT FK_carditem_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_carditem_card FOREIGN KEY (card_id) REFERENCES card(card_id)
);

CREATE TABLE storeitem (
    storeitem_id SERIAL,
    carditem_id int NOT NULL UNIQUE,
    price numeric(10, 2) NOT NULL,
    CONSTRAINT PK_storeitem PRIMARY KEY (storeitem_id),
    CONSTRAINT FK_storeitem_carditem FOREIGN KEY(carditem_id) REFERENCES carditem(carditem_id)
);

CREATE TABLE item (
	item_id SERIAL,
	user_id int NOT NULL,
	card_id int NOT NULL,
	price numeric(10, 2),
	CONSTRAINT PK_item PRIMARY KEY (item_id),
	CONSTRAINT FK_item_user FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_item_card FOREIGN KEY (card_id) REFERENCES card(card_id)
);

CREATE TABLE card_type (
    card_id int NOT NULL,
    type_id int NOT NULL,
    CONSTRAINT PK_card_type PRIMARY KEY (card_id, type_id),
    CONSTRAINT FK_card_type_card FOREIGN KEY (card_id) REFERENCES card(card_id),
    CONSTRAINT FK_card_type_type FOREIGN KEY (type_id) REFERENCES type(type_id)
);

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO users (username, password_hash, role) VALUES
    ('user1', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('user2', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('user3', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN');

INSERT INTO card (name, rarity) VALUES
    ('Bulbasaur', 1),
    ('Ivysaur', 2),
    ('Venusaur', 3),
    ('Charmander', 1),
    ('Charmeleon', 2),
    ('Charizard', 3),
    ('Squirtle', 1),
    ('Wartortle', 2),
    ('Blastoise', 3),
    ('Caterpie', 1),
    ('Metapod', 2),
    ('Butterfree', 3),
    ('Weedle', 1),
    ('Kakuna', 2),
    ('Beedrill', 3),
    ('Pidgey', 1),
    ('Pidgeotto', 2),
    ('Pidgeot', 3),
    ('Rattata', 1),
    ('Raticate', 2);

INSERT INTO type (name) VALUES
    ('Normal'),
    ('Fighting'),
    ('Flying'),
    ('Poison'),
    ('Ground'),
    ('Rock'),
    ('Bug'),
    ('Ghost'),
    ('Steel'),
    ('Fire'),
    ('Water'),
    ('Grass'),
    ('Electric'),
    ('Psychic'),
    ('Ice'),
    ('Dragon'),
    ('Dark'),
    ('Fairy');

INSERT INTO carditem (user_id, card_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (2, 10),
    (2, 11),
    (2, 1),
    (2, 12),
    (3, 1),
    (3, 13);

INSERT INTO storeitem (carditem_id, price) VALUES
    (1, 1.00),
    (2, 2.00),
    (3, 3.00),
    (4, 1.50),
    (5, 1.50),
    (6, 2.00),
    (10, 1.00),
    (14, 1.00);

INSERT INTO item (user_id, card_id, price) VALUES
	(1, 1, 1.00),
	(1, 2, 2.00),
	(1, 3, 3.00),
	(1, 4, 1.50),
	(1, 5, 1.50),
	(1, 6, 2.00),
	(1, 7, null),
	(1, 8, null),
	(1, 9, null),
	(2, 1, null),
	(2, 10, 1.00),
	(2, 11, null),
	(2, 12, null),
	(3, 1, 1.00),
	(3, 13, null);

INSERT INTO card_type (card_id, type_id) VALUES
    (1, 12),
    (2, 12),
    (3, 12),
    (4, 10),
    (5, 10),
    (6, 10),
    (7, 11),
    (8, 11),
    (9, 11),
    (10, 7),
    (11, 7),
    (12, 7),
    (13, 7),
    (14, 7),
    (15, 7),
    (16, 3),
    (17, 3),
    (18, 3),
    (19, 1),
    (20, 1);

COMMIT TRANSACTION;
