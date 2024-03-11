package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.theme.Styles;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;

public class TextFieldTileAnswerOption extends TextFieldTile{
    private static final String SUCCESS_TOGGLEBUTTON_STYLE = "success-toggle";

    private final DirtyBooleanProperty correct;
    public TextFieldTileAnswerOption(String title, String description, boolean isCorrect) {
        super(title, description);

        correct = new DirtyBooleanProperty(isCorrect);
        compositeDirtyPropertyProperty().add(correct);

        getActionPane().getChildren().add(0, createCorrectButton());
    }

    private Node createCorrectButton() {
        ToggleButton resultButton = new ToggleButton();
        resultButton.setGraphic(new FontIcon(MaterialDesignC.CHECK));
        resultButton.getStyleClass().addAll(Styles.BUTTON_ICON, SUCCESS_TOGGLEBUTTON_STYLE);
        resultButton.selectedProperty().bindBidirectional(correct);
        return resultButton;
    }
}
