package com.kyloapps.practice;

import javafx.scene.layout.Region;

public class PracticeMvciController {
    private final PracticeMvciViewBuilder view;
    public PracticeMvciController() {
        PracticeMvciModel model = new PracticeMvciModel();
        PracticeMvciInteractor interactor = new PracticeMvciInteractor(model);
        view = new PracticeMvciViewBuilder(model);
    }

    public Region getView() {
        return view.build();
    }
}
