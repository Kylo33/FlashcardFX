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
                createQuestionTile(model.questionProperty()),
                createImageTile(model.imageUrlProperty()),
                new TextFieldTile("Answer", "Enter the flashcard's answer.",
                        model.answerProperty()));
    }

    public static Node createQuestionTile(StringProperty propertyToBindTo) {
        return new TextFieldTile("Question", "Enter the flashcard's question.", propertyToBindTo);
    }

    public static Node createImageTile(StringProperty propertyToBindTo) {
        return new TextFieldTile("Image", "Enter an image URL — optional.", propertyToBindTo);
    }
}
