package com.kyloapps.View;

import atlantafx.base.theme.Styles;
import com.kyloapps.DisplayableFlashcard;
import com.kyloapps.Model.AnswerChoice;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class TableFlashcardView extends DisplayableFlashcard {
    private IntegerProperty currentStep = new SimpleIntegerProperty(0);
    private GridPane container = getContainer();
    private List<HBox> correctAnswers = new ArrayList<>();
    private GridPane table;
    private String[] headers;
    public static final String typeString = "table";

    public TableFlashcardView(String question, List<AnswerChoice<String[]>> options, String[] headers, String imageURL) {
        super(question, options, imageURL);
        this.headers = headers;

        // Create a gridpane which is styled to look like a simple table.

        table = new GridPane();
        int columns = headers.length;
        int rows = options.size() + 1;
        table.getStyleClass().add("tableGrid");
        table.setVisible(false);

        // Fill the first column with the headers
        for (int i = 0; i < columns; i++) {
            Label label = new Label(headers[i]);
            label.getStyleClass().add(Styles.TEXT_BOLD);
            HBox hBox = new HBox(label);
            hBox.getStyleClass().add("cell");
            hBox.setAlignment(Pos.CENTER);
            table.add(hBox, i, 0);
        }

        // The other columns should be the contents of the AnswerChoices
        for (int r = 1; r < rows - 1; r++) {
            AnswerChoice<String[]> answerChoice = options.get(r);
            for (int c = 0; c < columns; c++) {

                Label label = new Label(answerChoice.getContent()[c]);
                HBox hBox = new HBox(label);

                hBox.setAlignment(Pos.CENTER);
                hBox.getStyleClass().add("cell");
                table.add(hBox, c, r);

                if (answerChoice.isCorrect()) {
                    correctAnswers.add(hBox);
                }
            }
        }

        table.setAlignment(Pos.CENTER);
        StackPane tablePane = new StackPane(table);
        container.add(tablePane, 0, 1);
    }

    @Override
    public void applyStep(int newVal) {
        checkStep(newVal);
        switch (newVal) {
            case 0:
                table.setVisible(false);
                break;
            case 1:
                table.setVisible(true);
                correctAnswers.forEach(hBox -> {
                    hBox.getStyleClass().remove("correctAnswerCell");
                });
                break;
            case 2:
                correctAnswers.forEach(hBox -> {
                    hBox.getStyleClass().add("correctAnswerCell");
                });
                break;
        }
    }

    @Override
    public int getStepCount() {
        return 3;
    }

    @Override
    public String getTypeString() {
        return typeString;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }
}
