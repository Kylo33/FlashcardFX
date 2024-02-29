package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.theme.Styles;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class TableRowInputAnswer extends TableRowInput {
    private final BooleanProperty correct = new SimpleBooleanProperty();

    public TableRowInputAnswer(ObservableValue<Integer> columnCount, String title, String description, boolean isCorrect) {
        super(columnCount, title, description);
        ((HBox) getAction()).getChildren().add(0, createCorrectButton());
        this.correct.set(isCorrect);
    }

    private Node createCorrectButton() {
        ToggleButton toggleButton = new ToggleButton(null, new FontIcon(MaterialDesignC.CHECK));
        toggleButton.getStyleClass().addAll(Styles.BUTTON_ICON, "success-toggle");
        toggleButton.selectedProperty().bindBidirectional(correct);
        return toggleButton;
    }

    public boolean isCorrect() {
        return correct.get();
    }

    public BooleanProperty correctProperty() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct.set(correct);
    }
}