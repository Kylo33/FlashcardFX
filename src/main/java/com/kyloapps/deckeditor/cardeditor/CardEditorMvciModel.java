package com.kyloapps.deckeditor.cardeditor;

import com.kyloapps.deckeditor.cardeditor.forms.CardController;
import com.kyloapps.utils.DeepDirtyProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CardEditorMvciModel {
    private final ObjectProperty<CardController<?>> controller = new SimpleObjectProperty<>();
    private final DeepDirtyProperty<CardController<?>> controllerDeepDirtyProperty = new DeepDirtyProperty<>(controller, CardController::getDirtyProperty);

    public CardController<?> getController() {
        return controller.getValue();
    }

    public ObjectProperty<CardController<?>> controllerProperty() {
        return controller;
    }

    public void setController(CardController<?> controller) {
        this.controller.setValue(controller);
    }

    public DeepDirtyProperty<CardController<?>> getControllerDeepDirtyProperty() {
        return controllerDeepDirtyProperty;
    }
}
