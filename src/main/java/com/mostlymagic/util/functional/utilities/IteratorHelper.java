package com.mostlymagic.util.functional.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.mostlymagic.util.functional.Gathering;
import com.mostlymagic.util.functional.Series;

public final class IteratorHelper{

    private IteratorHelper(){
    }

    private static class ChainingIterator<E> implements Iterator<E>{

        private final Iterator<E>[] iterators;
        private int offset = 0;

        public ChainingIterator(final Iterator<E>[] iterators){
            this.iterators = iterators;
        }

        @Override
        public boolean hasNext(){
            while(offset < iterators.length){
                if(iterators[offset].hasNext()){
                    return true;
                } else{
                    offset++;
                }
            }
            return false;
        }

        @Override
        public E next(){
            return iterators[offset].next();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    }

    private enum EmptyIterator implements Iterator<Object>{

        INSTANCE;

        @Override
        public boolean hasNext(){
            return false;
        }

        @Override
        public Object next(){
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    private static class DescendingArrayIterator<T> implements Iterator<T>{

        private final T[] array;
        private int position;

        public DescendingArrayIterator(final T[] array){
            this.array = array;
            this.position = array.length;
        }

        @Override
        public boolean hasNext(){
            return position > 0;
        }

        @Override
        public T next(){
            return array[--position];
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    }

    public static <T> Iterator<T> arrayIterator(final T[] array){
        return Arrays.asList(array).iterator();
    }

    public static <T> Iterator<T> chain(final Iterator<T>... iterators){
        return new ChainingIterator<T>(iterators);
    }

    public static <T> Iterator<T> getReverseIterator(final T[] array){
        return new DescendingArrayIterator<T>(array);
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> emptyIterator(){
        return (Iterator<T>) EmptyIterator.INSTANCE;
    }

    public static <T> Iterator<T> getReverseIterator(final Gathering<T> items){
        Iterator<T> reverseIterator;
        if(items instanceof Series<?>){
            reverseIterator = getReverseIterator((Series<T>) items);
        } else{
            final Iterable<T> iterable = items.asIterable();
            reverseIterator = getReverseIterator(iterable);
        }
        return reverseIterator;
    }

    public static <T> Iterator<T> getReverseIterator(final Iterable<T> iterable){
        if(iterable instanceof List<?>){
            final List<T> copy = new ArrayList<T>((List<T>) iterable);
            Collections.reverse(copy);
            return copy.iterator();
        }
        return getReverseIterator(ArrayHelper.createArrayFromIterable(iterable));
    }

    public static <T> Iterator<T> getReverseIterator(final Series<T> other){
        return other.reverse().asIterable().iterator();
    }

}
