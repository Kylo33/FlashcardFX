package com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;

import com.kyloapps.domain.AnswerOption;
import com.kyloapps.utils.DeepDirtyList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.beans.DirtyBooleanProperty;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;

public class MultipleChoiceMvciModel {
    static final int MINIMUM_MCQ_COUNT = 1;
    static final int MAXIMUM_MCQ_COUNT = 10;

    private final IntegerProperty optionCount = new SimpleIntegerProperty();

    private final DirtyStringProperty question = new DirtyStringProperty("");
    private final DirtyStringProperty imageUrl = new DirtyStringProperty("");
    private final ObservableList<AnswerOption<StringProperty>> options = FXCollections.observableArrayList();
    private final DeepDirtyList<AnswerOption<StringProperty>> deepDirtyList = new DeepDirtyList<>(options, answerOption -> {
        CompositeDirtyProperty result = new CompositeDirtyProperty();

        DirtyBooleanProperty correct = new DirtyBooleanProperty(answerOption.isCorrect());
        answerOption.correctProperty().bindBidirectional(correct);

        DirtyStringProperty content = new DirtyStringProperty(answerOption.getContent().get());
        answerOption.getContent().bindBidirectional(content);

        result.addAll(correct, content);
        return result;
    });

    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    public int getOptionCount() {
        return optionCount.get();
    }

    public IntegerProperty optionCountProperty() {
        return optionCount;
    }

    public void setOptionCount(int optionCount) {
        this.optionCount.set(optionCount);
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

    public ObservableList<AnswerOption<StringProperty>> getOptions() {
        return options;
    }

    public DeepDirtyList<AnswerOption<StringProperty>> getDeepDirtyList() {
        return deepDirtyList;
    }

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }
}
