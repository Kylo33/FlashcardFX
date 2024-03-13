package com.kyloapps.deckeditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import com.kyloapps.domain.StringPropertySerializer;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

import java.io.IOException;
import java.util.stream.Collectors;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;
    private final ObjectMapper mapper;

    public DeckEditorMvciInteractor(DeckEditorMvciModel model) {
        this.model = model;
        mapper = configureObjectMapper();
        registerCurrentDeckListenerForLoadingCards(model);
        updateCompositeDirtyProperty();
    }


    private ObjectMapper configureObjectMapper() {
        final ObjectMapper mapper;
        mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule module = new SimpleModule();
        module.addSerializer(StringProperty.class, new StringPropertySerializer());
        mapper.registerModule(module);
        return mapper;
    }

    private void registerCurrentDeckListenerForLoadingCards(DeckEditorMvciModel model) {
        model.currentDeckProperty().addListener((observable, oldDeck, newDeck) -> {
            // Add all the cards.
            model.getCardEditorControllers().setAll(newDeck.getFlashcards().stream().map(flashcard -> {
                CardEditorMvciController result = new CardEditorMvciController();
                result.loadCard(flashcard);
                return result;
            }).collect(Collectors.toList()));

            // Rebaseline
            updateCompositeDirtyProperty();
            model.getCompositeDirtyProperty().rebaseline();
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
        updateCompositeDirtyProperty();
        model.getCompositeDirtyProperty().rebaseline();

        // Update the flashcards in the deck.
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

    private void updateCompositeDirtyProperty() {
        // Update the model's CompositeDirtyProperty. If a new card is added, its dirty properties don't need to be tracked right away.
        // The fact that the new card exists will be tracked in the cardEditorControllers list, and that can just be reverted.
        model.getCompositeDirtyProperty().clear();
        model.getCompositeDirtyProperty().add(model.getCardEditorControllers());
        model.getCardEditorControllers()
                .stream()
                .map(CardEditorMvciController::dirtyProperty)
                .forEach(model.getCompositeDirtyProperty()::add);
    }

    public void revertChanges() {
        updateCompositeDirtyProperty();
        model.getCompositeDirtyProperty().reset();
    }
}
