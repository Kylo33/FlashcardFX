package com.kyloapps.domain;

import java.util.List;

public class MultipleChoiceFlashcard implements Flashcard{
    private String question;
    private List<MultipleChoiceOption> answers;

    public MultipleChoiceFlashcard(String question, List<MultipleChoiceOption> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<MultipleChoiceOption> getAnswers() {
        return answers;
    }

    public void setAnswers(List<MultipleChoiceOption> answers) {
        this.answers = answers;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
