package com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.domain.AnswerOption;
import com.kyloapps.utils.DeepDirtyList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class MultipleChoiceMvciModel {
    static final int MINIMUM_MCQ_COUNT = 1;
    static final int MAXIMUM_MCQ_COUNT = 10;

    private final IntegerProperty optionCount = new SimpleIntegerProperty();

    private final TextFieldTile questionTile = new TextFieldTile("Question", "Enter the flashcard's question.");
    private final ObservableList<TextFieldTileAnswerOption> optionTiles = FXCollections.observableArrayList();
    private final DeepDirtyList<TextFieldTileAnswerOption> deepDirtyList = new DeepDirtyList<>(optionTiles, TextFieldTile::getMasterDirtyProperty);

    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    public TextFieldTile getQuestionTile() {
        return questionTile;
    }

    public ObservableList<TextFieldTileAnswerOption> getOptionTiles() {
        return optionTiles;
    }

    public DeepDirtyList<TextFieldTileAnswerOption> getDeepDirtyList() {
        return deepDirtyList;
    }

    public CompositeDirtyProperty compositeDirtyPropertyProperty() {
        return compositeDirtyProperty;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }

    public int getOptionCount() {
        return optionCount.get();
    }

    public IntegerProperty optionCountProperty() {
        return optionCount;
    }

    public void setOptionCount(int optionCount) {
        this.optionCount.set(optionCount);
    }
}
