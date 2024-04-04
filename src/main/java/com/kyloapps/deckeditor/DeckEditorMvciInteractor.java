package com.kyloapps.deckeditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kyloapps.deckeditor.cardeditor.CardEditorMvciController;
import com.kyloapps.domain.Deck;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

public class DeckEditorMvciInteractor {
    private final DeckEditorMvciModel model;
    private static final SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss-SSS");
    private static final ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper result = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule module = new SimpleModule();
        module.addSerializer(StringProperty.class, new StringPropertySerializer());
        result.registerModule(module);
        return result;
    }

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
        if (model.getDeckFileMap().get(model.getCurrentDeck()).delete()) {
            model.getDecks().remove(model.getCurrentDeck());
            model.getDeckFileMap().remove(model.getCurrentDeck());
        }
        model.setCurrentDeck(null);
        model.getCompositeDirtyProperty().rebaseline();
    }

    public void createCardEditor() {
        model.getCardEditorControllers().add(new CardEditorMvciController());
    }

    public void saveChanges() {
        model.getCompositeDirtyProperty().rebaseline();
        model.getCurrentDeck().setTitle(model.getEditingDeckNameInput());
        model.getCurrentDeck().setDescription(model.getEditingDeckDescriptionInput());
        model.getCurrentDeck().getFlashcards().setAll(
                model.getCardEditorControllers()
                        .stream()
                        .filter(controller -> controller.getCardController() != null)
                        .map(controller -> controller.getCardController().toFlashcard())
                        .collect(Collectors.toList()));
        try {
            objectMapper.writeValue(model.getDeckFileMap().get(model.getCurrentDeck()), model.getCurrentDeck());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
