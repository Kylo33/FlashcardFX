package com.kyloapps.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class TableFlashcard implements Flashcard {
    private StringProperty question = new SimpleStringProperty("");
    private StringProperty image = new SimpleStringProperty("");
    private ObservableList<StringProperty> headers = FXCollections.observableArrayList();
    private ObservableList<AnswerOption<ObservableList<StringProperty>>> options = FXCollections.observableArrayList();

    public TableFlashcard() {}

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setOptions(String question) {
        this.question.set(question);
    }

    public ObservableList<StringProperty> getHeaders() {
        return headers;
    }

    public void setHeaders(ObservableList<StringProperty> headers) {
        this.headers = headers;
    }

    @JsonDeserialize
    public void setHeaders(List<String> headers) {
        this.headers.setAll(
                headers.stream()
                        .map(SimpleStringProperty::new)
                        .collect(Collectors.toList())
        );
    }

    public ObservableList<AnswerOption<ObservableList<StringProperty>>> getOptions() {
        return options;
    }

    public void setOptions(ObservableList<AnswerOption<ObservableList<StringProperty>>> options) {
        this.options = options;
    }

    @JsonDeserialize
    public void setOptions(List<AnswerOption<List<String>>> options) {
        this.options.clear();
        options.stream().map( // Map each AnswerOption to an AnswerOption with observablelist of StringProperty
                answerOptionOfLists -> new AnswerOption<ObservableList<StringProperty>>(
                        answerOptionOfLists.isCorrect(),
                        answerOptionOfLists.getContent().stream().map(SimpleStringProperty::new).collect(Collectors.toCollection(FXCollections::observableArrayList))
                )
        ).forEachOrdered(this.options::add);
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    @Override
    public <T> T accept(FlashcardVisitor<T> flashcardVisitor) {
        return flashcardVisitor.visit(this);
    }

    public String getImage() {
        return image.get();
    }

    public StringProperty imageProperty() {
        return image;
    }

    public void setImage(String image) {
        this.image.set(image);
    }
}
