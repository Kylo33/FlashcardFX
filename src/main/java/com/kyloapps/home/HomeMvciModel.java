package com.kyloapps.home;

import com.kyloapps.domain.Deck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HomeMvciModel {
    private final ObservableList<Deck> decks = FXCollections.observableArrayList();

    public ObservableList<Deck> getDecks() {
        return decks;
    }
}
