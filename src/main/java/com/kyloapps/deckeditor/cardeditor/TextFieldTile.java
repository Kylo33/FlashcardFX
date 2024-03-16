package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.controls.Tile;
import com.kyloapps.deckeditor.DirtyUtils;
import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.collections.DirtyObservableList;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.awt.*;

/** A tile with a variable number of TextFields (can be bound) */
public class TextFieldTile extends Tile {
    public static final int TEXT_BOX_PADDING = 5;
    private final DirtyObservableList<TextField> textFields = new DirtyObservableList<>(createTextField());
    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();
    private final IntegerProperty textFieldCount = new SimpleIntegerProperty(textFields.size());

    public TextFieldTile(String title, String description) {
        super(title, description);
        setAction(getActionPane());
        registerTextFieldCountListener();
        bindCompositeProperty();
    }

    private void bindCompositeProperty() {
        DirtyUtils.bindCompositeDirtyProperty(
                compositeDirtyProperty,
                textFields,
                (textField) -> {
                    DirtyStringProperty dirtyStringProperty = new DirtyStringProperty(textField.getText());
                    textField.textProperty().bindBidirectional(dirtyStringProperty);
                    return dirtyStringProperty;
                }
        );
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

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
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
}
