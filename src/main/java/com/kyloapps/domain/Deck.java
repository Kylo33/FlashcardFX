package com.kyloapps.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @JsonIgnore
    private File file;

    public Deck(String title, String description, ObservableList<Flashcard> flashcards) {
        this.title.set(title);
        this.description.set(description);
        this.flashcards = flashcards;
    }

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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
