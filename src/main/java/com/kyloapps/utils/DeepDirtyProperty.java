package com.kyloapps.utils;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.util.function.Function;

// Should report that it is dirty when:
// - the current object has changed to another object
// - or the current object is dirty
public class DeepDirtyProperty<T> implements DirtyProperty {
    private final ObjectProperty<T> currentProperty;
    private final ObjectProperty<T> baseProperty;
    private final Function<T, DirtyProperty> dirtyPropertyExtractor;

    private final ObservableList<DirtyProperty> dirtyProperty = FXCollections.observableArrayList(dp -> new Observable[]{dp.isDirtyProperty()});

    private final BooleanProperty isDirty = new SimpleBooleanProperty(false);

    public DeepDirtyProperty(ObjectProperty<T> currentProperty, Function<T, DirtyProperty> dirtyPropertyExtractor) {
        this.currentProperty = currentProperty;
        this.baseProperty = new SimpleObjectProperty<>(currentProperty.get());
        this.dirtyPropertyExtractor = dirtyPropertyExtractor;

        bindIsDirty();
    }

    private void bindIsDirty() {
        // Keep dirtyProperty as a single element list (need the extractor functionality) where the element is
        // the currentProperty mapped to a DirtyProperty.
        if (currentProperty.get() != null)
            this.dirtyProperty.add(dirtyPropertyExtractor.apply(currentProperty.get()));
        else this.dirtyProperty.add(null);

        this.currentProperty.addListener((observable, oldT, newT) -> {
            this.dirtyProperty.set(0, dirtyPropertyExtractor.apply(newT));
        });

        isDirty.bind(Bindings.createBooleanBinding(
                ()
                        -> {
                    if (dirtyProperty.get(0) == null) return false;
                    if (baseProperty.get() == null || currentProperty.get() == null)
                        return baseProperty.get() == currentProperty.get();
                    return !baseProperty.get().equals(currentProperty.get()) || dirtyProperty.get(0).isDirty();
                },
                currentProperty, baseProperty, dirtyProperty));
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
        baseProperty.set(currentProperty.get());
        if (dirtyProperty.get(0) != null)
            dirtyProperty.get(0).rebaseline();
    }

    @Override
    public void reset() {
        currentProperty.set(baseProperty.get());
        if (dirtyProperty.get(0) != null)
            dirtyProperty.get(0).reset();
    }
}
