package com.kyloapps.deckeditor.cardeditor.forms.classic;

public class ClassicMvciInteractor {
    private final ClassicMvciModel model;

    public ClassicMvciInteractor(ClassicMvciModel model) {
        this.model = model;
        configureCompositeDirtyProperty();
    }

    private void configureCompositeDirtyProperty() {
        model.getCompositeDirtyProperty().addAll(
                model.questionProperty(), model.answerProperty(), model.imageUrlProperty());
    }
}
