package com.kyloapps.deckeditor.cardeditor.forms.table;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.utils.ListModifications;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class TableMvciInteractor {
    private final TableMvciModel model;

    public TableMvciInteractor(TableMvciModel model) {
        this.model = model;
        configureCompositeDirtyProperty();
        manageDimensions();
    }

    private void manageDimensions() {
        // Columns
        model.getHeaders().textFieldCountProperty().bindBidirectional(model.columnCountProperty());
        model.getOptionTiles().forEach(tile -> tile.textFieldCountProperty().bindBidirectional(model.columnCountProperty()));
        model.getOptionTiles().addListener((ListChangeListener<? super TextFieldTileAnswerOption>) change -> {
            while (change.next())
                change.getAddedSubList().forEach(tile -> tile.textFieldCountProperty().bindBidirectional(model.columnCountProperty()));
        });

        // Rows
        ListModifications.populateList(model.getOptionTiles(), model.getRowCount(),
                ()
                        -> new TextFieldTileAnswerOption(
                        "Answer Option", "Enter an answer choice."));
        model.rowCountProperty().addListener(
                (observable, oldValue, newValue)
                        -> ListModifications.populateList(model.getOptionTiles(),
                        newValue.intValue(),
                        ()
                                -> new TextFieldTileAnswerOption(
                                "Answer Option", "Enter an answer choice.")));
        model.getOptionTiles().addListener((ListChangeListener<? super TextFieldTileAnswerOption>) change -> {
            while (change.next())
                model.setRowCount(change.getList().size());
        });
    }

    private void configureCompositeDirtyProperty() {
        model.getCompositeDirtyProperty().addAll(
                model.getQuestionTile().getMasterDirtyProperty(),
                model.getHeaders().getMasterDirtyProperty(), model.getDeepDirtyList());
    }
}
