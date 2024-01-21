package com.kyloapps.practice;

import com.kyloapps.domain.Deck;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;

public class PracticeMvciController {
    private final PracticeMvciViewBuilder view;
    public PracticeMvciController(ObjectProperty<Deck> currentDeckProperty) {
        PracticeMvciModel model = new PracticeMvciModel();
        PracticeMvciInteractor interactor = new PracticeMvciInteractor(model);
        view = new PracticeMvciViewBuilder(model, interactor::previousCurrentFlashcard, interactor::nextCurrentFlashcard);
        model.currentDeckProperty().bind(currentDeckProperty);
        model.currentDeckProperty().addListener((observable, oldDeck, newDeck) -> {
            System.out.println("newDeck = " + newDeck);
        });
    }

    public Region getView() {
        return view.build();
    }
}
