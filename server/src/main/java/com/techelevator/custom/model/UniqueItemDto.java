package com.techelevator.custom.model;

import java.math.BigDecimal;

public class UniqueItemDto {
    private int cardId;
    private String name;
    private String imgUrl;
    private String type;
    private int countTotal;
    private int countWithPrice;
    private int countWithoutPrice;

    public UniqueItemDto() {}
    public UniqueItemDto(int cardId, String name, String imgUrl, String type, int countTotal, int countWithPrice, int countWithoutPrice) {
        this.cardId = cardId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.type = type;
        this.countTotal = countTotal;
        this.countWithPrice = countWithPrice;
        this.countWithoutPrice = countWithoutPrice;
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

    public int getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public int getCountWithPrice() {
        return countWithPrice;
    }

    public void setCountWithPrice(int countWithPrice) {
        this.countWithPrice = countWithPrice;
    }

    public int getCountWithoutPrice() {
        return countWithoutPrice;
    }

    public void setCountWithoutPrice(int countWithoutPrice) {
        this.countWithoutPrice = countWithoutPrice;
    }
}
