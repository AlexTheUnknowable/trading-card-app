package com.techelevator.custom.service;

import com.techelevator.custom.dao.CardDao;
import com.techelevator.custom.model.Card;
import com.techelevator.custom.model.PokeApiResponse;
import com.techelevator.custom.model.PokemonDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class PokemonImportService {

    private final CardDao cardDao;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();

    public PokemonImportService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public void importPokemon(int limit) {
        String url = "https://pokeapi.co/api/v2/pokemon?limit=" + limit;
        PokeApiResponse response = restTemplate.getForObject(url, PokeApiResponse.class);

        if (response == null || response.getResults() == null) {
            System.out.println("No Pokémon data found.");
            return;
        }

        for (PokeApiResponse.PokemonNameAndUrl nameAndUrl : response.getResults()) {
            try {
                PokemonDetails details = restTemplate.getForObject(nameAndUrl.getUrl(), PokemonDetails.class);

                if (details == null) continue;

                String name = capitalize(details.getName());
                String type =
                        details.getTypes() != null && !details.getTypes().isEmpty()
                            ? ( // if normal is 1st of 2 types, use 2nd type
                                details.getTypes().get(0).getType().getName().equals("normal") && details.getTypes().size() > 1
                                        ? details.getTypes().get(1).getType().getName()
                                        : details.getTypes().get(0).getType().getName()
                            ) : "unknown";
                String imgUrl =
                        details.getSprites() != null
                            ? details.getSprites().getFront_default()
                            : null;
                int rarity = random.nextInt(3) + 1;

                Card card = new Card();
                card.setName(name);
                card.setType(type);
                card.setImgUrl(imgUrl);
                card.setRarity(rarity);

                cardDao.upsertCard(card);
                System.out.println("Added: " + name + " (" + type + ")");
            } catch (Exception e) {
                System.out.println("Failed to import " + nameAndUrl.getName() + ": " + e.getMessage());
            }
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
