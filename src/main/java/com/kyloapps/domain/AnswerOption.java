package com.kyloapps.domain;

public class AnswerOption<T> {
    private boolean correct;
    private T content;

    public AnswerOption(boolean correct, T content) {
        this.correct = correct;
        this.content = content;
    }

    public AnswerOption() {

    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
