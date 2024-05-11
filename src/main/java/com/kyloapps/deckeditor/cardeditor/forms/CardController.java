package com.kyloapps.deckeditor.cardeditor.forms;

import com.kyloapps.domain.Flashcard;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.DirtyProperty;

public interface CardController<M extends Flashcard> {
    Region getView();
    DirtyProperty getDirtyProperty();
    <T> T accept(CardControllerVisitor<T> cardControllerVisitor);
    void loadCard(M flashcard);

    M toFlashcard();
}
