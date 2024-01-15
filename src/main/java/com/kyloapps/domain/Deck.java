package com.kyloapps.domain;

import java.util.List;

public class Deck {
     private String title;
     private String description;
     private List<Flashcard> flashcards;

    public Deck(String title, String description, List<Flashcard> flashcards) {
        this.title = title;
        this.description = description;
        this.flashcards = flashcards;
    }

    public Deck() {
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

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", flashcards=" + flashcards +
                '}';
    }
}
