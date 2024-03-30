package com.kyloapps.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassicFlashcard implements Flashcard {
    private StringProperty question = new SimpleStringProperty("");
    private StringProperty answer = new SimpleStringProperty("");
    private StringProperty imageUrl = new SimpleStringProperty("");

    public ClassicFlashcard() {}

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

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    @Override
    public <T> T accept(FlashcardVisitor<T> flashcardVisitor) {
        return flashcardVisitor.visit(this);
    }
}
