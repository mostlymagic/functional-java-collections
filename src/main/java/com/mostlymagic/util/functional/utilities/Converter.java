package com.mostlymagic.util.functional.utilities;

import java.util.ArrayList;
import java.util.Collection;

import com.mostlymagic.util.functional.Gathering;
import com.mostlymagic.util.functional.mixin.view.ArrayView;
import com.mostlymagic.util.functional.mixin.view.CollectionView;

public final class Converter{

    private Converter(){
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] collectionToArray(final Collection<E> collection){
        return (E[]) collection.toArray();
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] objectToArray(final E item){
        return (E[]) new Object[] { item };
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E> E[] gatheringToArray(final Gathering<E> gathering){
        E[] result;
        if(gathering instanceof ArrayView){
            result = (E[]) ((ArrayView) gathering).asArray();

        } else if(gathering instanceof CollectionView){
            result =
                (E[]) ((CollectionView) gathering).asCollection().toArray();
        } else{
            result = ArrayHelper.createArrayFromIterable(gathering.asIterable());
        }
        return result;
    }

    public static <T> Collection<T> iterableToCollection(final Iterable<T> input){
        if(input instanceof Collection<?>){
            return (Collection<T>) input;
        }
        final Collection<T> coll = new ArrayList<T>();
        for(final T t : input){
            coll.add(t);
        }
        return coll;
    }

}
