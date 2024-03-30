package com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;

import atlantafx.base.controls.Tile;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class MultipleChoiceMvciViewBuilder implements Builder<Region> {
    private final MultipleChoiceMvciModel model;
    private final ObjectProperty<Integer> rowSpinnerValue;
    public MultipleChoiceMvciViewBuilder(MultipleChoiceMvciModel model) {
        this.model = model;
        this.rowSpinnerValue = model.optionCountProperty().asObject();
    }

    @Override
    public Region build() {
        VBox optionBox = new VBox();
        // Bindings.bindContent(optionBox.getChildren(), model.getOptionTiles());
        // Gives IndexOutOfBoundsException. I think it's a bug with JavaFX:
        // https://stackoverflow.com/questions/27769583/calling-clear-on-observablelist-causes-indexoutofboundsexception/27773466#27773466
        Platform.runLater(() -> optionBox.getChildren().setAll(model.getOptionTiles()));
        model.getOptionTiles().addListener((InvalidationListener) observable
                -> Platform.runLater(
                () -> optionBox.getChildren().setAll(model.getOptionTiles())));
        return new VBox(15, model.getQuestionTile(), model.getImageTile(), createSpinnerTile(), optionBox);
    }

    private Region createSpinnerTile() {
        Tile spinnerTile =
                new Tile("Possible Answers", "Enter the number of answer choices.");

        Spinner<Integer> answerCountSpinner =
                new Spinner<>(MultipleChoiceMvciModel.MINIMUM_MCQ_COUNT,
                        MultipleChoiceMvciModel.MAXIMUM_MCQ_COUNT,
                        model.getOptionTiles().size());
        rowSpinnerValue.bindBidirectional(answerCountSpinner.getValueFactory().valueProperty());
        spinnerTile.setAction(answerCountSpinner);
        return spinnerTile;
    }
}
