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
        manageOptionCount();
    }

    private void configureCompositeDirtyProperty() {
        model.getCompositeDirtyProperty().addAll(model.getQuestionTile().getMasterDirtyProperty(), model.getDeepDirtyList());
    }

    public void manageOptionCount() {
        ListModifications.populateList(model.getOptionTiles(), model.getOptionCount(),
                ()
                        -> new TextFieldTileAnswerOption(
                        "Answer Option", "Enter an answer choice."));
        model.optionCountProperty().addListener(
                (observable, oldValue, newValue)
                        -> ListModifications.populateList(model.getOptionTiles(),
                        newValue.intValue(),
                        ()
                                -> new TextFieldTileAnswerOption(
                                "Answer Option", "Enter an answer choice.")));
        model.getOptionTiles().addListener((ListChangeListener<? super TextFieldTileAnswerOption>) change -> {
            while (change.next())
                model.setOptionCount(change.getList().size());
        });
    }

}
