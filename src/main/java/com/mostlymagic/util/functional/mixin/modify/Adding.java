package com.mostlymagic.util.functional.mixin.modify;

import java.util.Collection;

import com.mostlymagic.util.functional.Gathering;

/**
 * Methods for adding elements.
 * <p>
 * These methods are loosely equivalent to
 * {@link java.util.Collection#add(Object) Collection.add(E)} and
 * {@link java.util.Collection#addAll(Collection)
 * Collection.addAll(Collection<E>)}, the main difference being that these
 * methods return a new instance if changes are made.
 * 
 * @author Sean Patrick Floyd
 * @param <T>
 *            Container Type
 * @param <E>
 *            Element Type
 */
public interface Adding<T, E> {

    /**
     * Add a new Element and return the resulting container.
     * <p>
     * This method will always return a new Object if the contents have changed
     * (if the element is not yet present or duplicates are allowed). However,
     * for Containers that don't allow duplicates this method may return
     * <code>this</code> if the element is already present.
     * 
     * @return the resulting container (usually a new Object)
     */
    T add(E item);

    /**
     * Add an array of new Elements and return the resulting container.
     * <p>
     * This method will always return a new Object if the contents have changed
     * (if at least one of the elements is not yet present or duplicates are
     * allowed). However, for Containers that don't allow duplicates this method
     * may return <code>this</code> if all elements are already present.
     * 
     * @return the resulting container (usually a new Object)
     */
    T addArray(E... items);

    /**
     * Add a {@link Collection} of new Elements and return the resulting
     * container.
     * <p>
     * This method will always return a new Object if the contents have changed
     * (if at least one of the elements is not yet present or duplicates are
     * allowed). However, for Containers that don't allow duplicates this method
     * may return <code>this</code> if all elements are already present.
     * 
     * @return the resulting container (usually a new Object)
     */
    T addCollection(Collection<E> items);

    /**
     * Add a {@link Gathering} of new Elements and return the resulting
     * container.
     * <p>
     * This method will always return a new Object if the contents have changed
     * (if at least one of the elements is not yet present or duplicates are
     * allowed). However, for Containers that don't allow duplicates this method
     * may return <code>this</code> if all elements are already present.
     * 
     * @return the resulting container (usually a new Object)
     */
    T addGathering(Gathering<E> items);

}
