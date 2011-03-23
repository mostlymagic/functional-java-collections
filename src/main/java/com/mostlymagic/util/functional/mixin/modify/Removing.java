package com.mostlymagic.util.functional.mixin.modify;

import java.util.Collection;

import com.mostlymagic.util.functional.Gathering;

/**
 * Methods for removing elements. All of these methods are loosely equivalent to
 * {@link Collection#remove(Object) Collection.remove(E)} and
 * {@link Collection#removeAll(Collection) Collection.removeAll(Collection<E>)}.
 * 
 * @author Sean Patrick Floyd
 * @param <T>
 * @param <E>
 */
public interface Removing<T, E> {

    T remove(E item);

    T removeAll(E... items);

    T removeCollection(Collection<E> items);

    T removeGathering(Gathering<E> items);

}
