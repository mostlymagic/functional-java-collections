package com.mostlymagic.util.functional.mixin.modify;

public interface Sequencing<T, E> {

    T subSequence(int from, int to);

    E head();

    T tail();

}
