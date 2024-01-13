package com.kyloapps.View;

import atlantafx.base.controls.Tile;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableTile extends Tile {
    private final ReadOnlyObjectProperty<Integer> columnCount;
    private final HBox actions;

    public TableTile(String title, String description, ReadOnlyObjectProperty<Integer> columnCount) {
        super(title, description);
        this.columnCount = columnCount;
        actions = getActions();
        setAction(actions);
    }

    private HBox getActions() {
        HBox actions = new HBox();
        actions.setSpacing(5);

        for (int i = 0, c = columnCount.get(); i < c; i++) {
            actions.getChildren().add(new TextField());
        }

        columnCount.addListener((obs, oldValue, newValue) -> {
            if (newValue < oldValue) {
                List<Node> tiles = IntStream.range(0, newValue).mapToObj((val) -> actions.getChildren().get(val)).collect(Collectors.toList());
                actions.getChildren().setAll(tiles);
            } else {
                for (int i = oldValue; i < newValue; i++) {
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
        return Arrays.stream(getContents()).noneMatch(String::isEmpty);
    }
}
