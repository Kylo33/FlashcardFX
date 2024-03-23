package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.theme.Styles;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;

import java.util.Objects;

/** Subclass of TextFieldTile that includes an option to be correct or not. */
public class TextFieldTileAnswerOption extends TextFieldTile{

    public static final String SUCCESS_TOGGLE_STYLE = "success-toggle";
    private final DirtyBooleanProperty correct;

    public TextFieldTileAnswerOption(String title, String description) {
        this(title, description, false);
    }

    public TextFieldTileAnswerOption(String title, String description, boolean correct) {
        super(title, description);

        this.correct = new DirtyBooleanProperty(correct);
        getMasterDirtyProperty().add(this.correct);

//        getTextFields().get(0).textProperty().addListener((observable, oldValue, newValue) ->
//            System.out.println(this.getMasterDirtyProperty().isDirty())
//        );

        Node textFieldTileActionNode = getAction();
        setAction(new HBox(TEXT_BOX_PADDING, createCorrectToggle(), textFieldTileActionNode));
    }

    private Node createCorrectToggle() {
        ToggleButton result = new ToggleButton(null, new FontIcon(MaterialDesignC.CHECK));
        result.getStyleClass().addAll(SUCCESS_TOGGLE_STYLE, Styles.BUTTON_ICON);
        result.selectedProperty().bindBidirectional(this.correct);
        return result;
    }

    @Override
    public String toString() {
        return getTextFields().get(0).getText();
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
