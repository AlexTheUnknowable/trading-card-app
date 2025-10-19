-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS card_type CASCADE;
DROP TABLE IF EXISTS item CASCADE;
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
	img varchar(200),
	type varchar(10) NOT NULL,
    rarity int,
    CONSTRAINT PK_card PRIMARY KEY (card_id)
);

CREATE TABLE type (
    type_id SERIAL,
    name varchar(10) NOT NULL,
    CONSTRAINT PK_type PRIMARY KEY (type_id)
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

INSERT INTO card (name, img, type, rarity) VALUES
    ('Bulbasaur', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,1),
    ('Ivysaur', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,2),
    ('Venusaur', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,3),
    ('Charmander', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,1),
    ('Charmeleon', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,2),
    ('Charizard', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,3),
    ('Squirtle', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,1),
    ('Wartortle', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,2),
    ('Blastoise', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,3),
    ('Caterpie', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,1),
    ('Metapod', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,2),
    ('Butterfree', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,3),
    ('Weedle', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,1),
    ('Kakuna', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,2),
    ('Beedrill', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,3),
    ('Pidgey', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,1),
    ('Pidgeotto', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,2),
    ('Pidgeot', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,3),
    ('Rattata', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,1),
    ('Raticate', 'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png', 'grass' ,2);

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
