package com.kyloapps.domain;

public interface FlashcardVisitor<T> {
    T visit(ClassicFlashcard flashcard);

    T visit(MultipleChoiceFlashcard flashcard);

    T visit(TableFlashcard flashcard);
}
