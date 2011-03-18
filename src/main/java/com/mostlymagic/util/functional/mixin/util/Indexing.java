package com.mostlymagic.util.functional.mixin.util;

public interface Indexing<E> extends Containing<E>{

    int indexOf(E item);

    int lastIndexOf(E item);

    int[] indexesOf(E item);
}
