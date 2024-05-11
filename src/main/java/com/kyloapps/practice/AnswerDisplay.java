package com.kyloapps.practice;

import javafx.scene.layout.Region;

public class AnswerDisplay {
    private final Runnable backAction;
    private final Runnable forwardAction;
    private final Region answerRegion;

    public AnswerDisplay(Region answerRegion, Runnable forwardAction, Runnable backAction) {
        this.backAction = backAction;
        this.forwardAction = forwardAction;
        this.answerRegion = answerRegion;
    }

    public Runnable getBackAction() {
        return backAction;
    }

    public Runnable getForwardAction() {
        return forwardAction;
    }

    public Region getAnswerRegion() {
        return answerRegion;
    }
}
