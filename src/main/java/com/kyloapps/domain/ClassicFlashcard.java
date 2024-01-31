package com.kyloapps.domain;

public class ClassicFlashcard implements Flashcard {
    private String question;
    private String answer;

    public ClassicFlashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public ClassicFlashcard() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
