package com.kyloapps.Model;

import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.View.FlashcardFactory;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class DeckLoader {
    private final ObjectProperty<File> flashcardDirectory;
    private ObservableList decks;
    private IntegerProperty totalDecks = new SimpleIntegerProperty();

    public DeckLoader(ObjectProperty<File> flashcardDirectory) {
        this.flashcardDirectory = flashcardDirectory;
        this.decks = FXCollections.observableArrayList();

        // Add an event listener to changes in the flashcard directory.
        flashcardDirectory.addListener((observable, oldValue, newValue) -> new Thread(() -> this.load()).start());
    }

    public void load() {
        decks.clear();

        // For each json file in the flashcard directory
        File[] jsonFiles = Arrays.stream(flashcardDirectory.get().listFiles())
                .filter(file -> file.getName().endsWith(".json")).toArray(File[]::new);

        totalDecks.set(jsonFiles.length);

        for (File jsonFile: jsonFiles) {
            // Load the deck as a JSONObject
            JSONObject deckObject;
            try {
                deckObject = new JSONObject(Files.readString(jsonFile.toPath()));
            } catch (IOException e) {
                throw new RuntimeException("Error reading files.");
            }

            JSONArray cards = deckObject.getJSONArray("cards");

            // Load ONE card
            // This is so the user doesn't look at a blank screen when opening the deck.
            // The rest of the cards will be loaded later.

            ObservableList<DisplayableFlashcard> flashcards = FXCollections
                    .observableArrayList();
            for (int i = 0, c = cards.length(); i < c; i++) {
                JSONObject card = cards.getJSONObject(i);
                flashcards.add(FlashcardFactory.build(card));

            }

            // Create a new deck with the name, description, and flashcard list
            String title = deckObject.getString("title");
            String description = deckObject.getString("description");
            Deck e = new Deck(title, description, flashcards, jsonFile);
            decks.add(e);
        }
    }

    public ObservableList<Deck> getDecks() {
        return decks;
    }

    public int getTotalDecks() {
        return totalDecks.get();
    }

    public IntegerProperty totalDecksProperty() {
        return totalDecks;
    }

    public void setDecks(ObservableList decks) {
        this.decks = decks;
    }


}
