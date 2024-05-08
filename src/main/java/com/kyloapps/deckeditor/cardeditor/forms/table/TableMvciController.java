package com.kyloapps.deckeditor.cardeditor.forms.table;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.deckeditor.cardeditor.forms.CardControllerVisitor;
import com.kyloapps.domain.AnswerOption;
import com.kyloapps.domain.TableFlashcard;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.util.List;
import java.util.stream.Collectors;

public class TableMvciController implements CardController<TableFlashcard> {

    private final TableMvciModel model;
    private final TableMvciInteractor interactor;
    private final TableMvciViewBuilder viewBuilder;

    public TableMvciController(){
        this.model = new TableMvciModel();
        this.interactor = new TableMvciInteractor(model);
        this.viewBuilder = new TableMvciViewBuilder(model);
    }

    @Override
    public Region getView() {
        return viewBuilder.build();
    }

    @Override
    public DirtyProperty getDirtyProperty() {
        return model.getCompositeDirtyProperty();
    }

    @Override
    public <T> T accept(CardControllerVisitor<T> cardControllerVisitor) {
        return cardControllerVisitor.visit(this);
    }

    @Override
    public void loadCard(TableFlashcard flashcard) {
        model.setQuestion(flashcard.getQuestion());
        model.setImageUrl(flashcard.getImageUrl());

        model.getHeaders().setAll(copyStringPropertyList(flashcard.getHeaders()));
        model.getOptions().setAll(copyOptions(flashcard.getOptions()));
    }

    private static List<StringProperty> copyStringPropertyList(List<StringProperty> listToCopy) {
        return listToCopy.stream()
                .map(stringProperty -> new SimpleStringProperty(stringProperty.get()))
                .collect(Collectors.toList());
    }

    private static List<AnswerOption<ObservableList<StringProperty>>> copyOptions(List<AnswerOption<ObservableList<StringProperty>>> copyFrom) {
        return copyFrom.stream().map(option ->
                new AnswerOption<>(option.isCorrect(), FXCollections.observableArrayList(copyStringPropertyList(option.getContent())))
        ).collect(Collectors.toList());
    }

    @Override
    public TableFlashcard toFlashcard() {
        TableFlashcard result = new TableFlashcard();
        result.setQuestion(model.getQuestion());
        result.setImageUrl(model.getImageUrl());
        result.getHeaders().setAll(copyStringPropertyList(model.getHeaders()));
        result.getOptions().setAll(copyOptions(model.getOptions()));
        return result;
    }
}
