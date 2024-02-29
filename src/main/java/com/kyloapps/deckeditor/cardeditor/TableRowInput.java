package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import static com.kyloapps.deckeditor.cardeditor.TextFieldTile.populateList;

public class TableRowInput extends Tile {
    private final ObservableValue<Integer> columnCount;
    private final HBox textFieldBox = new HBox(5);

    public TableRowInput(ObservableValue<Integer> columnCount, String title, String description) {
        setTitle(title);
        setDescription(description);
        this.columnCount = columnCount;
        adjustTextFieldCount();
        columnCount.addListener((observable) -> adjustTextFieldCount());
        setAction(new HBox(5, textFieldBox));
    }

    private void adjustTextFieldCount() {
        populateList(textFieldBox.getChildren(), columnCount.getValue(), TextField::new);
    }
}
