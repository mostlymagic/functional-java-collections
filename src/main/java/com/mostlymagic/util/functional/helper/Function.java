package com.mostlymagic.util.functional.helper;

/**
 * A generic converter interface.
 * 
 * @author Sean Patrick Floyd
 */
public interface Function<I, O> {

    /**
     * Convert the supplied object.
     */
    O apply(I input);

}
