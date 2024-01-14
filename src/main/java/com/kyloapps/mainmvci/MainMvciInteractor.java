package com.kyloapps.mainmvci;

import javafx.beans.binding.Bindings;

public class MainMvciInteractor {
    private final MainMvciModel model;
    public MainMvciInteractor(MainMvciModel model) {
        this.model = model;
        registerDeckLoader();
    }

    private void registerDeckLoader() {

    }
}