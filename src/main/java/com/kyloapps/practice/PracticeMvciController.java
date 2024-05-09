package com.kyloapps.practice;

import com.kyloapps.domain.Deck;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PracticeMvciController {
    private final PracticeMvciViewBuilder view;
    private final PracticeMvciModel model;
    private final PracticeMvciInteractor interactor;

    public PracticeMvciController(ObjectProperty<Deck> currentDeckProperty) {
        model = new PracticeMvciModel();
        interactor = new PracticeMvciInteractor(model);
        view = new PracticeMvciViewBuilder(model, interactor::reload);
        model.currentDeckProperty().bind(currentDeckProperty);
    }

    public Region getView() {
        return view.build();
    }
}
