package com.mostlymagic.util.functional.mixin.view;

public interface ArrayView<E> {

    E[] asArray();

    E[] asArray(E[] placeHolder);

}
