package com.kyloapps.deckeditor;

import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;

    public DeckEditorMvciInteractor(DeckEditorMvciModel model) {
        this.model = model;
        model.changesWereMadeProperty().bind( // TOdO
                Bindings.createBooleanBinding(() -> true, model.getCardEditorControllers())
        );
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

    public void confirmEditDeck() {
        Deck currentDeck = model.getCurrentDeck();
        currentDeck.setTitle(model.getEditingDeckName());
        currentDeck.setDescription(model.getEditingDeckDescription());
    }

    public void createCardEditor() {
        CardEditorMvciController result = new CardEditorMvciController();
        model.getCardEditorControllers().add(result);
    }
}
