package com.kyloapps.practice;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class PracticeMvciViewBuilder implements Builder<Region> {
    private final PracticeMvciModel model;

    public PracticeMvciViewBuilder(PracticeMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        return new VBox(new Label("practice Page"));
    }
}
