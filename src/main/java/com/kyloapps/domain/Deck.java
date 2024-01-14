package com.kyloapps.domain;

import java.util.List;

public class Deck {
     private String title;
     private String description;
     private List flashcards;

    public Deck(String title, String description, List flashcards) {
        this.title = title;
        this.description = description;
        this.flashcards = flashcards;
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

    public List getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List flashcards) {
        this.flashcards = flashcards;
    }
}
