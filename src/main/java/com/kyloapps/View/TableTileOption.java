package com.kyloapps.View;

import atlantafx.base.theme.Styles;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class TableTileOption extends TableTile {
    private ToggleButton toggleButton;

    public TableTileOption(String title, String description, ReadOnlyObjectProperty columnCount) {
        super(title, description, columnCount);
        toggleButton = new ToggleButton(null, new FontIcon(MaterialDesignC.CHECK));
        toggleButton.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.SUCCESS);
        HBox newBox = new HBox(toggleButton, getAction());
        newBox.setSpacing(5);
        setAction(newBox);
    }

    public boolean isCorrect() {
        return toggleButton.isSelected();
    }

    public void setCorrect(boolean correct) {
        toggleButton.setSelected(correct);
    }
}
