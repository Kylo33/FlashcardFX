package com.kyloapps.View;

public class QuestionCreatorFactory {
    public static QuestionCreator build(String flashcardType) {
        switch (flashcardType) {
            case SimpleFlashcardView.typeString:
                return new SimpleFlashcardQuestionCreator();
            case MultipleChoiceFlashcardView.typeString:
                return new MultipleChoiceQuestionCreator();
            case TableFlashcardView.typeString:
                return new TableQuestionCreator();
            default:
                throw new IllegalStateException("Unexpected value: " + flashcardType);
        }
    }
}
