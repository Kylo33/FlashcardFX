package com.kyloapps.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.Deck;
import com.kyloapps.Model.Model;
import com.kyloapps.View.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.util.StringConverter;
import org.json.JSONObject;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final View view;
    private final Model model;
    private static final ObjectMapper objectMapper = getDeckObjectMapper();

    private static ObjectMapper getDeckObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Deck.class, new DeckSerializer());
        mapper.registerModule(module);
        return mapper;
    }

    public Controller() {
        this.model = new Model();
        this.view = new View(model.getPresentationModel(), this::pageSelectHandler, model.flashcardDirectoryProperty(),
                this::detailChangeHandler, this::saveHandler, this::createDeckActionHandler);

        Menu menu = (Menu) view.getPages().get(Pages.MENU);

        model.getDeckLoader().getDecks().clear();
        model.getDeckLoader().getDecks().addListener((ListChangeListener<? super Deck>) (change) -> {
            while (change.next()) {
                Platform.runLater(() -> {
                    view.loading(true);
                    menu.getFlowPane().getChildren().addAll(change.getAddedSubList().stream()
                            .map((deck) -> new DeckMenuCard((Deck) deck, this::handlePracticeBtn))
                            .collect(Collectors.toList()));
                    view.loading(false);
                });
            }
        });
        new Thread(() -> model.getDeckLoader().load()).start();

        configureEditor();
    }

    private void createDeckActionHandler(Pair<String, String> stringStringPair) {
        String title = stringStringPair.getKey();
        String desc = stringStringPair.getValue();
        File newFile;
        try {
            newFile = File.createTempFile(title, ".json", model.getFlashcardDirectory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Deck newDeck = new Deck(title, desc, FXCollections.observableArrayList(), newFile);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(newFile, newDeck);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        model.getDeckLoader().getDecks().add(newDeck);
        Editor editor = (Editor) view.getPages().get(Pages.EDITOR);
        editor.getDeckChoiceBox().setValue(newDeck);
    }

    private void saveHandler() {
        Editor editor = (Editor) view.getPages().get(Pages.EDITOR);
        Deck currentDeck = editor.getDeckChoiceBox().getSelectionModel().getSelectedItem();
        List<DisplayableFlashcard> newFlashcardsList = ((VBox) editor.getCreator().getChildren().get(0)).getChildren().stream()
                .filter((child) -> child instanceof CardOptions)
                .filter((option) -> ((CardOptions) option).getCreator() != null && ((CardOptions) option).getCreator().isComplete())
                .map((option) -> ((CardOptions) option).getCreator().build()).collect(Collectors.toList());
        currentDeck.getFlashcards().setAll(newFlashcardsList);
        ChoiceBox<Deck> newBox = new ChoiceBox<>(model.getDeckLoader().getDecks());
        newBox.setValue(currentDeck);
        editor.setDeckChoiceBox(newBox);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(currentDeck.getPathToSelf(), currentDeck);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void detailChangeHandler(String title, String description) {
        Editor editor = (Editor) view.getPages().get(Pages.EDITOR);
        Deck currentDeck = editor.getDeckChoiceBox().getSelectionModel().getSelectedItem();
        if (title != null) {
            currentDeck.setTitle(title);
        }
        if (description != null) {
            currentDeck.setDescription(description);
        }
    }

    private void configureEditor() {
        Editor editor = (Editor) view.getPages().get(Pages.EDITOR);
        editor.getDeckChoiceBox().setItems(model.getDeckLoader().getDecks());
        editor.getDeckChoiceBox().getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            editor.getCreator().clearCardOptions();
            newValue.getFlashcards().stream().map(flashcard -> CardOptionsFactory.build(flashcard))
                    .forEach((cardOptions) -> editor.getCreator().addCardOptions(cardOptions));
        });
    }

    private void handlePracticeBtn(Deck deck) {
        // Create the new practice page and set the deck.
        model.getPracticeDeckManager().setCurrentDeck(Deck.getShuffledDeck(deck));
        Practice practicePage = new Practice(model.getPracticeDeckManager());
        StackPane practiceCard = practicePage.getCardContainer();

        model.getPresentationModel().currentPageProperty().addListener((observable, oldValue, newValue) -> {
            if (!(newValue instanceof Practice)){
                model.getPracticeDeckManager().setCurrentDeck(null);
            }
        });

        // When there's a new current card in the practice deck manager, make the change to the VBox.
        model.getPracticeDeckManager().currentIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                model.getPracticeDeckManager().getCurrentDeck().getFlashcards()
                        .get((Integer) oldValue).resetDisplay();
            } catch (IndexOutOfBoundsException e) {
                // This happens when the deck changes.
            }
            practiceCard.getChildren().setAll(getCurrentCard().display());
        });
        // Do that now.
        practiceCard.getChildren().setAll(getCurrentCard().display());

        // Clicks on the VBox should trigger the card to go to the next screen.
        practiceCard.setOnMouseClicked((event) -> {
            switch (event.getButton()) {
                case PRIMARY:
                    getCurrentCard().nextStep();
                    break;
                case SECONDARY:
                    getCurrentCard().backStep();
                    break;
            }
        });

        // Set up navigation buttons
        practicePage.getPrevBtn().setOnAction((event) -> {
            model.getPracticeDeckManager().prevCard();
        });
        practicePage.getNextBtn().setOnAction((event) -> {
            model.getPracticeDeckManager().nextCard();
        });

        // Disable the buttons when there is no prev or next.
        model.getPracticeDeckManager().nextCardExistsProperty().addListener((observable, oldValue, newValue) -> {
            practicePage.getNextBtn().setDisable(!newValue);
        });
        model.getPracticeDeckManager().prevCardExistsProperty().addListener((observable, oldValue, newValue) -> {
            practicePage.getPrevBtn().setDisable(!newValue);
        });

        // Check that now.
        practicePage.getNextBtn().setDisable(!model.getPracticeDeckManager().isNextCardExists());
        practicePage.getPrevBtn().setDisable(!model.getPracticeDeckManager().isPrevCardExists());

        // Set the deck and switch to the page
        model.getPresentationModel().setCurrentPage(practicePage);
    }

    private DisplayableFlashcard getCurrentCard() {
        return model.getPracticeDeckManager().getCurrentDeck().getFlashcards().get(model.getPracticeDeckManager().getCurrentIndex());
    }

    private void pageSelectHandler(Node node) {
        model.getPresentationModel().setCurrentPage(node);
    }

    public void initTheme() {
        ObservableList<String> stylesheets = this.view.getScene().getStylesheets();
        stylesheets.addAll(this.model.getPresentationModel().getCurrentTheme().getUserAgentStylesheet(), "custom.css");
        this.model.getPresentationModel().currentThemeProperty().addListener((observableValue, oldValue, newValue) -> {
            stylesheets.setAll(newValue.getUserAgentStylesheet(), "custom.css");
        });
    }

    public View getView() {
        return view;
    }
}
