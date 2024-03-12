package com.kyloapps.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MultipleChoiceFlashcard implements Flashcard {
    private StringProperty question = new SimpleStringProperty();
    private ObservableList<AnswerOption<String>> options = FXCollections.observableArrayList();

    public MultipleChoiceFlashcard(String question, List<AnswerOption<String>> options) {
        this.question.set(question);
        this.options.setAll(options);
    }

    public MultipleChoiceFlashcard() {
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

    public ObservableList<AnswerOption<String>> getOptions() {
        return options;
    }

    public void setOptions(ObservableList<AnswerOption<String>> options) {
        this.options = options;
    }

    @JsonDeserialize
    public void setOptions(List<AnswerOption<String>> options) {
        this.options.setAll(options);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
