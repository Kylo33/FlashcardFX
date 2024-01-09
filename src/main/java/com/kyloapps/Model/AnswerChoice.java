package com.kyloapps.Model;

public class AnswerChoice<T> {
    private T content;
    private boolean correct;

    public AnswerChoice(T content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
