package com.mostlymagic.util.functional;

import java.util.List;

import com.mostlymagic.util.functional.helper.Function;
import com.mostlymagic.util.functional.mixin.modify.Deleting;
import com.mostlymagic.util.functional.mixin.modify.Filtering;
import com.mostlymagic.util.functional.mixin.modify.Inserting;
import com.mostlymagic.util.functional.mixin.modify.Replacing;
import com.mostlymagic.util.functional.mixin.modify.Reversing;
import com.mostlymagic.util.functional.mixin.modify.Sequencing;
import com.mostlymagic.util.functional.mixin.util.Indexing;
import com.mostlymagic.util.functional.mixin.view.ArrayView;
import com.mostlymagic.util.functional.mixin.view.ListView;

/**
 * Loosely equivalent to <code>{@link List}</code>, but with a different
 * contract: every method that adds or removes items must return a new instance
 * of the <code>Series</code>.
 * 
 * @author Sean Patrick Floyd
 */
public interface Series<E> extends Gathering<E>, Inserting<Series<E>, E>,
    Deleting<Series<E>, E>, Filtering<Series<E>, E>, ListView<E>,
    Reversing<Series<E>>, Sequencing<Series<E>, E>, ArrayView<E>, Indexing<E>,
    Replacing<Series<E>, E>

{

    /**
     * Returns a new Series by transforming all Elements of this Series using
     * the supplied converter {@link Function}.
     * 
     * @param function
     *            a converter function, may not be null
     * @return the new Series
     */
    <O> Series<O> transform(Function<E, O> function);
}
