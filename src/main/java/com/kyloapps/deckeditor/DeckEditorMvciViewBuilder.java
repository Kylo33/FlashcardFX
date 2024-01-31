package com.kyloapps.deckeditor;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;

public class DeckEditorMvciViewBuilder implements Builder<Region> {
    private final DeckEditorMvciModel model;

    public DeckEditorMvciViewBuilder(DeckEditorMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        BorderPane result = new BorderPane();
        result.setTop(new Label("Hello, wolrd!"));
        return result;
    }
}
