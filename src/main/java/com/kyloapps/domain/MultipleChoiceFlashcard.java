package com.kyloapps.domain;

import java.util.List;

public class MultipleChoiceFlashcard implements Flashcard {
    private String question;
    private List<AnswerOption<String>> options;

    public MultipleChoiceFlashcard(String question, List<AnswerOption<String>> options) {
        this.question = question;
        this.options = options;
    }

    public MultipleChoiceFlashcard() {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerOption<String>> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerOption<String>> options) {
        this.options = options;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
