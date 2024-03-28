package com.kyloapps.mainmvci;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyloapps.domain.Deck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMvciInteractor {
    private final MainMvciModel model;
    private static final ObjectMapper mapper = new ObjectMapper();
    public MainMvciInteractor(MainMvciModel model) {
        this.model = model;
        registerDeckLoader();
    }

    private void registerDeckLoader() {
        if (model.getCurrentDirectory() != null) {
            model.getDecks().setAll(createDecks());
        }

        model.currentDirectoryProperty().addListener((observable, oldValue, newValue) -> {
            model.getDecks().setAll(createDecks());
        });
    }

    private List<Deck> createDecks() {
        List<Deck> result = new ArrayList<>();
        for (File file : model.getCurrentDirectory().listFiles()) {
            if (file.getName().endsWith(".json")) {
                Deck newDeck;
                try {
                    newDeck = mapper.readValue(file, Deck.class);
                    model.getDeckFileMap().put(newDeck, file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    continue;
                }
                result.add(newDeck);
            }
        }
        return result;
    }
}