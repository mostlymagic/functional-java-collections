package com.mostlymagic.util.functional.helper;

/**
 * A Predicate is a Filter. The contract: if {@link #apply(Object)} returns
 * false, the Object is rejected.
 * 
 * @author Sean Patrick Floyd
 */
public interface Predicate<T> {

    /**
     * Returns true if this predicate accepts the supplied Object.
     */
    boolean apply(T input);

}
