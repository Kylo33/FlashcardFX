package com.kyloapps.deckeditor.cardeditor.forms;

import com.kyloapps.deckeditor.cardeditor.forms.classic.ClassicMvciController;
import com.kyloapps.deckeditor.cardeditor.forms.multiplechoice.MultipleChoiceMvciController;
import com.kyloapps.deckeditor.cardeditor.forms.table.TableMvciController;

public interface CardControllerVisitor<T> {
    T visit(MultipleChoiceMvciController controller);
    T visit(ClassicMvciController controller);

    T visit(TableMvciController controller);
}
