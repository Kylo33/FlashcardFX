package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.nield.dirtyfx.beans.DirtyObjectProperty;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

import java.util.List;
import java.util.function.Supplier;

public class TextFieldTile extends Tile {
    private static final int TEXT_BOX_PADDING = 5;

    private final IntegerProperty textFieldCount = new SimpleIntegerProperty(1);
    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();
    private final Supplier<Node> fieldSupplier = () -> {
        TextField result = new TextField();

        DirtyStringProperty dirtyObjectProperty = new DirtyStringProperty(result.getText());
        dirtyObjectProperty.bindBidirectional(result.textProperty());

        compositeDirtyProperty.add(dirtyObjectProperty);
        return result;
    };
    private final HBox textFieldBox = new HBox(TEXT_BOX_PADDING);
    private final Pane actionPane = new HBox(TEXT_BOX_PADDING, textFieldBox);

    public TextFieldTile(String title, String description) {
        super(title, description);
        setAction(actionPane);

        populateList(textFieldBox.getChildren(), textFieldCount.getValue(), fieldSupplier);
        registerFieldCountListener();
    }

    private void registerFieldCountListener() {
        textFieldCount.addListener((observable, oldValue, newValue) -> populateList(
                textFieldBox.getChildren(),
                (Integer) newValue,
                fieldSupplier
        ));
    }

    public Integer getTextFieldCount() {
        return textFieldCount.get();
    }

    public IntegerProperty textFieldCountProperty() {
        return textFieldCount;
    }

    public Pane getActionPane() {
        return actionPane;
    }

    public static <T> void populateList(List<T> list, int desiredCount, Supplier<T> nodeSupplier) {
        if (desiredCount < 0) throw new IllegalArgumentException("desiredCount must be greater than zero.");
        int currentCount = list.size();
        if (desiredCount > currentCount) {
            for (int i = 0, c = desiredCount - currentCount; i < c; i++)
                list.add(nodeSupplier.get());
        } else if (desiredCount < currentCount) {
            for (int i = 0, c = currentCount - desiredCount; i < c; i++)
                list.remove(list.size() - 1);
        }
    }

    public CompositeDirtyProperty compositeDirtyPropertyProperty() {
        return compositeDirtyProperty;
    }
}
