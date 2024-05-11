package com.kyloapps.practice;

import com.kyloapps.domain.Deck;
import javafx.beans.property.*;
import javafx.scene.image.Image;

public class PracticeMvciModel {
    private final ObjectProperty<Deck> currentDeck = new SimpleObjectProperty<>();
    private final IntegerProperty currentDeckIndex = new SimpleIntegerProperty(0);
    private final BooleanProperty nextExists = new SimpleBooleanProperty();
    private final BooleanProperty prevExists = new SimpleBooleanProperty();
    private final StringProperty question = new SimpleStringProperty();
    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

    public Deck getCurrentDeck() {
        return currentDeck.get();
    }

    public ObjectProperty<Deck> currentDeckProperty() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck.set(currentDeck);
    }

    public int getCurrentDeckIndex() {
        return currentDeckIndex.get();
    }

    public IntegerProperty currentDeckIndexProperty() {
        return currentDeckIndex;
    }

    public void setCurrentDeckIndex(int currentDeckIndex) {
        this.currentDeckIndex.set(currentDeckIndex);
    }

    public boolean isNextExists() {
        return nextExists.get();
    }

    public BooleanProperty nextExistsProperty() {
        return nextExists;
    }

    public void setNextExists(boolean nextExists) {
        this.nextExists.set(nextExists);
    }

    public boolean isPrevExists() {
        return prevExists.get();
    }

    public BooleanProperty prevExistsProperty() {
        return prevExists;
    }

    public void setPrevExists(boolean prevExists) {
        this.prevExists.set(prevExists);
    }

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }
}
