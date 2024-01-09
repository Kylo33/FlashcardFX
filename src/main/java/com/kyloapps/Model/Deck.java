package com.kyloapps.Model;

import com.kyloapps.DisplayableFlashcard;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private String title;
    private String description;
    private ObservableList<DisplayableFlashcard> flashcards;
    private File pathToSelf;

    public Deck(String title, String description, ObservableList<DisplayableFlashcard> flashcards, File pathToSelf) {
        this.pathToSelf = pathToSelf;
        this.title = title;
        this.description = description;
        this.flashcards = flashcards;
    }

    public static Deck getShuffledDeck(Deck deck) {
        ObservableList<DisplayableFlashcard> tempFlashcardList = FXCollections.observableArrayList();
        tempFlashcardList.addAll(deck.getFlashcards());
        Collections.shuffle(tempFlashcardList);
        return new Deck(deck.getTitle(), deck.getDescription(), tempFlashcardList, deck.getPathToSelf());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObservableList<DisplayableFlashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(ObservableList<DisplayableFlashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @Override
    public String toString() {
        String string = getTitle() + " - " + getFlashcards().size() + " card";
        if (getFlashcards().size() == 1) {
            return string;
        } else {
            return string + "s";
        }
    }

    public File getPathToSelf() {
        return pathToSelf;
    }
}
