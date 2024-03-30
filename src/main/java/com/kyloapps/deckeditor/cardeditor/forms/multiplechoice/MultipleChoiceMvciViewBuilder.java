package com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;

import atlantafx.base.controls.Tile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.tobiasdiez.easybind.EasyBind;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class MultipleChoiceMvciViewBuilder implements Builder<Region> {
    private final MultipleChoiceMvciModel model;
    private final ObjectProperty<Integer> rowSpinnerValue;
    private final ObservableList<TextFieldTileAnswerOption> textFieldTileAnswerOptions;
    public MultipleChoiceMvciViewBuilder(MultipleChoiceMvciModel model) {
        this.model = model;
        this.rowSpinnerValue = model.optionCountProperty().asObject();
        this.textFieldTileAnswerOptions = createTextFieldTileAnswerOptionBoundList();
    }

    private ObservableList<TextFieldTileAnswerOption> createTextFieldTileAnswerOptionBoundList() {
        return EasyBind.mapBacked(model.getOptions(), answerOption -> {
            TextFieldTileAnswerOption result = new TextFieldTileAnswerOption("Answer Option", "Enter an answer choice.", answerOption.isCorrect());
            result.getTextFields().get(0).textProperty().bindBidirectional(answerOption.getContent());
            answerOption.correctProperty().bindBidirectional(result.correctProperty());
            return result;
        });
    }

    @Override
    public Region build() {
        return new VBox(15, createQuestionTile(), createImageTile(), createSpinnerTile(), createOptionBox());
    }

    private Node createOptionBox() {
        VBox optionBox = new VBox();
        Platform.runLater(() -> optionBox.getChildren().setAll(textFieldTileAnswerOptions));
        textFieldTileAnswerOptions.addListener((InvalidationListener) observable
                -> Platform.runLater(
                () -> optionBox.getChildren().setAll(textFieldTileAnswerOptions))); // watch out for GC
        return optionBox;
    }

    private Node createQuestionTile() {
        TextFieldTile result = new TextFieldTile("Question", "Enter the flashcard's question.");
        result.getTextFields().get(0).textProperty().bindBidirectional(model.questionProperty());
        return result;
    }

    private Node createImageTile() {
        TextFieldTile result = new TextFieldTile("Image", "Enter an image URL — optional.");
        result.getTextFields().get(0).textProperty().bindBidirectional(model.imageUrlProperty());
        return result;
    }

    private Region createSpinnerTile() {
        Tile spinnerTile =
                new Tile("Possible Answers", "Enter the number of answer choices.");

        Spinner<Integer> answerCountSpinner =
                new Spinner<>(MultipleChoiceMvciModel.MINIMUM_MCQ_COUNT,
                        MultipleChoiceMvciModel.MAXIMUM_MCQ_COUNT,
                        model.getOptions().size());
        rowSpinnerValue.bindBidirectional(answerCountSpinner.getValueFactory().valueProperty());
        spinnerTile.setAction(answerCountSpinner);
        return spinnerTile;
    }
}
