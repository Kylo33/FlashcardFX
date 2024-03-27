package com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.utils.ListModifications;
import com.kyloapps.domain.AnswerOption;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.util.Pair;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;
import org.nield.dirtyfx.beans.DirtyStringProperty;

public class MultipleChoiceMvciInteractor {
    private final MultipleChoiceMvciModel model;

    public MultipleChoiceMvciInteractor(MultipleChoiceMvciModel model) {
        this.model = model;
        configureCompositeDirtyProperty();
    }

    private void configureCompositeDirtyProperty() {
        model.getCompositeDirtyProperty().addAll(model.getQuestionTile().getMasterDirtyProperty(), model.getDeepDirtyList());
    }

    public void updateAnswerCount(int desiredAnswerCount) {
        ListModifications.populateList(model.getOptionTiles(),
                desiredAnswerCount,
                () -> new TextFieldTileAnswerOption("Answer Option", "Enter an answer choice."));
    }

}
