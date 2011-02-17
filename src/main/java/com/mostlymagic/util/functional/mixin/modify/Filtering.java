package com.mostlymagic.util.functional.mixin.modify;

import com.mostlymagic.util.functional.helper.Predicate;

public interface Filtering<T, E> {

    T filterWith(Predicate<E> predicate);

}
