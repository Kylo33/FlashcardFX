package com.kyloapps.practice;

import com.kyloapps.domain.ClassicFlashcard;
import com.kyloapps.domain.MultipleChoiceFlashcard;
import com.kyloapps.domain.TableFlashcard;
import com.kyloapps.domain.FlashcardVisitor;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class PracticeViewFlashcardVisitor implements FlashcardVisitor<Node> {

    @Override
    public Node visit(ClassicFlashcard flashcard) {
        return new Label("normi! " + flashcard.getQuestion());
    }

    @Override
    public Node visit(MultipleChoiceFlashcard flashcard) {
        return new Label("mcq! " + flashcard.getOptions().toString());
    }

    @Override
    public Node visit(TableFlashcard flashcard) {
        return new Label("Tabl! " + flashcard.getHeaders().toString());
    }
}
