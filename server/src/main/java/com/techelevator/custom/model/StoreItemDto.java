package com.techelevator.custom.model;

import java.math.BigDecimal;

public class StoreItemDto {
    private int storeItemId;
    private int cardItemId;
    private int userId;
    private int cardId;
    private String name;
    private BigDecimal price;

    public StoreItemDto() {}
    public StoreItemDto(int storeItemId, int cardItemId, int userId, int cardId, String name, BigDecimal price) {
        this.storeItemId = storeItemId;
        this.cardItemId = cardItemId;
        this.userId = userId;
        this.cardId = cardId;
        this.name = name;
        this.price = price;
    }

    public int getStoreItemId() {
        return storeItemId;
    }

    public void setStoreItemId(int storeItemId) {
        this.storeItemId = storeItemId;
    }

    public int getCardItemId() {
        return cardItemId;
    }

    public void setCardItemId(int cardItemId) {
        this.cardItemId = cardItemId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
