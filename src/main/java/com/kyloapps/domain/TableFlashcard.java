package com.kyloapps.domain;

import java.util.List;

public class TableFlashcard implements Flashcard{
    private String question;
    private List<String> headers;
    private List<AnswerOption<List<String>>> options;

    public TableFlashcard(String question, List<String> headers, List<AnswerOption<List<String>>> options) {
        this.question = question;
        this.headers = headers;
        this.options = options;
    }

    public TableFlashcard() {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<AnswerOption<List<String>>> getOptions() {
        return options;
    }

    public void setOptions(List<AnswerOption<List<String>>> options) {
        this.options = options;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
