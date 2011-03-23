package com.mostlymagic.util.functional.implementation.gathering;

import java.util.Arrays;
import java.util.Collections;

import com.mostlymagic.util.functional.Gathering;

/**
 * @author Sean Patrick Floyd
 */
public final class Gatherings{

    private static class ArrayGathering<T> extends AbstractGathering<T>{

        private final T[] items;

        public ArrayGathering(final T[] items){
            this.items = items;
        }

        @Override
        public Iterable<T> asIterable(){
            return Collections.unmodifiableCollection(Arrays.asList(items));
        }

        @Override
        public int size(){
            return items.length;
        }

    }

    private enum EmptyGathering implements Gathering<Object>{
        INSTANCE;

        @Override
        public String join(){
            return "";
        }

        @Override
        public String join(final String separator){
            return join();
        }

        @Override
        public Iterable<Object> asIterable(){
            return Collections.emptySet();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size(){
            return 0;
        }
    }

    public static <T> Gathering<T> asGathering(final T... items){
        return new ArrayGathering<T>(items);

    }

    @SuppressWarnings("unchecked")
    public static <T> Gathering<T> emptyGathering(){
        return (Gathering<T>) EmptyGathering.INSTANCE;
    }

}
