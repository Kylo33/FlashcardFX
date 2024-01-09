package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableQuestionCreator extends VBox implements QuestionCreator{
    private TextField questionInput;
    private TextField imageInput;
    private TableTile headers;
    private VBox optionsBox = new VBox();
    private Spinner<Integer> optionCounter;
    private Spinner<Integer> columnCounter;

    public TableQuestionCreator() {
        Tile question = new Tile("Question", "Enter a question.");
        questionInput = new TextField();
        question.setAction(questionInput);
        getChildren().add(question);

        Tile imageUrl = new Tile("Image URL", "(optional)");
        imageInput = new TextField();
        imageUrl.setAction(imageInput);
        getChildren().add(imageUrl);

        Tile columnCount = new Tile("Column Count", "Enter the number of columns.");
        columnCounter = new Spinner<Integer>(1, 10, 1);
        columnCount.setAction(columnCounter);
        getChildren().add(columnCount);

        Tile optionCount = new Tile("Option Count", "Enter the number of possible answers.");
        optionCounter = new Spinner<Integer>(1, 10, 1);
        optionCount.setAction(optionCounter);
        getChildren().add(optionCount);

        headers = new TableTile("Headers", "Enter the headers for the table.", columnCounter.valueProperty());
        getChildren().add(headers);

        optionsBox.getChildren().add(getTableTile(columnCounter));
        optionCounter.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue < oldValue) {
                List<Node> tiles = IntStream.range(0, newValue).mapToObj((val) -> optionsBox.getChildren().get(val)).collect(Collectors.toList());
                optionsBox.getChildren().setAll(tiles);
            } else {
                for (int i = oldValue; i < newValue; i++) {
                    optionsBox.getChildren().add(getTableTile(columnCounter));
                }
            }
        });

        getChildren().add(optionsBox);
    }

    private static TableTile getTableTile(Spinner columnCounter) {
        return new TableTileOption("Answer Option", "Enter one row of the table.", columnCounter.valueProperty());
    }

    @Override
    public Node get() {
        return this;
    }

    @Override
    public DisplayableFlashcard build() {
        String question = questionInput.getText();
        String[] headerContents = headers.getContents();
        List<AnswerChoice<String[]>> answerChoices = optionsBox.getChildren().stream().map((child) -> {
            TableTileOption tileOption = ((TableTileOption) child);
            return new AnswerChoice<>(tileOption.getContents(), tileOption.isCorrect());
        }).collect(Collectors.toList());
        String imageUrl = imageInput.getText();
        if (imageUrl.isEmpty()) {
            imageUrl = null;
        }
        return new TableFlashcardView(question, answerChoices, headerContents, imageUrl);
    }

    @Override
    public boolean isComplete() {

        // If the question is not empty, the headers are completed, and the options are completed.
        return (!questionInput.getText().isEmpty() &&
                optionsBox.getChildren().stream().anyMatch((child) -> ((TableTile) child).isCompleted()) &&
                headers.isCompleted());
    }

    @Override
    public void fillFromFlashcard(DisplayableFlashcard flashcard) {
        questionInput.setText(flashcard.getQuestion());

        columnCounter.getValueFactory().setValue(((TableFlashcardView) flashcard).getHeaders().length);
        optionCounter.getValueFactory().setValue(flashcard.getOptions().size());

        headers.setContents(((TableFlashcardView) flashcard).getHeaders());
        for (int i = 0, c = optionsBox.getChildren().size(); i < c; i++) {
            TableTileOption tileOption = (TableTileOption) optionsBox.getChildren().get(i);
            AnswerChoice<String[]> choice = (AnswerChoice<String[]>) flashcard.getOptions().get(i);
            tileOption.setContents(choice.getContent());
            tileOption.setCorrect(choice.isCorrect());
        }
        if (flashcard.getImageURL() != null) {
            imageInput.setText(flashcard.getImageURL());
        }
    }
}
