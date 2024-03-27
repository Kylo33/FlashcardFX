package com.kyloapps.deckeditor.cardeditor.forms.classic;

import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class ClassicMvciViewBuilder implements Builder<Region> {
    private final ClassicMvciModel model;

    public ClassicMvciViewBuilder(ClassicMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        return new VBox(15, model.getQuestionTile(), model.getAnswerTile());
    }
}
