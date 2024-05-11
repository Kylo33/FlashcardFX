package com.kyloapps.deckeditor.cardeditor.forms;

import atlantafx.base.theme.Styles;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;

/** Subclass of TextFieldTile that includes an option to be correct or not. */
public class TextFieldTileAnswerOption extends TextFieldTile{

    public static final String SUCCESS_TOGGLE_STYLE_CLASS = "success-toggle";
    private final DirtyBooleanProperty correct = new DirtyBooleanProperty(false);

    public TextFieldTileAnswerOption(String title, String description, ObservableList<StringProperty> stringProperties) {
        super(title, description, stringProperties);
        setAction(new HBox(TextFieldTileAnswerOption.TEXT_BOX_PADDING, createCorrectToggle(), getAction()));
    }

    public TextFieldTileAnswerOption(String title, String description, StringProperty stringProperty) {
        this(title, description, FXCollections.observableArrayList(stringProperty));
    }

    private Node createCorrectToggle() {
        ToggleButton result = new ToggleButton(null, new FontIcon(MaterialDesignC.CHECK));
        result.getStyleClass().addAll(SUCCESS_TOGGLE_STYLE_CLASS, Styles.BUTTON_ICON);
        result.selectedProperty().bindBidirectional(this.correct);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TextFieldTileAnswerOption that = (TextFieldTileAnswerOption) o;

        return this.correct.get() == that.correct.get();
    }

    public boolean isCorrect() {
        return correct.get();
    }

    public DirtyBooleanProperty correctProperty() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct.set(correct);
    }
}
