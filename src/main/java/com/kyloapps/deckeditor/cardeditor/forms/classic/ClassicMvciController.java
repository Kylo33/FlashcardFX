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
        model.getQuestionTile().getTextFields().get(0).setText(flashcard.getQuestion());
        model.getAnswerTile().getTextFields().get(0).setText(flashcard.getAnswer());
    }

    @Override
    public <T> T accept(CardControllerVisitor<T> cardControllerVisitor){
        return cardControllerVisitor.visit(this);
    }
}
