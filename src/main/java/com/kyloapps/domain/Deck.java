package com.kyloapps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;


public class Deck {
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private ObservableList<Flashcard> flashcards;

    public Deck() {
        flashcards = FXCollections.observableArrayList();
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public ObservableList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(ObservableList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @JsonDeserialize
    public void setFlashcards(List<Flashcard> flashcards){
        this.flashcards.setAll(flashcards);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "title=" + title +
                ", description=" + description +
                ", flashcards=" + flashcards +
                '}';
    }
}
