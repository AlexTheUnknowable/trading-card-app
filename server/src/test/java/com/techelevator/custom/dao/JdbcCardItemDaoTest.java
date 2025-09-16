package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.CardItem;
import com.techelevator.custom.model.CardItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcCardItemDaoTest extends BaseDaoTest {
    private static final CardItem CARD_ITEM_1 = new CardItem(1, 1, 1);
    private static final CardItem CARD_ITEM_2 = new CardItem(2, 1, 2);
    private static final CardItem CARD_ITEM_3 = new CardItem(3, 2, 2);
    private static final CardItem CARD_ITEM_4 = new CardItem(4, 2, 3);
    private static final CardItem CARD_ITEM_5 = new CardItem(5, 3, 1);
    private static final CardItem CARD_ITEM_6 = new CardItem(6, 3, 3);
    private JdbcCardItemDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcCardItemDao(jdbcTemplate);
    }

    @Test
    public void getAllCardItems_returns_correct_list_of_card_items() {
        List<CardItem> cardItems = dao.getAllCardItems();

        assertNotNull(cardItems, "getAllCardItems returned a null list of card items");
        assertEquals(6, cardItems.size(), "getAllCardItems returned a list with the incorrect number of card items");
        assertEquals(CARD_ITEM_1.getUserId(), cardItems.get(0).getUserId(), "getAllCardItems returned a list in incorrect order (user IDs do not match)");
        assertEquals(CARD_ITEM_1.getCardId(), cardItems.get(0).getCardId(), "getAllCardItems returned a list in incorrect order (card IDs do not match)");
        assertEquals(CARD_ITEM_2.getUserId(), cardItems.get(1).getUserId(), "getAllCardItems returned a list in incorrect order (user IDs do not match)");
        assertEquals(CARD_ITEM_2.getCardId(), cardItems.get(1).getCardId(), "getAllCardItems returned a list in incorrect order (card IDs do not match)");
        assertEquals(CARD_ITEM_3.getUserId(), cardItems.get(2).getUserId(), "getAllCardItems returned a list in incorrect order (user IDs do not match)");
        assertEquals(CARD_ITEM_3.getCardId(), cardItems.get(2).getCardId(), "getAllCardItems returned a list in incorrect order (card IDs do not match)");
        assertEquals(CARD_ITEM_4.getUserId(), cardItems.get(3).getUserId(), "getAllCardItems returned a list in incorrect order (user IDs do not match)");
        assertEquals(CARD_ITEM_4.getCardId(), cardItems.get(3).getCardId(), "getAllCardItems returned a list in incorrect order (card IDs do not match)");
        assertEquals(CARD_ITEM_5.getUserId(), cardItems.get(4).getUserId(), "getAllCardItems returned a list in incorrect order (user IDs do not match)");
        assertEquals(CARD_ITEM_5.getCardId(), cardItems.get(4).getCardId(), "getAllCardItems returned a list in incorrect order (card IDs do not match)");
        assertEquals(CARD_ITEM_6.getUserId(), cardItems.get(5).getUserId(), "getAllCardItems returned a list in incorrect order (user IDs do not match)");
        assertEquals(CARD_ITEM_6.getCardId(), cardItems.get(5).getCardId(), "getAllCardItems returned a list in incorrect order (card IDs do not match)");
    }

    @Test
    public void getCardItemById_with_valid_id_returns_correct_card_item() {
        CardItem actualCardItem = dao.getCardItemById(CARD_ITEM_1.getId());
        assertEquals(CARD_ITEM_1.getId(), actualCardItem.getId(), "getCardItemById with valid id did not return correct card item");
    }

    @Test
    public void getCardItemById_with_invalid_id_returns_null() {
        CardItem cardItem = dao.getCardItemById(-1);
        assertNull(cardItem, "getCardItemById with invalid cardItemId did not return null card item");
    }

    @Test
    public void getCardItemsByUser_given_valid_user_should_return_correct_card_item_dtos() {
        List<CardItemDto> user1CardItems = dao.getCardItemsByUser(1);

        assertNotNull(user1CardItems, "getCardItemsByUser returned a null list of card items");
        assertEquals(2, user1CardItems.size(), "getCardItemsByUser returned a list with the incorrect number of card items");
        assertEquals(CARD_ITEM_1.getId(), user1CardItems.get(0).getCardItemId(), "getCardItemsByUser returned a list in incorrect order (card item IDs do not match)");
        assertEquals(CARD_ITEM_1.getUserId(), user1CardItems.get(0).getUserId(), "getCardItemsByUser returned a list in incorrect order (user IDs do not match)");
        assertEquals(CARD_ITEM_2.getId(), user1CardItems.get(1).getCardItemId(), "getCardItemsByUser returned a list in incorrect order (card item IDs do not match)");
        assertEquals(CARD_ITEM_2.getUserId(), user1CardItems.get(1).getUserId(), "getCardItemsByUser returned a list in incorrect order (user IDs do not match)");
    }

    @Test
    public void getCardItemsByUser_given_invalid_user_should_return_empty_list() {
        List<CardItemDto> cardItems = dao.getCardItemsByUser(-1);
        List<CardItemDto> emptyList = new ArrayList<>();
        assertEquals(cardItems, emptyList, "getCardItemsByUser with invalid user id did not return empty list");
    }

    @Test
    public void createCardItem_should_create_card_item() {
        CardItem newCardItem = new CardItem(7, 1, 3);

        CardItem cardItem = dao.createCardItem(newCardItem);
        assertNotNull(cardItem, "Call to create should return non-null card item");

        CardItem actualCardItem = dao.getCardItemById(cardItem.getId());
        assertNotNull(actualCardItem, "Call to getCardItemById after call to create should return non-null card item");

        assertEquals(newCardItem.getId(), actualCardItem.getId());
    }
}
