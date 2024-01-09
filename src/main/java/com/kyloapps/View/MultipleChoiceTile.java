package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class MultipleChoiceTile extends Tile {
    private TextField textField;
    private ToggleButton correct;

    public MultipleChoiceTile() {
        setTitle("Answer Choice");
        setDescription("Enter a possible answer");

        textField = new TextField();
        correct = new ToggleButton(null, new FontIcon(MaterialDesignC.CHECK));
        correct.getStyleClass().addAll(Styles.SUCCESS, Styles.BUTTON_ICON);
        HBox actions = new HBox(correct, textField);
        actions.setSpacing(5);

        setAction(actions);
    }

    public boolean isCorrect() {
        return correct.isSelected();
    }

    public String getInput() {
        return textField.getText();
    }

    public void setContent(String content) {
        this.textField.setText(content);
    }

    public void setCorrect(boolean correct) {
        this.correct.setSelected(correct);
    }
}
