package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.cardeditor.forms.multiplechoice.MultipleChoiceMvciController;

public interface ControllerVisitor<T> {
    T visit(MultipleChoiceMvciController controller);
}
