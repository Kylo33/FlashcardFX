package com.kyloapps.deckeditor.cardeditor;

import atlantafx.base.theme.Styles;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

/** Subclass of TextFieldTile that includes an option to be correct or not. */
public class TextFieldTileAnswerOption extends TextFieldTile{

    private final DirtyBooleanProperty correct;

    public TextFieldTileAnswerOption(String title, String description) {
        this(title, description, false);
    }

    public TextFieldTileAnswerOption(String title, String description, boolean correct) {
        super(title, description);
        this.correct = new DirtyBooleanProperty(correct);

        // Add a "correct" checkbox toggle to the left of the TextField s provided by TextFieldTile
        Node textFieldTileActionNode = getAction();
        setAction(new HBox(TEXT_BOX_PADDING, createCorrectToggle(), textFieldTileActionNode));
    }

    private Node createCorrectToggle() {
        ToggleButton result = new ToggleButton(null, new FontIcon(MaterialDesignC.CHECK));
        result.getStyleClass().addAll(".success-toggle", Styles.BUTTON_ICON);
        result.selectedProperty().bindBidirectional(this.correct);
        return result;
    }

    @Override
    public CompositeDirtyProperty getCompositeDirtyProperty() {
        CompositeDirtyProperty result = super.getCompositeDirtyProperty();
        result.add(this.correct);
        return result;
    }
}
