package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Card;
import com.techelevator.custom.model.CardItem;
import com.techelevator.custom.model.CardItemDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCardItemDao implements CardItemDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCardItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CardItem> getAllCardItems() {
        List<CardItem> cardItems = new ArrayList<>();
        String sql = "SELECT carditem_id, user_id, card_id FROM carditem;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                CardItem cardItem = mapRowToCardItem(results);
                cardItems.add(cardItem);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return cardItems;
    }

    @Override
    public CardItem getCardItemById(int cardItemId){
        CardItem cardItem = null;
        String sql = "SELECT carditem_id, user_id, card_id FROM carditem WHERE carditem_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cardItemId);
            if (results.next()) {
                cardItem = mapRowToCardItem(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return cardItem;
    }

    @Override
    public CardItemDto getCardItemDtoById(int cardItemId){
        CardItemDto cardItemDto = null;
        String sql = "SELECT carditem_id, user_id, ci.card_id AS card_id, c.name AS name FROM carditem AS ci JOIN card AS c ON ci.card_id = c.card_id WHERE carditem_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cardItemId);
            if (results.next()) {
                cardItemDto = mapRowToCardItemDto(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return cardItemDto;
    }

    @Override
    public CardItem createCardItem(CardItem cardItem) {
        CardItem newCardItem = null;
        String sql = "INSERT INTO carditem (user_id, card_id) VALUES (?, ?) RETURNING carditem_id;";
        try {
            int newCardItemId = jdbcTemplate.queryForObject(sql, int.class, cardItem.getUserId(), cardItem.getCardId());
            newCardItem = getCardItemById(newCardItemId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return newCardItem;
    }

    @Override
    public CardItem updateCardItem(CardItem cardItem) {
        CardItem updatedCardItem = null;
        String sql = "UPDATE carditem SET user_id = ?, card_id = ? WHERE carditem_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, cardItem.getUserId(), cardItem.getCardId(), cardItem.getId());
            if (numberOfRows == 0) {
                throw new DaoException("0 rows affected, expected at least 1");
            } else {
                updatedCardItem = getCardItemById(cardItem.getId());
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return updatedCardItem;
    }

    @Override
    public int deleteCardItemById(int cardItemId) {
        int numberOfRows = 0;
        String deleteStoreItemSql = "DELETE FROM storeitem WHERE carditem_id IN (SELECT carditem_id FROM carditem WHERE carditem_id = ?);";
        String deleteCardItemSql = "DELETE FROM carditem WHERE carditem_id = ?;";
        try {
            jdbcTemplate.update(deleteStoreItemSql, cardItemId);
            numberOfRows = jdbcTemplate.update(deleteCardItemSql, cardItemId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return numberOfRows;
    }

    @Override
    public List<CardItemDto> getCardItemsByUser(int userId) {
        List<CardItemDto> cardItemDtos = new ArrayList<>();
        String sql = "SELECT carditem_id, user_id, ci.card_id AS card_id, c.name AS name FROM carditem AS ci JOIN card AS c ON ci.card_id = c.card_id WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                CardItemDto cardItemDto = mapRowToCardItemDto(results);
                cardItemDtos.add(cardItemDto);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return cardItemDtos;
    }

    private CardItem mapRowToCardItem(SqlRowSet results) {
        CardItem cardItem = new CardItem();
        cardItem.setId(results.getInt("carditem_id"));
        cardItem.setUserId(results.getInt("user_id"));
        cardItem.setCardId(results.getInt("card_id"));
        return cardItem;
    }

    private CardItemDto mapRowToCardItemDto(SqlRowSet results) {
        CardItemDto cardItemDto = new CardItemDto();
        cardItemDto.setCardItemId(results.getInt("carditem_id"));
        cardItemDto.setUserId(results.getInt("user_id"));
        cardItemDto.setCardId(results.getInt("card_id"));
        cardItemDto.setName(results.getString("name"));
        return cardItemDto;
    }

    public boolean userOwnsCard(int userId, int cardItemId) {
        String sql = "SELECT user_id, carditem_id FROM carditem WHERE user_id = ? AND carditem_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, cardItemId);
            if (results.next()) { // if any rows are returned, the user owns the card
                return true;
            } else {
                return false;
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
