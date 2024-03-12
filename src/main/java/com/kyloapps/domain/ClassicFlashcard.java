package com.kyloapps.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassicFlashcard implements Flashcard {
    private StringProperty question = new SimpleStringProperty();
    private StringProperty answer = new SimpleStringProperty();

    public ClassicFlashcard(String question, String answer) {
        this.question.set(question);
        this.answer.set(answer);
    }

    public ClassicFlashcard() {
    }

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getAnswer() {
        return answer.get();
    }

    public StringProperty answerProperty() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer.set(answer);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
