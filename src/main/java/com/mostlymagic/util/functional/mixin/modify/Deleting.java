package com.mostlymagic.util.functional.mixin.modify;

public interface Deleting<T, E> extends Removing<T, E>{

    T deleteItemOffset(int position);

    T deleteItemOffsets(int... positions);

}
