package com.mostlymagic.util.functional;

import com.mostlymagic.util.functional.helper.Function;
import com.mostlymagic.util.functional.mixin.modify.Deleting;
import com.mostlymagic.util.functional.mixin.modify.Filtering;
import com.mostlymagic.util.functional.mixin.modify.Inserting;
import com.mostlymagic.util.functional.mixin.modify.Reversing;
import com.mostlymagic.util.functional.mixin.modify.Sequencing;
import com.mostlymagic.util.functional.mixin.view.ArrayView;
import com.mostlymagic.util.functional.mixin.view.ListView;

public interface Series<E> extends Gathering<E>, Inserting<Series<E>, E>,
    Deleting<Series<E>, E>, Filtering<Series<E>, E>, ListView<E>,
    Reversing<Series<E>>, Sequencing<Series<E>>, ArrayView<E>

{

    <O> Series<O> transform(Function<E, O> function);
}
