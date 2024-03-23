package com.kyloapps.deckeditor;

import com.kyloapps.dirty.DeepDirtyList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.Before;
import org.junit.Test;
import org.nield.dirtyfx.beans.DirtyIntegerProperty;
import org.nield.dirtyfx.collections.DirtyObservableList;
import org.nield.dirtyfx.tracking.DirtyProperty;

import static org.junit.jupiter.api.Assertions.*;

public class DeepDirtyListTest {
    private DirtyObservableList<IntegerProperty> integerDirtyObservableList;
    private DeepDirtyList<IntegerProperty> integerDeepDirtyList;

    public static DirtyProperty integerPropertyExtractor (IntegerProperty integerProperty) {
        DirtyIntegerProperty dirtyIntegerProperty = new DirtyIntegerProperty(integerProperty.get());
        integerProperty.bindBidirectional(dirtyIntegerProperty);
        return dirtyIntegerProperty;
    }

    @Before
    public void setup() {
        integerDirtyObservableList = new DirtyObservableList<>(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2), new SimpleIntegerProperty(3));
        integerDeepDirtyList = new DeepDirtyList<>(integerDirtyObservableList, DeepDirtyListTest::integerPropertyExtractor);
    }

    @Test
    public void testAddNewElements() {
        assertFalse(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.add(new SimpleIntegerProperty(4));
        assertTrue(integerDeepDirtyList.isDirty());
    }

    @Test
    public void testChangeExistingElements() {
        assertFalse(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.get(1).set(5);
        assertTrue(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.get(1).set(2);
        assertFalse(integerDeepDirtyList.isDirty());
    }

    @Test
    public void testReset() {
        assertFalse(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.add(new SimpleIntegerProperty(4));
        assertTrue(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.reset();
        assertFalse(integerDeepDirtyList.isDirty());
    }

    @Test
    public void testRebase() {
        assertFalse(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.add(new SimpleIntegerProperty(4));
        assertTrue(integerDeepDirtyList.isDirty());
        integerDeepDirtyList.rebaseline();
        assertFalse(integerDeepDirtyList.isDirty());

        integerDirtyObservableList.get(1).set(5);
        assertTrue(integerDeepDirtyList.isDirty());
        integerDeepDirtyList.rebaseline();
        assertFalse(integerDeepDirtyList.isDirty());
    }

    @Test
    public void addDeleteElement() {
        assertFalse(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.add(new SimpleIntegerProperty(5));
        assertTrue(integerDeepDirtyList.isDirty());
        integerDirtyObservableList.remove(integerDirtyObservableList.size() - 1);
        assertFalse(integerDeepDirtyList.isDirty());
    }
}