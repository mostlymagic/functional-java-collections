package com.mostlymagic.util.functional.mixin.modify;

import java.util.Collection;

import com.mostlymagic.util.functional.Gathering;

public interface Removing<T, E> {

    T remove(E item);

    T removeAll(E... items);

    T removeCollection(Collection<E> items);

    T removeGathering(Gathering<E> items);

}
