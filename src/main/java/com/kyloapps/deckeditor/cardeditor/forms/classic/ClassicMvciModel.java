package com.kyloapps.deckeditor.cardeditor.forms.classic;

import com.kyloapps.deckeditor.cardeditor.forms.TextFieldTile;
import org.nield.dirtyfx.beans.DirtyStringProperty;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

public class ClassicMvciModel {
    private final DirtyStringProperty question = new DirtyStringProperty("");
    private final DirtyStringProperty answer = new DirtyStringProperty("");

    private final DirtyStringProperty imageUrl = new DirtyStringProperty("");

    private final CompositeDirtyProperty compositeDirtyProperty = new CompositeDirtyProperty();

    public String getQuestion() {
        return question.get();
    }

    public DirtyStringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getAnswer() {
        return answer.get();
    }

    public DirtyStringProperty answerProperty() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer.set(answer);
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

    public CompositeDirtyProperty getCompositeDirtyProperty() {
        return compositeDirtyProperty;
    }
}
