package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultipleChoiceQuestionCreator extends VBox implements QuestionCreator {
    private TextField questionInput;
    private TextField imageInput;
    private VBox optionsBox;
    private Spinner count;

    public MultipleChoiceQuestionCreator() {
        Tile question = new Tile("Question", "Enter a question.");
        questionInput = new TextField();
        question.setAction(questionInput);
        getChildren().add(question);

        Tile imageUrl = new Tile("Image URL", "(optional)");
        imageInput = new TextField();
        imageUrl.setAction(imageInput);
        getChildren().add(imageUrl);

        Tile optionCount = new Tile("Option Count", "Enter the number of possible answers.");
        count = new Spinner<Integer>(1, 10, 1);
        optionCount.setAction(count);
        getChildren().add(optionCount);

        optionsBox = new VBox();

        optionsBox.getChildren().add(new MultipleChoiceTile());
        count.valueProperty().addListener((obs, oldValue, newValue) -> {
//            Platform.runLater(() -> {
                if ((int) newValue < (int) oldValue) {
                    List<Node> tiles = IntStream.range(0, (int) newValue).mapToObj((val) -> optionsBox.getChildren().get(val)).collect(Collectors.toList());
                    optionsBox.getChildren().setAll(tiles);
                } else {
                    for (int i = (int) oldValue; i < (int) newValue; i++) {
                        optionsBox.getChildren().add(new MultipleChoiceTile());
                    }
                }
//            });
        });

        getChildren().add(optionsBox);
    }

    @Override
    public Node get() {
        return this;
    }

    @Override
    public DisplayableFlashcard build() {
        String question = questionInput.getText();
        List<AnswerChoice<String>> answerChoices = optionsBox.getChildren().stream()
                .map((child) -> {
                    MultipleChoiceTile tile = ((MultipleChoiceTile) child);
                    return new AnswerChoice<>(tile.getInput(), tile.isCorrect());
                }).collect(Collectors.toList());
        String imageURL = imageInput.getText();
        if (imageURL.isEmpty()) {
            return new MultipleChoiceFlashcardView(question, answerChoices, null);
        }
        return new MultipleChoiceFlashcardView(question, answerChoices, imageURL);
    }

    @Override
    public boolean isComplete() {
        // If any of the options are empty
        return !(questionInput.getText().isEmpty() || optionsBox.getChildren().stream()
                .anyMatch((child) -> ((MultipleChoiceTile) child).getInput().isEmpty()));
    }

    @Override
    public void fillFromFlashcard(DisplayableFlashcard flashcard) {
        Platform.runLater(() -> {
            questionInput.setText(flashcard.getQuestion());
            if (flashcard.getImageURL() != null) {
                imageInput.setText(flashcard.getImageURL());
            }

            int length = flashcard.getOptions().size();

            count.getValueFactory().setValue(length);
            for (int i = 0, c = length; i < c; i++) {
                MultipleChoiceTile tile = (MultipleChoiceTile) optionsBox.getChildren().get(i);
                AnswerChoice<String> option = (AnswerChoice<String>) flashcard.getOptions().get(i);
                tile.setContent(option.getContent());
                tile.setCorrect(option.isCorrect());
            }
        });
    }
}
