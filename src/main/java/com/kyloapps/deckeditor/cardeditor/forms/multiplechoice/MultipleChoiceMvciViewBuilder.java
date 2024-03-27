package com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;

import atlantafx.base.controls.Tile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.tobiasdiez.easybind.EasyBind;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class MultipleChoiceMvciViewBuilder implements Builder<Region> {
    private final MultipleChoiceMvciModel model;
    private final Consumer<Integer> changeOptionCountAction;
    public MultipleChoiceMvciViewBuilder(MultipleChoiceMvciModel model,
                                         Consumer<Integer> changeOptionCountAction) {
        this.model = model;
        this.changeOptionCountAction = changeOptionCountAction;
    }

    @Override
    public Region build() {
        VBox optionBox = new VBox();
        // Bindings.bindContent(optionBox.getChildren(), model.getOptionTiles());
        // Gives IndexOutOfBoundsException. I think it's a bug with JavaFX:
        // https://stackoverflow.com/questions/27769583/calling-clear-on-observablelist-causes-indexoutofboundsexception/27773466#27773466
        Platform.runLater(() -> {
            optionBox.getChildren().setAll(model.getOptionTiles());
        });
        model.getOptionTiles().addListener((InvalidationListener) observable -> {
            Platform.runLater(() -> {
                optionBox.getChildren().setAll(model.getOptionTiles());
            });
        });
        return new VBox(15, model.getQuestionTile(), createSpinnerTile(), optionBox);
    }

    private Region createSpinnerTile() {
        Tile spinnerTile =
                new Tile("Possible Answers", "Enter the number of answer choices.");

        Spinner<Integer> answerCountSpinner =
                new Spinner<>(MultipleChoiceMvciModel.MINIMUM_MCQ_COUNT,
                        MultipleChoiceMvciModel.MAXIMUM_MCQ_COUNT,
                        model.getOptionTiles().size());

        answerCountSpinner.valueProperty().addListener(
                (observable, oldValue,
                 newValue) -> changeOptionCountAction.accept(newValue));

        spinnerTile.setAction(answerCountSpinner);
        return spinnerTile;
    }
}
