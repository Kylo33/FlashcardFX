package com.kyloapps.domain;

public class MultipleChoiceOption {
    private boolean correct;
    private String content;

    public MultipleChoiceOption(boolean correct, String content) {
        this.correct = correct;
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
