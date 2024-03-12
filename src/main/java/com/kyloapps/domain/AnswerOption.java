package com.kyloapps.domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AnswerOption<T> {
    private BooleanProperty correct = new SimpleBooleanProperty();
    private T content;

    public AnswerOption(boolean correct, T content) {
        this.correct.set(correct);
        this.content = content;
    }

    public AnswerOption() {

    }

    public boolean isCorrect() {
        return correct.get();
    }

    public BooleanProperty correctProperty() {
        return correct;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
