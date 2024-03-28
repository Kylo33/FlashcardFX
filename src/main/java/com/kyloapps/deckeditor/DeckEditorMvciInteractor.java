package com.kyloapps.deckeditor;

import com.kyloapps.deckeditor.cardeditor.forms.classic.ClassicMvciController;
import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.Deck;
import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import javafx.application.Platform;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;

    public DeckEditorMvciInteractor(DeckEditorMvciModel model) {
        this.model = model;
    }

    public void createDeck() {
        Deck result = new Deck();
        result.setTitle(model.getCreationDeckNameInput());
        result.setDescription(model.getCreationDeckDescriptionInput());
        model.getDecks().add(result);
        model.setCurrentDeck(result);
    }

    public void deleteDeck() {
        model.getDecks().remove(model.getCurrentDeck());
        model.setCurrentDeck(null);
        model.getMasterDirtyProperty().rebaseline();
    }

    public void confirmEditDeck() {

    }

    public void createCardEditor() {
        model.getCardEditorControllers().add(new CardEditorMvciController());
    }

    public void saveChanges() {
        model.getMasterDirtyProperty().rebaseline();
    }

    public void revertChanges() {
        model.getMasterDirtyProperty().reset();
    }

    public void deleteCard(CardEditorMvciController cardEditorMvciController) {
        model.getCardEditorControllers().remove(cardEditorMvciController);
    }

    public void switchDecks() {
        model.getCardEditorControllers().clear();

        Deck currentDeck = model.getCurrentDeck();

        if (currentDeck == null)
            return;

        //   Load cards
        currentDeck.getFlashcards().forEach(flashcard -> {
            CardEditorMvciController cardController = new CardEditorMvciController();
            cardController.loadCard(flashcard);
            model.getCardEditorControllers().add(cardController);
        });
        model.getMasterDirtyProperty().rebaseline();
    }
}
