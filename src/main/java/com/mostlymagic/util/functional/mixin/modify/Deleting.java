package com.mostlymagic.util.functional.mixin.modify;

public interface Deleting<T, E> extends Removing<T, E>{

    T deleteItem(int position);

    T deleteItems(int... positions);

}
