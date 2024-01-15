package com.kyloapps.mainmvci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyloapps.domain.Deck;

public class MainMvciInteractor {
    private final MainMvciModel model;
    public MainMvciInteractor(MainMvciModel model) {
        this.model = model;
        registerDeckLoader();
    }

    private void registerDeckLoader() {
        String json = "{\"title\":\"Deck Title!\",\"description\":\"Deck description!\",\"flashcards\":[{\"type\":\"classic\",\"question\":\"What is the mitochondria?\",\"answer\":\"The powerhouse of the cell.\"},{\"type\":\"multipleChoice\",\"question\":\"Which of the following describe 70s ribosomes?\",\"options\":[{\"content\":\"Found in prokaryotic cells.\",\"correct\":true},{\"content\":\"Found in mitochondria.\",\"correct\":true},{\"content\":\"Produces ATP.\",\"correct\":false},{\"content\":\"Larger than 80s ribosomes.\",\"correct\":false}]},{\"type\":\"table\",\"question\":\"In which of the following conditions will there be a net movement of water molecules, by osmosis, from cell X to cell Y?\",\"headers\":[\"Cell X\",\"Cell Y\"],\"options\":[{\"content\":[\"Hypertonic\",\"Hypotonic\"],\"correct\":false},{\"content\":[\"Isotonic\",\"Isotonic\"],\"correct\":false},{\"content\":[\"Hypotonic\",\"Hypertonic\"],\"correct\":true},{\"content\":[\"More concentrated solute\",\"Less concentrated solute\"],\"correct\":false}]}]}";
        ObjectMapper mapper = new ObjectMapper();
        Deck deck;
        try {
            deck = mapper.readValue(json, Deck.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(deck);
    }
}