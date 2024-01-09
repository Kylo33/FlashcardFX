package com.kyloapps.View;

import com.kyloapps.DisplayableFlashcard;
import javafx.scene.Node;

public interface QuestionCreator {
    public Node get();
    public DisplayableFlashcard build();
    public boolean isComplete();
    public void fillFromFlashcard(DisplayableFlashcard flashcard);
}
