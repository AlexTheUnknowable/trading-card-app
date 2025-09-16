package com.techelevator.custom.dao;

import com.techelevator.custom.exception.DaoException;
import com.techelevator.custom.model.Card;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCardDao implements CardDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT card_id, name, rarity FROM card ORDER BY card_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Card card = mapRowToCard(results);
                cards.add(card);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return cards;
    }

    @Override
    public Card getCardById(int cardId) {
        Card card = null;
        String sql = "SELECT card_id, name, rarity FROM card WHERE card_id = ? ORDER BY card_id;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cardId);
            if (results.next()) {
                card = mapRowToCard(results);
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return card;
    }

    @Override
    public Card createCard(Card card) {
        Card newCard = null;
        String sql = "INSERT INTO card (name, rarity) VALUES (?, ?) RETURNING card_id;";
        try {
            int newCardId = jdbcTemplate.queryForObject(sql, int.class, card.getName(), card.getRarity());
            newCard = getCardById(newCardId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return newCard;
    }

    @Override
    public Card updateCard(Card card) {
        Card updatedCard = null;
        String sql = "UPDATE card SET name = ?, rarity = ? WHERE card_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, card.getName(), card.getRarity(), card.getId());
            if (numberOfRows == 0) {
                throw new DaoException("0 rows affected, expected at least 1");
            } else {
                updatedCard = getCardById(card.getId());
            }
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return updatedCard;
    }

    @Override
    public int deleteCardById(int cardId) {
        int numberOfRows = 0;
        String deleteStoreItemSql = "DELETE FROM storeitem WHERE carditem_id IN (SELECT carditem_id FROM carditem WHERE card_id = ?);";
        String deleteCardItemSql = "DELETE FROM carditem WHERE card_id = ?;";
        String deleteCardTypeSql = "DELETE FROM card_type WHERE card_id = ?;";
        String deleteCardSql = "DELETE FROM card WHERE card_id = ?;";
        try {
            jdbcTemplate.update(deleteStoreItemSql, cardId);
            jdbcTemplate.update(deleteCardItemSql, cardId);
            jdbcTemplate.update(deleteCardTypeSql, cardId);
            numberOfRows = jdbcTemplate.update(deleteCardSql, cardId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return numberOfRows;
    }

    private Card mapRowToCard(SqlRowSet results) {
        Card card = new Card();
        card.setId(results.getInt("card_id"));
        card.setName(results.getString("name"));
        card.setRarity(results.getInt("rarity"));
        return card;
    }

//    @Override
//    public void linkCardUser(int userId, int cardId) {
//        String sql = "INSERT INTO carditem (user_id, card_id) VALUES (?, ?)";
//        try {
//            jdbcTemplate.update(sql, userId, cardId);
//        }catch (DataAccessException e) {
//            throw new DaoException(e.getMessage());
//        }
//    }
//
//    @Override
//    public void unlinkCardUser(int userId, int cardId) {
//        String sql = "DELETE FROM carditem WHERE user_id = ? AND card_id = ?";
//        try {
//            jdbcTemplate.update(sql, userId, cardId);
//        }catch (DataAccessException e) {
//            throw new DaoException(e.getMessage());
//        }
//    }
}
