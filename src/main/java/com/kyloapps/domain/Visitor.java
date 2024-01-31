package com.kyloapps.domain;

public interface Visitor<T> {
    T visit(ClassicFlashcard flashcard);

    T visit(MultipleChoiceFlashcard flashcard);

    T visit(TableFlashcard flashcard);
}
