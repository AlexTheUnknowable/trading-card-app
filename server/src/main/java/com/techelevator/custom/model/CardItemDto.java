package com.techelevator.custom.model;

public class CardItemDto {
    private int cardItemId;
    private int userId;
    private int cardId;
    private String name;

    public CardItemDto() {}
    public CardItemDto(int cardItemId, int userId, int cardId, String name) {
        this.cardItemId = cardItemId;
        this.userId = userId;
        this.cardId = cardId;
        this.name = name;
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
}
