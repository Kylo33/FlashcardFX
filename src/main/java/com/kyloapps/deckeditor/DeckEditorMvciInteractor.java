package com.kyloapps.deckeditor;

import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;
    private static final SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss-SSS");

    public DeckEditorMvciInteractor(
            DeckEditorMvciModel model) {
        this.model = model;
        configureCompositeDirtyProperty();
        configureEditingProperties();
    }

    private void configureEditingProperties() {
        model.setEditingDeckNameInput(model.getCurrentDeck() == null
                ? ""
                : model.getCurrentDeck().getTitle());
        model.setEditingDeckDescriptionInput(model.getCurrentDeck() == null
                ? ""
                : model.getCurrentDeck().getDescription());
        model.currentDeckProperty().addListener((observable, oldDeck, newDeck) -> {
            model.setEditingDeckNameInput(model.getCurrentDeck() == null
                    ? ""
                    : model.getCurrentDeck().getTitle());
            model.setEditingDeckDescriptionInput(model.getCurrentDeck() == null
                    ? ""
                    : model.getCurrentDeck().getDescription());
        });
    }

    private void configureCompositeDirtyProperty() {
        model.getCompositeDirtyProperty().addAll(model.getDeepDirtyList(),
                model.editingDeckDescriptionInputProperty(),
                model.editingDeckNameInputProperty());
    }

    public void createDeck() {
        Deck result = new Deck();
        result.setTitle(model.getCreationDeckNameInput());
        result.setDescription(model.getCreationDeckDescriptionInput());
        model.getDecks().add(result);
        model.setCurrentDeck(result);
        model.getDeckFileMap().put(result, new File(getFilename(result)));
    }

    private String getFilename(Deck deck) {
        return model.getCurrentDirectory() + File.separator
                + deck.getTitle() + "-" + simpleDateFormat.format(new Date())
                + ".json";
    }

    public void deleteDeck() {
        model.getDecks().remove(model.getCurrentDeck());
        model.setCurrentDeck(null);
        model.getCompositeDirtyProperty().rebaseline();
    }

    public void createCardEditor() {
        model.getCardEditorControllers().add(new CardEditorMvciController());
    }

    public void saveChanges() {
        model.getCompositeDirtyProperty().rebaseline();
    }

    public void revertChanges() {
        model.getCompositeDirtyProperty().reset();
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
        model.getCompositeDirtyProperty().rebaseline();
    }
}
