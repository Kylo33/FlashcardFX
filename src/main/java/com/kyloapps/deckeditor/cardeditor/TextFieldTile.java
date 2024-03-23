package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.dirty.DeepDirtyList;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.util.stream.IntStream;

/** A tile with a variable number of TextFields (can be bound) */
public class TextFieldTile extends Tile {
    public static final int TEXT_BOX_PADDING = 5;
    private final ObservableList<TextField> textFields = FXCollections.observableArrayList(createTextField());
    private final IntegerProperty textFieldCount = new SimpleIntegerProperty(textFields.size());
    private final CompositeDirtyProperty masterDirtyProperty = new CompositeDirtyProperty();

    public TextFieldTile(String title, String description) {
        super(title, description);
        setAction(getActionPane());
        masterDirtyProperty.add(new DeepDirtyList<>(textFields, TextFieldTile::createStringDirtyProperty));
        registerTextFieldCountListener();
    }

    private static DirtyProperty createStringDirtyProperty(TextField textField) {
        DirtyStringProperty dirtyStringProperty = new DirtyStringProperty(textField.getText());
        textField.textProperty().bindBidirectional(dirtyStringProperty);
        return dirtyStringProperty;
    }

    private void registerTextFieldCountListener() {
        // Bidirectional binding with listeners so that the TextFieldCount can be bound bidirectionally by other classes.
        textFieldCount.addListener((observableValue, oldValue, newValue) ->
            ListModifications.populateList(textFields, newValue.intValue(), this::createTextField)
        );
        textFields.addListener((InvalidationListener) change -> textFieldCount.set(textFields.size()));
    }

    private TextField createTextField() {
        return new TextField();
    }

    private HBox getActionPane() {
        HBox textFieldBox = new HBox(TEXT_BOX_PADDING);
        Bindings.bindContent(textFieldBox.getChildren(), textFields);
        return textFieldBox;
    }

    //TODO bind bidirectionally to this on the spinner side so that when the dirty list of textfields gets reset,
    // the counter will as well.

    public int getTextFieldCount() {
        return textFieldCount.get();
    }

    public IntegerProperty textFieldCountProperty() {
        return textFieldCount;
    }

    public void setTextFieldCount(int textFieldCount) {
        this.textFieldCount.set(textFieldCount);
    }

    public ObservableList<TextField> getTextFields() {
        return textFields;
    }

    public CompositeDirtyProperty getMasterDirtyProperty() {
        return masterDirtyProperty;
    }

    // TODO check if this works. try removing a blank answer and adding a new one.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextFieldTile that = (TextFieldTile) o;

        return (IntStream.range(0, getTextFieldCount()).allMatch(i -> {
            String thisText = this.getTextFields().get(i).getText();
            String thatText = that.getTextFields().get(i).getText();
            return thisText.equals(thatText);
        }));
    }
}
