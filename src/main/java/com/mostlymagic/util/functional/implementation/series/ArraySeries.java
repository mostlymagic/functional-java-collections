package com.mostlymagic.util.functional.implementation.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.mostlymagic.util.functional.Series;
import com.mostlymagic.util.functional.utilities.ArrayHelper;
import com.mostlymagic.util.functional.utilities.Check;

public final class ArraySeries<T> extends AbstractSeries<T>{

    private static class ArrayIterator<T> implements Iterator<T>{

        private final T[] data;
        private int counter = 0;

        @SuppressWarnings("unchecked")
        public ArrayIterator(final Object[] store){
            data = (T[]) store;
        }

        @Override
        public boolean hasNext(){
            return data.length > counter;
        }

        @Override
        public T next(){
            return data[counter++];
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    }

    private final T[] store;

    @SuppressWarnings("unchecked")
    private ArraySeries(final Object[] data){
        store = (T[]) data;
    }

    @Override
    public int size(){
        return store.length;
    }

    @Override
    public List<T> asList(){
        return new ArrayList<T>(Arrays.asList(store));
    }

    @Override
    public Iterator<T> iterator(){
        return new ArrayIterator<T>(store);
    }

    @Override
    public Series<T> insertArray(final int position, final T... items){
        Check.gte(position, END,
            "Negative numbers lower than -1 are not allowed");
        Check.lte(position, store.length,
            "Index out of bounds: {0}, max allowed: {1}", position,
            store.length);
        return new ArraySeries<T>(ArrayHelper.mergeArrays(store, items,
            position == END ? store.length : position));
    }

    @Override
    public Series<T> deleteItems(final int... positions){
        return new ArraySeries<T>(ArrayHelper.removeArrayOffsets(store,
            positions));
    }

    @Override
    public Series<T> removeArray(final T... items){
        return new ArraySeries<T>(ArrayHelper.removeArrayItems(store, items));
    }

    public static <T> Series<T> of(final T... items){
        return new ArraySeries<T>(Arrays.copyOf(items, items.length));
    }

    @Override
    protected Series<T> clone(final T[] data){
        return new ArraySeries<T>(data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Series<T> reverse(){
        final Object[] data = Arrays.copyOf(store, store.length);
        Collections.reverse(Arrays.asList(data));
        return clone((T[]) data);
    }

    @Override
    public Series<T> subSequence(final int from, final int to){
        final int effectiveTo =
            to == END ? store.length : Math.min(to, store.length);
        Check.gte(from, 0, "From ({0}) may not be negative", from);
        Check.lte(from, effectiveTo,
            "From ({0}) must be less or equal to To ({1})", from, to);
        return clone(Arrays.copyOfRange(store, from, effectiveTo));
    }

}
