package com.kyloapps.utils;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.util.function.Function;

public class DeepDirtyList<T> implements DirtyProperty {
    private final ObservableList<T> currentObservableList;
    private final ObservableList<T> baseObservableList;
    private final Function<T, DirtyProperty> dirtyPropertyExtractor;

    private final ObservableMap<T, DirtyProperty> baseDirtyPropertyMap = FXCollections.observableHashMap();
    private final ObservableMap<T, DirtyProperty> currentDirtyPropertyMap = FXCollections.observableHashMap();

    private final BooleanProperty isDirty = new SimpleBooleanProperty(false);

    public DeepDirtyList(ObservableList<T> currentObservableList, Function<T, DirtyProperty> dirtyPropertyExtractor) {
        this.currentObservableList = currentObservableList;
        this.baseObservableList = FXCollections.observableArrayList(currentObservableList);
        this.dirtyPropertyExtractor = dirtyPropertyExtractor;

        configureDirtyPropertyMap();
        bindIsDirty();
    }

    private void configureDirtyPropertyMap() {
        currentObservableList.forEach(t -> currentDirtyPropertyMap.put(t, dirtyPropertyExtractor.apply(t)));
        currentObservableList.addListener((ListChangeListener<? super T>) change -> {
            while (change.next()) {
                if (change.wasAdded())
                    change.getAddedSubList().forEach(t -> currentDirtyPropertyMap.put(t, dirtyPropertyExtractor.apply(t)));
                if (change.wasRemoved())
                    change.getRemoved().forEach(currentDirtyPropertyMap::remove);
            }
        });
    }

    private void bindIsDirty() {
        isDirty.bind(new BooleanBinding() {
            {
                bind(currentObservableList, baseObservableList, currentDirtyPropertyMap);
                currentDirtyPropertyMap.values().forEach(dirtyProperty -> bind(dirtyProperty.isDirtyProperty()));
                currentDirtyPropertyMap.addListener((MapChangeListener<? super T, ? super DirtyProperty>) change -> {
                    if (change.wasAdded()) {
                        bind(change.getValueAdded().isDirtyProperty());
                    }
                    if (change.wasRemoved()) {
                        unbind(change.getValueRemoved().isDirtyProperty());
                    }
                });
            }

            @Override
            protected boolean computeValue() {
                return !baseObservableList.equals(currentObservableList)
                        || currentDirtyPropertyMap.values().stream().anyMatch(DirtyProperty::isDirty);
            }
        });
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
        baseDirtyPropertyMap.clear();
        baseDirtyPropertyMap.putAll(currentDirtyPropertyMap);

        currentDirtyPropertyMap.forEach(((t, dirtyProperty) -> dirtyProperty.rebaseline()));
    }

    @Override
    public void reset() {
        currentObservableList.setAll(baseObservableList);
        currentDirtyPropertyMap.clear();
        currentDirtyPropertyMap.putAll(baseDirtyPropertyMap);

        currentDirtyPropertyMap.forEach(((t, dirtyProperty) -> dirtyProperty.reset()));
    }
}
