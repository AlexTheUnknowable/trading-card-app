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
        String sql = "SELECT card_id, name, img, type, rarity FROM card ORDER BY card_id;";
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
        String sql = "SELECT card_id, name, img, type, rarity FROM card WHERE card_id = ? ORDER BY card_id;";
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
        String sql = "INSERT INTO card (name, img, type, rarity) VALUES (?, ?, ?, ?) RETURNING card_id;";
        try {
            int newCardId = jdbcTemplate.queryForObject(sql, int.class, card.getName(), card.getImgUrl(), card.getType(), card.getRarity());
            newCard = getCardById(newCardId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return newCard;
    }

    @Override
    public Card upsertCard(Card card) {
        Card newCard = null;
        String sql = "INSERT INTO card (name, img, type, rarity) VALUES (?, ?, ?, ?) "
                + "ON CONFLICT (name) DO UPDATE SET img = EXCLUDED.img, type = EXCLUDED.type, rarity = EXCLUDED.rarity "
                + "RETURNING card_id;";
        try {
            int newCardId = jdbcTemplate.queryForObject(sql, int.class, card.getName(), card.getImgUrl(), card.getType(), card.getRarity());
            newCard = getCardById(newCardId);
        } catch (DataAccessException e) {
            throw new DaoException(e.getMessage());
        }
        return newCard;
    }

    @Override
    public Card updateCard(Card card) {
        Card updatedCard = null;
        String sql = "UPDATE card SET name = ?, img = ?, type = ?, rarity = ? WHERE card_id = ?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, card.getName(), card.getImgUrl(), card.getType(), card.getRarity(), card.getId());
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
        String deleteItemSql = "DELETE FROM item WHERE card_id = ?;";
        String deleteCardTypeSql = "DELETE FROM card_type WHERE card_id = ?;";
        String deleteCardSql = "DELETE FROM card WHERE card_id = ?;";
        try {
            jdbcTemplate.update(deleteItemSql, cardId);
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
        card.setImgUrl(results.getString("img"));
        card.setType(results.getString("type"));
        card.setRarity(results.getInt("rarity"));
        return card;
    }

}
