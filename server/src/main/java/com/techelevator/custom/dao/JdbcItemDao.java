package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Item;
import com.techelevator.custom.model.ItemDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcItemDao implements ItemDao {
    private final JdbcTemplate jdbcTemplate;
    public JdbcItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT item_id, user_id, card_id, price FROM item;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Item item = mapRowToItem(results);
                items.add(item);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return items;
    }

    @Override
    public List<ItemDto> getAllItemDtos() {
        List<ItemDto> itemDtos = new ArrayList<>();
        String sql = "SELECT item_id, i.user_id, i.card_id, c.name, u.username, price FROM item AS i JOIN card AS c ON i.card_id = c.card_id JOIN users AS u ON i.user_id = u.user_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                ItemDto itemDto = mapRowToItemDto(results);
                itemDtos.add(itemDto);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return itemDtos;
    }

    @Override
    public Item getItemById(int itemId) {
        Item item = null;
        String sql = "SELECT item_id, user_id, card_id, price FROM item WHERE item_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                item = mapRowToItem(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return item;
    }

    @Override
    public ItemDto getItemDtoById(int itemId) {
        ItemDto itemDto = null;
        String sql = "SELECT item_id, i.user_id, i.card_id, c.name, u.username, price FROM item AS i JOIN card AS c ON i.card_id = c.card_id JOIN users AS u ON i.user_id = u.user_id WHERE item_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
            if (results.next()) {
                itemDto = mapRowToItemDto(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return itemDto;
    }

    @Override
    public List<ItemDto> getItemDtosByUser(int userId) {
        List<ItemDto> itemDtos = new ArrayList<>();
        String sql = "SELECT item_id, i.user_id, i.card_id, c.name, u.username, price FROM item AS i JOIN card AS c ON i.card_id = c.card_id JOIN users AS u ON i.user_id = u.user_id WHERE u.user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                ItemDto itemDto = mapRowToItemDto(results);
                itemDtos.add(itemDto);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return itemDtos;
    }

    @Override
    public List<ItemDto> getItemDtosOnStore() {
        List<ItemDto> itemDtos = new ArrayList<>();
        String sql = "SELECT item_id, i.user_id, i.card_id, c.name, u.username, price FROM item AS i JOIN card AS c ON i.card_id = c.card_id JOIN users AS u ON i.user_id = u.user_id WHERE price > 0;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                ItemDto itemDto = mapRowToItemDto(results);
                itemDtos.add(itemDto);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return itemDtos;
    }

    @Override
    public Item createItem(Item item) {
        Item newItem = null;
        String sql = "INSERT INTO item (user_id, card_id, price) VALUES (?, ?, ?) RETURNING item_id;";
        try {
            int newItemId = jdbcTemplate.queryForObject(sql, int.class, item.getUserId(), item.getCardId(), item.getPrice());
            newItem = getItemById(newItemId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return newItem;
    }

    @Override
    public Item updateItem(Item item) {
        Item updatedItem = null;
        String sql = "UPDATE item SET user_id = ?, card_id = ?, price = ? WHERE item_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, item.getUserId(), item.getCardId(), item.getPrice(), item.getItemId());
            if (numberOfRows == 0) {
                throw new DaoException("0 rows affected, expected at least 1");
            } else {
                updatedItem = getItemById(item.getItemId());
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return updatedItem;
    }

    @Override
    public int deleteItemById(int itemId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM item WHERE item_id = ?;";
        try {
            numberOfRows = jdbcTemplate.update(sql, itemId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return numberOfRows;
    }

    private Item mapRowToItem(SqlRowSet results) {
        Item item = new Item();
        item.setItemId(results.getInt("item_id"));
        item.setUserId(results.getInt("user_id"));
        item.setCardId(results.getInt("card_id"));
        item.setPrice(results.getBigDecimal("price"));
        return item;
    }

    private ItemDto mapRowToItemDto(SqlRowSet results) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemId(results.getInt("item_id"));
        itemDto.setUserId(results.getInt("user_id"));
        itemDto.setCardId(results.getInt("card_id"));
        itemDto.setName(results.getString("name"));
        itemDto.setUsername(results.getString("username"));
        itemDto.setPrice(results.getBigDecimal("price"));
        return itemDto;
    }
}
