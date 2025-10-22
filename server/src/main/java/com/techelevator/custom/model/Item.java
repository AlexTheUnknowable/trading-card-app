package com.techelevator.custom.model;

import java.math.BigDecimal;

public class Item {
    private int itemId;
    private int userId;
    private int cardId;
    private BigDecimal price;

    public Item() {}
    public Item(int userId, int cardId, BigDecimal price) {
        this.userId = userId;
        this.cardId = cardId;
        this.price = price;
    }
    public Item(int itemId, int userId, int cardId, BigDecimal price) {
        this.itemId = itemId;
        this.userId = userId;
        this.cardId = cardId;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
