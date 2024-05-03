package com.kyloapps.deckeditor.cardeditor.forms.classic;

import com.kyloapps.deckeditor.cardeditor.forms.CardControllerVisitor;
import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.domain.ClassicFlashcard;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class ClassicMvciController implements CardController<ClassicFlashcard> {

    private final ClassicMvciModel model;
    private final ClassicMvciInteractor interactor;
    private final ClassicMvciViewBuilder viewBuilder;

    public ClassicMvciController() {
        this.model = new ClassicMvciModel();
        this.interactor = new ClassicMvciInteractor(model);
        this.viewBuilder = new ClassicMvciViewBuilder(model);

        model.getCompositeDirtyProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue + " dirty");
            System.out.println(model.getQuestion());
        });
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    @Override
    public DirtyProperty getDirtyProperty() {
        return model.getCompositeDirtyProperty();
    }

    @Override
    public void loadCard(ClassicFlashcard flashcard) {
        model.setQuestion(flashcard.getQuestion());
        model.setAnswer(flashcard.getAnswer());
        if (flashcard.getImageUrl() != null)
            model.setImageUrl(flashcard.getImageUrl());
    }

    @Override
    public ClassicFlashcard toFlashcard() {
        ClassicFlashcard result = new ClassicFlashcard();
        result.setQuestion(model.getQuestion());
        result.setAnswer(model.getAnswer());
        result.setImageUrl(model.getImageUrl());
        return result;
    }

    @Override
    public <T> T accept(CardControllerVisitor<T> cardControllerVisitor){
        return cardControllerVisitor.visit(this);
    }
}
