package com.techelevator.custom.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class StoreItem {
    private int storeItemId;
    private int cardItemId;
    @Positive
    private BigDecimal price;

    public StoreItem() {}
    public StoreItem(int storeItemId, int cardItemId, BigDecimal price) {
        this.storeItemId = storeItemId;
        this.cardItemId = cardItemId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
