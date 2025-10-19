package com.techelevator.custom.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class Card {
    private int id;
    @NotEmpty(message="Name must not be blank")
    private String name;
    private String type;
    private String imgUrl;
    private int rarity;

    public Card() {}

    public Card(int id, String name, int rarity) { // quick fix just so the tests don't implode
        this.id = id;
        this.name = name;
        this.rarity = rarity;
    }

    public Card(int id, String name, String imgUrl, String type, int rarity) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.type = type;
        this.rarity = rarity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
}
