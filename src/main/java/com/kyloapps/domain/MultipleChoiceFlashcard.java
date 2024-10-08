package com.kyloapps.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class MultipleChoiceFlashcard implements Flashcard {
    private StringProperty question = new SimpleStringProperty("");
    private StringProperty imageUrl = new SimpleStringProperty();
    private ObservableList<AnswerOption<StringProperty>> options = FXCollections.observableArrayList();

    public MultipleChoiceFlashcard() {}

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public ObservableList<AnswerOption<StringProperty>> getOptions() {
        return options;
    }

    public void setOptions(ObservableList<AnswerOption<StringProperty>> options) {
        this.options = options;
    }

    @JsonDeserialize
    public void setOptions(List<AnswerOption<String>> options) {
        this.options.setAll(
                options.stream().
                        map(stringAnswerOption -> {
                            return new AnswerOption<StringProperty>(stringAnswerOption.isCorrect(), new SimpleStringProperty(stringAnswerOption.getContent()));
                        }).collect(Collectors.toList()));
    }

    @Override
    public <T> T accept(FlashcardVisitor<T> flashcardVisitor) {
        return flashcardVisitor.visit(this);
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
}
