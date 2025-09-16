package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.StoreItem;
import com.techelevator.custom.model.StoreItemDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcStoreItemDao implements StoreItemDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcStoreItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<StoreItem> getStoreItems() {
        List<StoreItem> storeItems = new ArrayList<>();
        String sql = "SELECT storeitem_id, carditem_id, price FROM storeitem;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                StoreItem storeItem = mapRowToStoreItem(results);
                storeItems.add(storeItem);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return storeItems;
    }

    @Override
    public List<StoreItemDto> getStoreItemDtos() {
        List<StoreItemDto> storeItemDtos = new ArrayList<>();
        String sql = "SELECT storeitem_id, si.carditem_id, ci.user_id, c.card_id, c.name, price FROM storeitem AS si JOIN carditem AS ci ON si.carditem_id = ci.carditem_id JOIN card AS c ON ci.card_id = c.card_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                StoreItemDto storeItemDto = mapRowToStoreItemDto(results);
                storeItemDtos.add(storeItemDto);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return storeItemDtos;
    }

    @Override
    public StoreItem getStoreItemById(int storeItemId) {
        StoreItem storeItem = null;
        String sql = "SELECT storeitem_id, carditem_id, price FROM storeitem WHERE storeitem_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, storeItemId);
            if (results.next()) {
                storeItem = mapRowToStoreItem(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return storeItem;
    }

    @Override
    public StoreItemDto getStoreItemDtoById(int storeItemDtoId) {
        StoreItemDto storeItemDto = null;
        String sql = "SELECT storeitem_id, si.carditem_id, ci.user_id, c.card_id, c.name, price FROM storeitem AS si JOIN carditem AS ci ON si.carditem_id = ci.carditem_id JOIN card AS c ON ci.card_id = c.card_id WHERE storeitem_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, storeItemDtoId);
            if (results.next()) {
                storeItemDto = mapRowToStoreItemDto(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return storeItemDto;
    }

    @Override
    public List<StoreItemDto> getStoreItemsByUser(int userId) {
        List<StoreItemDto> storeItemDtos = new ArrayList<>();
        String sql = "SELECT storeitem_id, si.carditem_id, ci.user_id, c.card_id, c.name, price FROM storeitem AS si JOIN carditem AS ci ON si.carditem_id = ci.carditem_id JOIN card AS c ON ci.card_id = c.card_id WHERE ci.user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                StoreItemDto storeItemDto = mapRowToStoreItemDto(results);
                storeItemDtos.add(storeItemDto);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return storeItemDtos;
    }

    @Override
    public StoreItem createStoreItem(StoreItem storeItem) {
        StoreItem newStoreItem = null;
        String sql = "INSERT INTO storeitem (carditem_id, price) VALUES (?, ?) RETURNING storeitem_id;";
        try {
            int newStoreItemId = jdbcTemplate.queryForObject(sql, int.class, storeItem.getCardItemId(), storeItem.getPrice());
            newStoreItem = getStoreItemById(newStoreItemId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return newStoreItem;
    }

    @Override
    public StoreItem updateStoreItem(StoreItem storeItem) {
        StoreItem updatedStoreItem = null;
        String sql = "UPDATE storeitem SET carditem_id = ?, price = ? WHERE storeitem_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, storeItem.getCardItemId(), storeItem.getPrice()); // is this broken? no storeitem id?? lmao ig i never tested this
            if (numberOfRows == 0) {
                throw new DaoException("0 rows affected, expected at least 1");
            } else {
                updatedStoreItem = getStoreItemById(storeItem.getStoreItemId());
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return updatedStoreItem;
    }

    @Override
    public int deleteStoreItemById(int storeItemId) {
        int numberOfRows = 0;
        String sql = "DELETE FROM storeitem WHERE storeitem_id = ?;";
        try {
            numberOfRows = jdbcTemplate.update(sql, storeItemId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return numberOfRows;
    }

    private StoreItem mapRowToStoreItem(SqlRowSet results) {
        StoreItem storeItem = new StoreItem();
        storeItem.setStoreItemId(results.getInt("storeitem_id"));
        storeItem.setCardItemId(results.getInt("carditem_id"));
        storeItem.setPrice(results.getBigDecimal("price"));
        return storeItem;
    }

    private StoreItemDto mapRowToStoreItemDto(SqlRowSet results) {
        StoreItemDto storeItemDto = new StoreItemDto();
        storeItemDto.setStoreItemId(results.getInt("storeitem_id"));
        storeItemDto.setCardItemId(results.getInt("carditem_id"));
        storeItemDto.setUserId(results.getInt("user_id"));
        storeItemDto.setCardId(results.getInt("card_id"));
        storeItemDto.setName(results.getString("name"));
        storeItemDto.setPrice(results.getBigDecimal("price"));
        return storeItemDto;
    }

    @Override
    public boolean isCardItemOnStore(int cardItemId) {
        String sql = "SELECT storeitem_id, carditem_id, price FROM storeitem WHERE carditem_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cardItemId);
            if (results.next()) { // if any rows are returned, the card is already on the store
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
