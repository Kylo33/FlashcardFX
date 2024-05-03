package com.kyloapps.deckeditor.cardeditor.forms.table;

import com.kyloapps.domain.AnswerOption;
import com.kyloapps.utils.DeepDirtyList;
import java.util.function.Function;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class TableMvciModel {
    static final int MIN_ROW_COUNT = 1;
    static final int MAX_ROW_COUNT = 5;
    static final int DEFAULT_ROW_COUNT = 2;
    static final int MIN_COLUMN_COUNT = 1;
    static final int MAX_COLUMN_COUNT = 5;
    static final int DEFAULT_COLUMN_COUNT = 2;

    private static final Function<StringProperty, DirtyProperty>
            STRING_PROPERTY_DIRTY_PROPERTY_EXTRACTOR = stringProperty -> {
        DirtyStringProperty result = new DirtyStringProperty(stringProperty.get());
        stringProperty.bindBidirectional(result);
        return result;
    };

    private final IntegerProperty rowCount = new SimpleIntegerProperty(DEFAULT_ROW_COUNT);
    private final IntegerProperty columnCount = new SimpleIntegerProperty(DEFAULT_COLUMN_COUNT);

    private final DirtyStringProperty question = new DirtyStringProperty("");
    private final DirtyStringProperty imageUrl = new DirtyStringProperty("");
    private final ObservableList<StringProperty> headers = FXCollections.observableArrayList();
    private final ObservableList<AnswerOption<ObservableList<StringProperty>>> options =
            FXCollections.observableArrayList();

    private final DeepDirtyList<StringProperty> headersDeepDirtyList =
            new DeepDirtyList<>(headers, STRING_PROPERTY_DIRTY_PROPERTY_EXTRACTOR);
    private final DeepDirtyList<AnswerOption<ObservableList<StringProperty>>> optionsDeepDirtyList =
            new DeepDirtyList<>(options, observableListAnswerOption -> {
                CompositeDirtyProperty result = new CompositeDirtyProperty();

                DeepDirtyList<StringProperty> content = new DeepDirtyList<>(
                        observableListAnswerOption.getContent(), STRING_PROPERTY_DIRTY_PROPERTY_EXTRACTOR);

                DirtyBooleanProperty correct =
                        new DirtyBooleanProperty(observableListAnswerOption.isCorrect());
                observableListAnswerOption.correctProperty().bindBidirectional(correct);

                result.addAll(content, correct);
                return result;
            });

    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    public int getRowCount() {
        return rowCount.get();
    }

    public IntegerProperty rowCountProperty() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount.set(rowCount);
    }

    public String getQuestion() {
        return question.get();
    }

    public DirtyStringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getImageUrl() {
        return imageUrl.get();
    }

    public DirtyStringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    public ObservableList<StringProperty> getHeaders() {
        return headers;
    }

    public ObservableList<AnswerOption<ObservableList<StringProperty>>> getOptions() {
        return options;
    }

    public DeepDirtyList<StringProperty> getHeadersDeepDirtyList() {
        return headersDeepDirtyList;
    }

    public DeepDirtyList<AnswerOption<ObservableList<StringProperty>>> getOptionsDeepDirtyList() {
        return optionsDeepDirtyList;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
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
