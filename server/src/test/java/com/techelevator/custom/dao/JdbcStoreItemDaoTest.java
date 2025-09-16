package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.CardItem;
import com.techelevator.custom.model.CardItemDto;
import com.techelevator.custom.model.StoreItem;
import com.techelevator.custom.model.StoreItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcStoreItemDaoTest extends BaseDaoTest {
    private static final StoreItem STORE_ITEM_1 = new StoreItem(1, 2, new BigDecimal("1.00"));
    private static final StoreItem STORE_ITEM_2 = new StoreItem(2, 3, new BigDecimal("1.00"));
    private static final StoreItem STORE_ITEM_3 = new StoreItem(3, 4, new BigDecimal("2.00"));
    private static final StoreItem STORE_ITEM_4 = new StoreItem(4, 6, new BigDecimal("1.50"));
    private JdbcStoreItemDao dao;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcStoreItemDao(jdbcTemplate);
    }

    @Test
    public void getStoreItems_returns_correct_list_of_store_items() {
        List<StoreItem> storeItems = dao.getStoreItems();

        assertNotNull(storeItems, "getStoreItems returned a null list of store items");
        assertEquals(4, storeItems.size(), "getStoreItems returned a list with the incorrect number of store items");
        assertEquals(STORE_ITEM_1.getStoreItemId(), storeItems.get(0).getStoreItemId(), "getStoreItems returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_1.getCardItemId(), storeItems.get(0).getCardItemId(), "getStoreItems returned a list in incorrect order (card item IDs do not match)");
        assertEquals(STORE_ITEM_2.getStoreItemId(), storeItems.get(1).getStoreItemId(), "getStoreItems returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_2.getCardItemId(), storeItems.get(1).getCardItemId(), "getStoreItems returned a list in incorrect order (card item IDs do not match)");
        assertEquals(STORE_ITEM_3.getStoreItemId(), storeItems.get(2).getStoreItemId(), "getStoreItems returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_3.getCardItemId(), storeItems.get(2).getCardItemId(), "getStoreItems returned a list in incorrect order (card item IDs do not match)");
        assertEquals(STORE_ITEM_4.getStoreItemId(), storeItems.get(3).getStoreItemId(), "getStoreItems returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_4.getCardItemId(), storeItems.get(3).getCardItemId(), "getStoreItems returned a list in incorrect order (card item IDs do not match)");
    }

    @Test
    public void getStoreItemDtos_returns_correct_list_of_store_item_dtos() {
        List<StoreItemDto> storeItemDtos = dao.getStoreItemDtos();

        assertNotNull(storeItemDtos, "getStoreItemDtos returned a null list of store items Dtos");
        assertEquals(4, storeItemDtos.size(), "getStoreItemDtos returned a list with the incorrect number of store items Dtos");
        assertEquals(STORE_ITEM_1.getStoreItemId(), storeItemDtos.get(0).getStoreItemId(), "getStoreItemDtos returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_1.getCardItemId(), storeItemDtos.get(0).getCardItemId(), "getStoreItemDtos returned a list in incorrect order (card item IDs do not match)");
        assertEquals("Charmander", storeItemDtos.get(0).getName(), "getStoreItemDtos returned a list in incorrect order (names do not match)");
        assertEquals(STORE_ITEM_2.getStoreItemId(), storeItemDtos.get(1).getStoreItemId(), "getStoreItemDtos returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_2.getCardItemId(), storeItemDtos.get(1).getCardItemId(), "getStoreItemDtos returned a list in incorrect order (card item IDs do not match)");
        assertEquals("Charmander", storeItemDtos.get(1).getName(), "getStoreItemDtos returned a list in incorrect order (names do not match)");
        assertEquals(STORE_ITEM_3.getStoreItemId(), storeItemDtos.get(2).getStoreItemId(), "getStoreItemDtos returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_3.getCardItemId(), storeItemDtos.get(2).getCardItemId(), "getStoreItemDtos returned a list in incorrect order (card item IDs do not match)");
        assertEquals("Squirtle", storeItemDtos.get(2).getName(), "getStoreItemDtos returned a list in incorrect order (names do not match)");
        assertEquals(STORE_ITEM_4.getStoreItemId(), storeItemDtos.get(3).getStoreItemId(), "getStoreItemDtos returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_4.getCardItemId(), storeItemDtos.get(3).getCardItemId(), "getStoreItemDtos returned a list in incorrect order (card item IDs do not match)");
        assertEquals("Squirtle", storeItemDtos.get(3).getName(), "getStoreItemDtos returned a list in incorrect order (names do not match)");
    }

    @Test
    public void getStoreItemById_with_valid_id_returns_correct_store_item() {
        StoreItem actualStoreItem = dao.getStoreItemById(STORE_ITEM_1.getStoreItemId());
        assertEquals(STORE_ITEM_1.getStoreItemId(), actualStoreItem.getStoreItemId(), "getStoreItemById with valid id did not return correct store item");
    }

    @Test
    public void getStoreItemById_with_invalid_id_returns_null() {
        StoreItem storeItem = dao.getStoreItemById(-1);
        assertNull(storeItem, "getStoreItemById with invalid storeItemId did not return null store item");
    }

    @Test
    public void getStoreItemDtoById_with_valid_id_returns_correct_store_item_dto() {
        StoreItemDto actualStoreItemDto = dao.getStoreItemDtoById(STORE_ITEM_1.getStoreItemId());
        assertEquals(STORE_ITEM_1.getStoreItemId(), actualStoreItemDto.getStoreItemId(), "getStoreItemDtoById with valid id did not return correct store item");
    }

    @Test
    public void getStoreItemDtoById_with_invalid_id_returns_null() {
        StoreItemDto storeItemDto = dao.getStoreItemDtoById(-1);
        assertNull(storeItemDto, "getStoreItemDtoById with invalid storeItemId did not return null store item");
    }

    @Test
    public void getStoreItemsByUser_given_valid_user_should_return_correct_store_item_dtos() {
        List<StoreItemDto> user1StoreItems = dao.getStoreItemsByUser(1);

        assertNotNull(user1StoreItems, "getStoreItemsByUser returned a null list of store items");
        assertEquals(1, user1StoreItems.size(), "getStoreItemsByUser returned a list with the incorrect number of card items");
        assertEquals(STORE_ITEM_1.getStoreItemId(), user1StoreItems.get(0).getStoreItemId(), "getStoreItemsByUser returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_1.getCardItemId(), user1StoreItems.get(0).getCardItemId(), "getStoreItemsByUser returned a list in incorrect order (card item IDs do not match)");

        List<StoreItemDto> user2StoreItems = dao.getStoreItemsByUser(2);

        assertNotNull(user2StoreItems, "getStoreItemsByUser returned a null list of store items");
        assertEquals(2, user2StoreItems.size(), "getStoreItemsByUser returned a list with the incorrect number of card items");
        assertEquals(STORE_ITEM_2.getStoreItemId(), user2StoreItems.get(0).getStoreItemId(), "getStoreItemsByUser returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_2.getCardItemId(), user2StoreItems.get(0).getCardItemId(), "getStoreItemsByUser returned a list in incorrect order (card item IDs do not match)");
        assertEquals(STORE_ITEM_3.getStoreItemId(), user2StoreItems.get(1).getStoreItemId(), "getStoreItemsByUser returned a list in incorrect order (store item IDs do not match)");
        assertEquals(STORE_ITEM_3.getCardItemId(), user2StoreItems.get(1).getCardItemId(), "getStoreItemsByUser returned a list in incorrect order (card item IDs do not match)");
    }

    @Test
    public void getStoreItemsByUser_given_invalid_user_should_return_empty_list() {
        List<StoreItemDto> storeItems = dao.getStoreItemsByUser(-1);
        List<StoreItemDto> emptyList = new ArrayList<>();
        assertEquals(storeItems, emptyList, "getStoreItemsByUser with invalid user id did not return empty list");
    }

    @Test
    public void createStoreItem_should_create_store_item() {
        StoreItem newStoreItem = new StoreItem(5, 1, new BigDecimal("3.00"));

        StoreItem storeItem = dao.createStoreItem(newStoreItem);
        assertNotNull(storeItem, "Call to create should return non-null store item");

        StoreItem actualStoreItem = dao.getStoreItemById(storeItem.getStoreItemId());
        assertNotNull(actualStoreItem, "Call to getStoreItemById after call to create should return non-null store item");

        assertEquals(newStoreItem.getStoreItemId(), actualStoreItem.getStoreItemId());
    }
}
