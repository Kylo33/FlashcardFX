package com.kyloapps.deckeditor.cardeditor.forms;

import com.kyloapps.deckeditor.cardeditor.ControllerVisitor;
import com.kyloapps.domain.Flashcard;
import com.kyloapps.domain.FlashcardVisitor;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.DirtyProperty;

public interface CardController<M extends Flashcard> {
    Region getView();
    DirtyProperty getDirtyProperty();
    <T> T accept(ControllerVisitor<T> controllerVisitor);
    void loadCard(M flashcard);
}
