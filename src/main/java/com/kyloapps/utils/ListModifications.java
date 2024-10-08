package com.kyloapps.utils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListModifications {
    /**
     * Change the length of a list to a desired size.
     * @param list The list to change
     * @param desiredCount The desired size of the list
     * @param nodeSupplier A supplier for additional list elements
     * @param onRemove Ran after each element is deleted.
     */
    public static <T> void populateList(List<T> list, int desiredCount, Supplier<T> nodeSupplier, Consumer<T> onRemove) {
        if (desiredCount < 0) throw new IllegalArgumentException("desiredCount must be greater than zero.");
        int currentCount = list.size();
        if (desiredCount > currentCount) {
            for (int i = 0, c = desiredCount - currentCount; i < c; i++)
                list.add(nodeSupplier.get());
        } else if (desiredCount < currentCount) {
            for (int i = 0, c = currentCount - desiredCount; i < c; i++) {
                T removedElement = list.get(list.size() - 1);
                list.remove(list.size() - 1);
                onRemove.accept(removedElement);
            }
        }
    }

    /**
     * Change the length of a list to a desired size.
     * @param list The list to change
     * @param desiredCount The desired size of the list
     * @param nodeSupplier A supplier for additional list elements
     */
    public static <T> void populateList(List<T> list, int desiredCount, Supplier<T> nodeSupplier) {
        populateList(list, desiredCount, nodeSupplier, (a) -> {});
    }
}
