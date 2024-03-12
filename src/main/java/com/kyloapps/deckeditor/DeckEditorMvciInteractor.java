package com.kyloapps.deckeditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.io.IOException;
import java.util.stream.Collectors;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;
    private final ObjectMapper mapper = new ObjectMapper();

    public DeckEditorMvciInteractor(DeckEditorMvciModel model) {
        this.model = model;
        registerCardEditorListener(model);
        registerCurrentDeckListenerForLoadingCards(model);
    }

    private void registerCurrentDeckListenerForLoadingCards(DeckEditorMvciModel model) {
        model.currentDeckProperty().addListener((observable, oldDeck, newDeck) -> {
            model.getCardEditorControllers().setAll(newDeck.getFlashcards().stream().map(flashcard -> {
                CardEditorMvciController result = new CardEditorMvciController();
                result.loadCard(flashcard);
                return result;
            }).collect(Collectors.toList()));
        });
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
        model.getCurrentDeck().getFlashcards().setAll(
                model.getCardEditorControllers()
                        .stream()
                        .map(controller -> controller.getFlashcard())
                        .collect(Collectors.toList())
        );
        try {
            mapper.writeValue(model.getCurrentDeck().getFile(), model.getCurrentDeck());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void revertChanges() {
        model.getCompositeDirtyProperty().reset();
    }
}
