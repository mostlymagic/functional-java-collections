package com.mostlymagic.util.functional.mixin.modify;

import java.util.Collection;

import com.mostlymagic.util.functional.Gathering;

public interface Inserting<T, E> extends Adding<T, E>{

    T insert(int position, E item);

    T insertArray(int position, E... items);

    T insertCollection(int position, Collection<E> items);

    T insertGathering(int position, Gathering<E> items);

    T append(E item);

    T appendArray(E... items);

    T appendCollection(Collection<E> items);

    T appendGathering(Gathering<E> items);

    T prepend(E item);

    T prependArray(E... items);

    T prependCollection(Collection<E> items);

    T prependGathering(Gathering<E> items);

}
