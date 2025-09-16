BEGIN TRANSACTION;

-- Users
INSERT INTO users (username, password_hash, role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user3','user3','ROLE_USER');

INSERT INTO card (name, rarity) VALUES ('Bulbasaur', 1);
INSERT INTO card (name, rarity) VALUES ('Charmander', 2);
INSERT INTO card (name, rarity) VALUES ('Squirtle', 3);

INSERT INTO type (name) VALUES ('Grass'), ('Fire'), ('Water');

INSERT INTO carditem (user_id, card_id) VALUES
    (1, 1), (1, 2),
    (2, 2), (2, 3),
    (3, 1), (3, 3);

INSERT INTO storeitem (carditem_id, price) VALUES
    (2, 1.00),
    (3, 1.00),
    (4, 2.00),
    (6, 1.50);

INSERT INTO card_type (card_id, type_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3);

COMMIT TRANSACTION;
