package com.techelevator.custom.model;

import java.math.BigDecimal;

public class ItemDto {
    private int itemId;
    private int userId;
    private int cardId;
    private String name;
    private String imgUrl;
    private String type;
    private String username;
    private BigDecimal price;

    public ItemDto() {}
    public ItemDto(int itemId, int userId, int cardId, String name, String imgUrl, String type, String username, BigDecimal price) {
        this.itemId = itemId;
        this.userId = userId;
        this.cardId = cardId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.type = type;
        this.username = username;
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

    public void setCardId(int cardId) { this.cardId = cardId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
