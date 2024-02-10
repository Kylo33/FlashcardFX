package com.kyloapps.deckeditor;

import com.kyloapps.domain.Deck;
import javafx.collections.FXCollections;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;

    public DeckEditorMvciInteractor(DeckEditorMvciModel model) {
        this.model = model;
    }

    public void deleteDeck() {
        model.getDecks().remove(model.getCurrentDeck());
    }

    public void createDeck() {
        model.getDecks().add(new Deck(
                model.getNewDeckName(),
                model.getNewDeckDescription(),
                FXCollections.observableArrayList()
        ));
    }
}
