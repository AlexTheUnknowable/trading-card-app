package com.techelevator.custom.dao;

import com.techelevator.custom.model.Card;

import java.util.List;

public interface CardDao {
    List<Card> getCards();
    Card getCardById(int cardId);
    Card createCard(Card card);
    Card upsertCard(Card card);
    Card updateCard(Card card);
    int deleteCardById(int cardId);
}
