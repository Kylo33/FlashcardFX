package com.kyloapps.deckeditor.cardeditor.forms.table;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.utils.DeepDirtyList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.beans.DirtyIntegerProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class TableMvciModel {
    static final int MIN_ROW_COUNT = 1;
    static final int MAX_ROW_COUNT = 5;
    static final int MIN_COLUMN_COUNT = 1;
    static final int MAX_COLUMN_COUNT = 5;

    private final TextFieldTile questionTile = new TextFieldTile("Question", "Enter the flashcard's question.");
    private final TextFieldTile headers = new TextFieldTile("Table Headers", "Enter the first row of the table (not an answer option).");
    private final ObservableList<TextFieldTileAnswerOption> optionTiles = FXCollections.observableArrayList();
    private final DeepDirtyList<TextFieldTileAnswerOption> deepDirtyList = new DeepDirtyList<>(optionTiles, TextFieldTile::getMasterDirtyProperty);

    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    private final IntegerProperty rowCount = new SimpleIntegerProperty();
    private final IntegerProperty columnCount = new SimpleIntegerProperty();

    public TextFieldTile getQuestionTile() {
        return questionTile;
    }

    public TextFieldTile getHeaders() {
        return headers;
    }

    public ObservableList<TextFieldTileAnswerOption> getOptionTiles() {
        return optionTiles;
    }

    public DeepDirtyList<TextFieldTileAnswerOption> getDeepDirtyList() {
        return deepDirtyList;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }

    public CompositeDirtyProperty compositeDirtyPropertyProperty() {
        return compositeDirtyProperty;
    }

    public int getRowCount() {
        return rowCount.get();
    }

    public IntegerProperty rowCountProperty() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount.set(rowCount);
    }

    public int getColumnCount() {
        return columnCount.get();
    }

    public IntegerProperty columnCountProperty() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount.set(columnCount);
    }
}
