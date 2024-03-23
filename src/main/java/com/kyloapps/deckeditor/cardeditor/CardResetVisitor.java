package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;
import com.kyloapps.domain.Visitor;

public class CardResetVisitor implements Visitor<Void> {
    @Override
    public Void visit(ClassicFlashcard flashcard) {
        flashcard.questionProperty().unbind();
        flashcard.setQuestion("");
        flashcard.answerProperty().unbind();
        flashcard.setAnswer("");
        return null;
    }

    @Override
    public Void visit(MultipleChoiceFlashcard flashcard) {
        flashcard.questionProperty().unbind();
        flashcard.setQuestion("");
        flashcard.getOptions().clear();
        return null;
    }

    @Override
    public Void visit(TableFlashcard flashcard) {
        flashcard.setOptions("");
        flashcard.getHeaders().clear();
        flashcard.getOptions().clear();
        return null;
    }
}
