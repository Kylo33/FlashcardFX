package com.kyloapps.deckeditor;

import javafx.collections.ListChangeListener;
import org.nield.dirtyfx.collections.DirtyObservableList;
import org.nield.dirtyfx.tracking.CompositeDirtyProperty;
import org.nield.dirtyfx.tracking.DirtyProperty;

import java.util.function.Function;

public class DirtyUtils {

    /**
     * Bind a CompositeDirtyProperty's contents to a DirtyObservableList and all of its items converted to DirtyProperties.
     * @param composite The CompositeDirtyProperty to bind to.
     * @param dirtyObservableList The DirtyObservableList that contains items to track as well.
     * @param dirtyPropertyExtractor Takes an item of DirtyObservableList and returns a DirtyProperty
     */
    public static <T> void bindCompositeDirtyProperty(CompositeDirtyProperty composite,
                                                      DirtyObservableList<T> dirtyObservableList,
                                                      Function<T, DirtyProperty> dirtyPropertyExtractor
                                                      ) {
        composite.clear();
        composite.add(dirtyObservableList);
        dirtyObservableList.stream().map(dirtyPropertyExtractor).forEach(composite::add);

        dirtyObservableList.addListener((ListChangeListener<? super T>) change -> {
            change.next();
            change.getAddedSubList().stream().map(dirtyPropertyExtractor).forEach(composite::add);
            change.getRemoved().stream().map(dirtyPropertyExtractor).forEach(composite::remove);
        });
    }
}
