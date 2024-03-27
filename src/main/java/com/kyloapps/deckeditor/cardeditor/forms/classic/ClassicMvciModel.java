package com.kyloapps.deckeditor.cardeditor.forms.classic;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class ClassicMvciModel {
    private final TextFieldTile questionTile = new TextFieldTile("Question", "Enter the flashcard's question.");
    private final TextFieldTile answerTile = new TextFieldTile("Answer", "Enter the flashcard's answer.");
    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    public TextFieldTile getQuestionTile() {
        return questionTile;
    }

    public TextFieldTile getAnswerTile() {
        return answerTile;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }
}
