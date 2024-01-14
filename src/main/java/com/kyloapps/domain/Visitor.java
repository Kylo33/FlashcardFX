package com.kyloapps.domain;

public interface Visitor {
    void visit(ClassicFlashcard flashcard);
    void visit(MultipleChoiceFlashcard flashcard);
}
