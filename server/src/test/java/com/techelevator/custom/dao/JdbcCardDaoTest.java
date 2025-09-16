package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcCardDaoTest extends BaseDaoTest {
    private static final Card CARD_1 = new Card(1, "Bulbasaur", 1);
    private static final Card CARD_2 = new Card(2, "Charmander", 2);
    private static final Card CARD_3 = new Card(3, "Squirtle", 3);
    private JdbcCardDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcCardDao(jdbcTemplate);
    }

    @Test
    public void getCards_returns_correct_list_of_cards() {
        List<Card> cards = dao.getCards();

        assertNotNull(cards, "getCards returned a null list of cards");
        assertEquals(3, cards.size(), "getCards returned a list with the incorrect number of cards");
        assertEquals(CARD_1.getName(), cards.get(0).getName(), "getCards returned a list in incorrect order");
        assertEquals(CARD_2.getName(), cards.get(1).getName(), "getCards returned a list in incorrect order");
        assertEquals(CARD_3.getName(), cards.get(2).getName(), "getCards returned a list in incorrect order");
    }

    @Test
    public void getCardById_with_valid_id_returns_correct_card() {
        Card actualCard = dao.getCardById(CARD_1.getId());
        assertEquals(CARD_1.getName(), actualCard.getName(), "getCardById with valid id did not return correct card");
    }

    @Test
    public void getCardById_with_invalid_id_returns_null() {
        Card card = dao.getCardById(-1);
        assertNull(card, "getCardById with invalid cardId did not return null card");
    }

    @Test
    public void createCard_should_create_card() {
        Card newCard = new Card(4, "Pidgey", 1);

        Card card = dao.createCard(newCard);
        assertNotNull(card, "Call to create should return non-null card");

        Card actualCard = dao.getCardById(card.getId());
        assertNotNull(actualCard, "Call to getCardById after call to create should return non-null card");

        newCard.setId(actualCard.getId());
        assertEquals(newCard.getName(), actualCard.getName());
    }

    @Test
    public void createCard_with_no_name_should_throw_error() {
        try {
            dao.createCard(new Card(4, null, 1));
            fail("Expected createCard() with null name to throw DaoException, but it didn't throw any exception");
        } catch (DaoException e) {
            // expected condition
        } catch (Exception e) {
            fail("Expected createCard() with null name to throw DaoException, but threw a different exception");
        }
    }
}
