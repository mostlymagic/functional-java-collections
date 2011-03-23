package com.mostlymagic.util.functional;

import com.mostlymagic.util.functional.mixin.modify.Joining;
import com.mostlymagic.util.functional.mixin.view.IterableView;

/**
 * <code>Gathering</code> is loosely equivalent to {@link java.util.Collection
 * Collection}, but all <code>Gathering</code> implementations must be immutable
 * (methods for adding or removing ).
 * <p>
 * Gatherings may or may not implement {@link Iterable}, but they must provide
 * an {@link Iterable} view through the {@link #asIterable()} method.
 */
public interface Gathering<E> extends IterableView<E>, Joining<E>{

    /**
     * <b>Note:</b> Given the immutable nature of <code>Gathering</code>, all
     * iterators provided by asIterable() will not support
     * {@link java.util.Iterator#remove() remove()} (In cases where
     * <code>asIterable()</code> returns a new {@link Iterable}, changes will
     * not be written back to the <code>Gathering</code>, otherwise
     * {@link java.lang.UnsupportedOperationException
     * UnsupportedOperationException} will be thrown).
     */
    @Override
    Iterable<E> asIterable();

    /**
     * The number of elements.
     */
    int size();

}
