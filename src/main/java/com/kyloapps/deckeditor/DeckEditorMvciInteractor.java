package com.kyloapps.deckeditor;

import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;

    public DeckEditorMvciInteractor(DeckEditorMvciModel model) {
        this.model = model;
        registerCardEditorListener(model);
    }

    private void registerCardEditorListener(DeckEditorMvciModel model) {
        model.getCardEditorControllers().addListener((ListChangeListener<? super CardEditorMvciController>) change -> {
            change.next();

            change.getAddedSubList()
                    .stream()
                    .forEach(cardEditorMvciController -> {
                        model.getCompositeDirtyProperty().add(cardEditorMvciController.dirtyProperty());
                    });

            change.getRemoved()
                    .stream()
                    .forEach(cardEditorMvciController -> {
                        model.getCompositeDirtyProperty().remove(cardEditorMvciController.dirtyProperty());
                    });
        });
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

    public void saveChanges() {
        model.getCompositeDirtyProperty().rebaseline();
    }

    public void revertChanges() {
        model.getCompositeDirtyProperty().reset();
    }
}
