package com.techelevator.custom.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class Card {
    private int id;
    @NotEmpty(message="Name must not be blank")
    private String name;
    private int rarity;

    public Card() {}
    public Card(int id, String name, int rarity) {
        this.id = id;
        this.name = name;
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

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
}
