package com.mostlymagic.util.functional.mixin.util;

public interface Containing<E> {

    boolean contains(E item);

    boolean containsAll(Iterable<E> items);

}
