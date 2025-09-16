-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS card_type CASCADE;
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

CREATE TABLE card_type (
    card_id int NOT NULL,
    type_id int NOT NULL,
    CONSTRAINT PK_card_type PRIMARY KEY (card_id, type_id),
    CONSTRAINT FK_card_type_card FOREIGN KEY (card_id) REFERENCES card(card_id),
    CONSTRAINT FK_card_type_type FOREIGN KEY (type_id) REFERENCES type(type_id)
);

COMMIT TRANSACTION;
