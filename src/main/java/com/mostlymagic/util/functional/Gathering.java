package com.mostlymagic.util.functional;

import com.mostlymagic.util.functional.mixin.modify.Joining;
import com.mostlymagic.util.functional.mixin.view.IterableView;

public interface Gathering<E> extends IterableView<E>, Joining<E>{

    int size();

}
