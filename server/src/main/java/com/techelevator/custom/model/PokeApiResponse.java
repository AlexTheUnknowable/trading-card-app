package com.techelevator.custom.model;

import java.util.List;

public class PokeApiResponse {
    private int count;
    private String next;
    private String previous;
    private List<PokemonNameAndUrl> results;

    public List<PokemonNameAndUrl> getResults() {
        return results;
    }

    public void setResults(List<PokemonNameAndUrl> results) {
        this.results = results;
    }

    public static class PokemonNameAndUrl {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}