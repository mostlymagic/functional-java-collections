package com.mostlymagic.util.functional.mixin.modify;

public interface Replacing<T, E> {

    T replace(E item, E replacement);

    T replaceAll(E item, E replacement);

}
