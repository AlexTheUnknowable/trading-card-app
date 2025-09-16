package com.techelevator.custom.model;

public class CardItem {
    private int id;
    private int userId;
    private int cardId;

    public CardItem() {}
    public CardItem(int id, int userId, int cardId) {
        this.id = id;
        this.userId = userId;
        this.cardId = cardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
