package com.kyloapps.View;

import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class SimpleFlashcardView extends DisplayableFlashcard<String> {
    private Label answer;
    private GridPane container = getContainer();
    public static final String typeString = "classic";

    /* A SimpleFlashcard is a normal flashcard, but there is just one AnswerChoice which is always correct. */

    public SimpleFlashcardView(String question, List<AnswerChoice<String>> options, String imageURL) {
        super(question, options, imageURL);
        answer = new Label(options.get(0).getContent());
        answer.setVisible(false);
        StackPane answerPane = new StackPane(answer);
        answerPane.setAlignment(Pos.CENTER);
        container.add(answerPane, 0, 1);
    }

    @Override
    public String getTypeString() {
        return typeString;
    }

    @Override
    public void applyStep(int newVal) {
        checkStep(newVal);
        switch (newVal) {
            case 0:
                answer.setVisible(false);
                break;
            case 1:
                answer.setVisible(true);
                break;
        }
    }

    @Override
    public int getStepCount() {
        return 2;
    }
}
