package com.kyloapps.deckeditor.cardeditor;

public class CardEditorMvciInteractor {
    private final CardEditorMvciModel model;

    public CardEditorMvciInteractor(CardEditorMvciModel model) {
        this.model = model;
        model.controllerProperty().addListener((c) -> {
            model.getController().getDirtyProperty().isDirtyProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("CardController is dirty: " + newValue);
            });
        });
    }
}
