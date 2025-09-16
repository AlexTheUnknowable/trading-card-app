package com.techelevator.custom.dao;

import com.techelevator.custom.model.Card;
import com.techelevator.custom.model.CardItem;
import com.techelevator.custom.model.CardItemDto;

import java.util.List;

public interface CardItemDao {
    List<CardItem> getAllCardItems();
    CardItem getCardItemById(int cardItemId);
    CardItem createCardItem(CardItem cardItem);
    CardItem updateCardItem(CardItem cardItem);
    int deleteCardItemById(int cardItemId);
    List<CardItemDto> getCardItemsByUser(int userId);
    CardItemDto getCardItemDtoById(int id);
    boolean userOwnsCard(int userId, int cardItemId);
}
