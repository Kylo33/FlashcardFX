package com.kyloapps.domain;

public interface Visitor {
    void visit(ClassicFlashcard flashcard);
    void visit(MultipleChoiceFlashcard flashcard);
    void visit(TableFlashcard flashcard);
}
