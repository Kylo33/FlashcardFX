package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

public class SimpleFlashcardQuestionCreator extends VBox implements QuestionCreator {
    private TextField questionInput;
    private TextField answerInput;
    private TextField imageInput;

    public SimpleFlashcardQuestionCreator() {
        Tile question = new Tile("Question", "Enter a question.");
        questionInput = new TextField();
        question.setAction(questionInput);
        getChildren().add(question);

        Tile answer = new Tile("Answer", "Enter the correct answer.");
        answerInput = new TextField();
        answer.setAction(answerInput);
        getChildren().add(answer);

        Tile imageUrl = new Tile("Image URL", "(optional)");
        imageInput = new TextField();
        imageUrl.setAction(imageInput);
        getChildren().add(imageUrl);
    }

    @Override
    public boolean isComplete() {
        return !(questionInput.getText().isEmpty() || answerInput.getText().isEmpty());
    }

    @Override
    public void fillFromFlashcard(DisplayableFlashcard flashcard) {
        questionInput.setText(flashcard.getQuestion());
        answerInput.setText(((AnswerChoice<String>) flashcard.getOptions().get(0)).getContent());
        if (flashcard.getImageURL() != null) {
            imageInput.setText(flashcard.getImageURL());
        }
    }

    @Override
    public Node get() {
        return this;
    }

    @Override
    public DisplayableFlashcard build() {
        if (imageInput.getText().isEmpty()) return new SimpleFlashcardView(questionInput.getText(),
                List.of(new AnswerChoice<>(answerInput.getText(), true)),
                null);
        else return new SimpleFlashcardView(questionInput.getText(),
                List.of(new AnswerChoice<>(answerInput.getText(), true)),
                imageInput.getText());

    }
}
