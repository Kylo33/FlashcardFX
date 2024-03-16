package com.kyloapps.deckeditor;

import com.kyloapps.domain.Deck;
import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import javafx.application.Platform;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.util.ArrayList;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;

    public DeckEditorMvciInteractor(DeckEditorMvciModel model) {
        this.model = model;
        bindCompositeDirtyProperty();
    }

    /** Maintain the CompositeDirtyProperty with all other DirtyProperties */
    private void bindCompositeDirtyProperty() {
        DirtyUtils.bindCompositeDirtyProperty(
                model.getCompositeDirtyProperty(),
                model.getCardEditorControllers(),
                CardEditorMvciController::dirtyProperty
        );
    }

    public void createDeck() {

    }

    public void deleteDeck() {

    }

    public void confirmEditDeck() {

    }

    public void createCardEditor() {

    }

    public void saveChanges() {

    }

    public void revertChanges() {

    }

    public void deleteCard(CardEditorMvciController cardEditorMvciController) {

    }

    public void switchDecks() {
        Deck currentDeck = model.getCurrentDeck();
        // Load cards
        currentDeck.getFlashcards().forEach(flashcard -> {
            CardEditorMvciController cardController = new CardEditorMvciController();
            cardController.loadCard(flashcard);
            model.getCardEditorControllers().add(cardController);
        });
        model.getCompositeDirtyProperty().rebaseline();
    }
}
