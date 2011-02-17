package com.mostlymagic.util.functional.mixin.modify;

import java.util.Collection;

import com.mostlymagic.util.functional.Gathering;

public interface Adding<T, E> {

    T add(E item);

    T addArray(E... items);

    T addCollection(Collection<E> items);

    T addGathering(Gathering<E> items);

}
