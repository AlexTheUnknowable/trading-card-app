package com.techelevator.custom.model;

import java.util.List;

public class PokemonDetails {
    private String name;
    private Sprites sprites;
    private List<TypeSlot> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<TypeSlot> getTypes() {
        return types;
    }

    public void setTypes(List<TypeSlot> types) {
        this.types = types;
    }

    // nested classes for sub-objects
    public static class Sprites {
        private String front_default;

        public String getFront_default() {
            return front_default;
        }

        public void setFront_default(String front_default) {
            this.front_default = front_default;
        }
    }

    public static class TypeSlot {
        private Type type;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }
    }

    public static class Type {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
