package com.kyloapps.View;

import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleChoiceFlashcardView extends DisplayableFlashcard<String> {
    private static final int STEP_COUNT = 3;
    private IntegerProperty currentStep = new SimpleIntegerProperty(0);
    private List<Label> correctAnswers = new ArrayList<>();
    private GridPane container = getContainer();
    private VBox optionBox = new VBox();
    public static final String typeString = "multipleChoice";

    public MultipleChoiceFlashcardView(String question, List<AnswerChoice<String>> options, String imageURL) {
        super(question, options, imageURL);

        // Add the AnswerChoices to optionBox
        Collections.shuffle(options);
        for (AnswerChoice<String> option: options) {
            Label label = new Label(option.getContent(), new FontIcon(MaterialDesignC.CIRCLE));
            optionBox.getChildren().add(label);

            // Maintain a list of the correct answers (for styling later)
            if (option.isCorrect()) {
                correctAnswers.add(label);
            }
        }

        // Starts invisible, changes as steps go.
        optionBox.setVisible(false);

        // Populate the containers.
        HBox optionBoxContainer = new HBox();
        optionBoxContainer.getChildren().add(optionBox);

        container.add(new ScrollPane(optionBoxContainer), 0, 1);

        currentStep.set(0);

        // Styling
        optionBox.setSpacing(7);
        optionBox.setAlignment(Pos.CENTER_LEFT);

        optionBoxContainer.setAlignment(Pos.CENTER);
    }

    @Override
    public String getTypeString() {
        return typeString;
    }

    public int getStepCount() {
        return STEP_COUNT;
    }

    @Override
    public void applyStep(int newVal) {
        checkStep(newVal);
        switch (newVal) {
            case 0:
                optionBox.setVisible(false);
                break;
            case 1:
                for (Label label: correctAnswers) {
                    label.getStyleClass().remove("correctAnswer");
                    label.setGraphic(new FontIcon(MaterialDesignC.CIRCLE));
                }
                optionBox.setVisible(true);
                break;
            case 2:
                for (Label label: correctAnswers) {
                    label.getStyleClass().add("correctAnswer");
                    label.setGraphic(new FontIcon(MaterialDesignC.CHECKBOX_MARKED_CIRCLE));
                }
                break;
        }
    }
}
