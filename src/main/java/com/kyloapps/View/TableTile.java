package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import atlantafx.base.theme.Styles;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableTile extends Tile {
    private TextField textField;
    List<TextField> textFieldList = new ArrayList<>();
    private ReadOnlyObjectProperty columnCount;
    private HBox actions;

    public TableTile(String title, String description, ReadOnlyObjectProperty columnCount) {
        super(title, description);
        this.columnCount = columnCount;
        actions = getActions(columnCount);
        setAction(actions);
    }

    private HBox getActions(ReadOnlyObjectProperty columnCount) {
        HBox actions = new HBox();
        actions.setSpacing(5);

        for (int i = 0, c = (int) columnCount.get(); i < c; i++) {
            actions.getChildren().add(new TextField());
        }

        columnCount.addListener((obs, oldValue, newValue) -> {
            if ((int) newValue < (int) oldValue) {
                List<Node> tiles = IntStream.range(0, (int) newValue).mapToObj((val) -> actions.getChildren().get(val)).collect(Collectors.toList());
                actions.getChildren().setAll(tiles);
            } else {
                for (int i = (int) oldValue; i < (int) newValue; i++) {
                    actions.getChildren().add(new TextField());
                }
            }
        });
        return actions;
    }

    public String[] getContents() {
        return actions.getChildren().stream().map((child) -> ((TextField) child).getText()).toArray(String[]::new);
    }

    public void setContents(String[] strings) {
        for (int i = 0, c = strings.length; i < c; i++) {
            ((TextField) actions.getChildren().get(i)).setText(strings[i]);
        }
    }

    public boolean isCompleted() {
        // If any of the contents are empty strings.
        return !Arrays.stream(getContents()).anyMatch((str) -> str.isEmpty());
    }
}
