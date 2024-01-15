package com.kyloapps.domain;

import java.util.List;

public class MultipleChoiceFlashcard implements Flashcard{
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

    public List<AnswerOption<String>> getOptions() {
        return options;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(List<AnswerOption<String>> options) {
        this.options = options;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
