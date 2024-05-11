package com.kyloapps.deckeditor.cardeditor.forms.table;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTileAnswerOption;
import com.kyloapps.domain.AnswerOption;
import com.kyloapps.utils.ListModifications;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.function.Supplier;

public class TableMvciInteractor {
    private static final Supplier<StringProperty> STRING_PROPERTY_SUPPLIER = () -> new SimpleStringProperty("");
    private final TableMvciModel model;

    public TableMvciInteractor(TableMvciModel model) {
        this.model = model;
        configureCompositeDirtyProperty();
        manageDimensions();
    }

    private void manageDimensions() {
        // Columns
        ListModifications.populateList(model.getHeaders(), model.getColumnCount(), STRING_PROPERTY_SUPPLIER);
        model.columnCountProperty().addListener((observable, oldColumnCount, newColumnCount)
                -> {
            ListModifications.populateList(model.getHeaders(), newColumnCount.intValue(), STRING_PROPERTY_SUPPLIER);
            model.getOptions().forEach(listOption -> ListModifications.populateList(listOption.getContent(), newColumnCount.intValue(), STRING_PROPERTY_SUPPLIER));
        });

        model.getHeaders().addListener(
                (ListChangeListener<? super StringProperty>) change -> model.setColumnCount(change.getList().size()));

        // Rows
        ListModifications.populateList(model.getOptions(), model.getRowCount(), this::getObservableListOption);
        model.rowCountProperty().addListener((observable, oldRowCount, newRowCount) ->
                ListModifications.populateList(model.getOptions(), newRowCount.intValue(), this::getObservableListOption));
        model.getOptions().addListener((ListChangeListener<? super AnswerOption<ObservableList<StringProperty>>>) change -> {
            while (change.next())
                model.setRowCount(change.getList().size());
        });
    }

    private AnswerOption<ObservableList<StringProperty>> getObservableListOption() {
        ObservableList<StringProperty> stringPropertyObservableList = FXCollections.observableArrayList();
        ListModifications.populateList(stringPropertyObservableList, model.getColumnCount(), STRING_PROPERTY_SUPPLIER);
        return new AnswerOption<>(false, stringPropertyObservableList);
    }

    private void configureCompositeDirtyProperty() {
        model.getCompositeDirtyProperty().addAll(
                model.questionProperty(),
                model.imageUrlProperty(),
                model.getHeadersDeepDirtyList(),
                model.getOptionsDeepDirtyList());
    }
}
