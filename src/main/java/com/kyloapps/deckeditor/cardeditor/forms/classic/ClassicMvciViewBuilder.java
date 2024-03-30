package com.kyloapps.deckeditor.cardeditor.forms.classic;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public class ClassicMvciViewBuilder implements Builder<Region> {
    private final ClassicMvciModel model;

    public ClassicMvciViewBuilder(ClassicMvciModel model) {
        this.model = model;
    }

    @Override
    public Region build() {
        return new VBox(15,
                createSimpleBoundTile("Question", "Enter the flashcard's question.",
                        model.questionProperty()),
                createSimpleBoundTile("Image", "Enter an image URL — optional.",
                        model.imageUrlProperty()),
                createSimpleBoundTile("Answer", "Enter the flashcard's answer.",
                        model.answerProperty()));
    }

    public static Node createSimpleBoundTile( String title, String desc, StringProperty propertyToBindTo) {
        TextFieldTile result = new TextFieldTile(title, desc);
        result.getTextFields().get(0).textProperty().bindBidirectional(propertyToBindTo);
        return result;
    }
}
