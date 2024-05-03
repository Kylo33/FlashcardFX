package com.kyloapps.deckeditor.cardeditor.forms;

import atlantafx.base.controls.Tile;
import com.kyloapps.utils.ListModifications;
import com.kyloapps.utils.DeepDirtyList;
import com.tobiasdiez.easybind.EasyBind;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private final ObservableList<StringProperty> stringProperties;
    private final ObservableList<TextField> textFields;

    public TextFieldTile(String title, String description, ObservableList<StringProperty> stringProperties) {
        super(title, description);
        this.stringProperties = stringProperties;
        this.textFields = bindTextFields();
        setAction(getActionPane());
    }

    public TextFieldTile(String title, String description, StringProperty stringProperty) {
        this(title, description, FXCollections.observableArrayList(stringProperty));
    }

    private ObservableList<TextField> bindTextFields() {
        return EasyBind.mapBacked(stringProperties, stringProperty -> {
            TextField result = new TextField(stringProperty.get());
            result.textProperty().bindBidirectional(stringProperty);
            return result;
        });
    }

    private static DirtyProperty createStringDirtyProperty(TextField textField) {
        DirtyStringProperty dirtyStringProperty = new DirtyStringProperty(textField.getText());
        textField.textProperty().bindBidirectional(dirtyStringProperty);
        return dirtyStringProperty;
    }

    private HBox getActionPane() {
        HBox textFieldBox = new HBox(TEXT_BOX_PADDING);
        textFieldBox.getChildren().setAll(textFields);
        textFields.addListener((ListChangeListener<? super TextField>) change -> {
            while (change.next())
                Platform.runLater(() -> textFieldBox.getChildren().setAll(textFields));
        });
        return textFieldBox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextFieldTile that = (TextFieldTile) o;

        int textFieldCount = getTextFields().size();

        return (IntStream.range(0, textFieldCount).allMatch(i -> {
            String thisText = this.getTextFields().get(i).getText();
            String thatText = that.getTextFields().get(i).getText();
            return thisText.equals(thatText);
        }));
    }

    public ObservableList<StringProperty> getStringProperties() {
        return stringProperties;
    }

    public ObservableList<TextField> getTextFields() {
        return textFields;
    }
}
