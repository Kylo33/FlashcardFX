package com.kyloapps.dirty;

import com.tobiasdiez.easybind.EasyBind;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.util.function.Function;

public class DeepDirtyList<T> implements DirtyProperty {
    private final ObservableList<T> currentObservableList;
    private final ObservableList<T> baseObservableList;

    private final ObservableList<DirtyProperty> dirtyProperties = FXCollections.observableArrayList(dirtyProperty ->
            new Observable[]{dirtyProperty.isDirtyProperty()}
    );

    private final ObservableList<DirtyProperty> dirtyPropertyBoundList; // Store here to prevent the binding being GC'd
    private final BooleanProperty isDirty = new SimpleBooleanProperty();

    public DeepDirtyList(ObservableList<T> currentObservableList, Function<T, DirtyProperty> dirtyPropertyExtractor) {
        this.currentObservableList = currentObservableList;
        this.baseObservableList = FXCollections.observableArrayList(currentObservableList);

        dirtyPropertyBoundList = EasyBind.mapBacked(currentObservableList, dirtyPropertyExtractor);
        Bindings.bindContent(dirtyProperties, dirtyPropertyBoundList);

        bindIsDirty();
    }

    private void bindIsDirty() {
        isDirty.bind(Bindings.createBooleanBinding(() -> {
            return !baseObservableList.equals(currentObservableList)
                    || dirtyProperties.stream().anyMatch(DirtyProperty::isDirty);
        }, currentObservableList, baseObservableList, dirtyProperties));
    }

    @Override
    public boolean isDirty() {
        return isDirty.get();
    }

    @Override
    public ObservableValue<Boolean> isDirtyProperty() {
        return isDirty;
    }

    @Override
    public void rebaseline() {
        baseObservableList.setAll(currentObservableList);
        dirtyProperties.forEach(DirtyProperty::rebaseline);
    }

    @Override
    public void reset() {
        currentObservableList.setAll(baseObservableList);
        dirtyProperties.forEach(DirtyProperty::reset);
    }

    public ObservableList<T> getCurrentObservableList() {
        return currentObservableList;
    }

    public ObservableList<T> getBaseObservableList() {
        return baseObservableList;
    }
}
