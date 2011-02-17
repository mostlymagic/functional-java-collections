package com.mostlymagic.util.functional.implementation.series;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import com.mostlymagic.util.functional.Gathering;
import com.mostlymagic.util.functional.Series;
import com.mostlymagic.util.functional.helper.Function;
import com.mostlymagic.util.functional.helper.Predicate;
import com.mostlymagic.util.functional.mixin.view.IterableView;
import com.mostlymagic.util.functional.utilities.Converter;

public abstract class AbstractSeries<E> implements Series<E>, Iterable<E>{

    protected static final int END = -1;

    @Override
    public Series<E> add(final E item){
        return append(item);
    }

    @Override
    public Series<E> addArray(final E... items){
        return appendArray(items);
    }

    @Override
    public Series<E> addCollection(final Collection<E> items){
        return appendCollection(items);
    }

    @Override
    public Series<E> addGathering(final Gathering<E> items){
        return appendGathering(items);
    }

    @Override
    public Series<E> append(final E item){
        return insert(END, item);
    }

    @Override
    public Series<E> appendArray(final E... items){
        return insertArray(END, items);
    }

    @Override
    public Series<E> appendCollection(final Collection<E> items){
        return insertCollection(END, items);
    }

    @Override
    public Series<E> appendGathering(final Gathering<E> items){
        return insertGathering(END, items);
    }

    @Override
    public Iterable<E> asIterable(){
        return this;
    }

    protected abstract Series<E> clone(E[] data);

    @Override
    public Series<E> deleteItem(final int position){
        return deleteItems(new int[] { position });
    }

    @Override
    public abstract Series<E> deleteItems(final int... positions);

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof IterableView<?>){
            final Iterator<E> mine = asIterable().iterator();
            final Iterator<?> theOther =
                ((IterableView<?>) ((IterableView<?>) obj))
                    .asIterable()
                    .iterator();
            while(mine.hasNext() && theOther.hasNext()){
                final E next = mine.next();
                final Object other = theOther.next();
                if(next != theOther && next != null && !next.equals(other)){
                    return false;
                }
            }
            return(!mine.hasNext() && !theOther.hasNext());
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Series<E> filterWith(final Predicate<E> predicate){

        final Object[] data = new Object[size()];
        int offset = 0;
        for(final E item : this){
            if(predicate.apply(item)){
                data[offset++] = item;
            }
        }
        return clone((E[]) Arrays.copyOf(data, offset));
    }

    @Override
    public int hashCode(){
        int hashCode = 1;
        final Iterator<E> i = iterator();
        while(i.hasNext()){
            final E obj = i.next();
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        }
        return hashCode;
    }

    @Override
    public Series<E> insert(final int position, final E item){
        return insertArray(position, Converter.objectToArray(item));
    }

    @Override
    public abstract Series<E> insertArray(final int position, final E... items);

    @Override
    public Series<E> insertCollection(final int position,
        final Collection<E> items){
        return insertArray(position, Converter.collectionToArray(items));
    }

    @Override
    public Series<E> insertGathering(final int position,
        final Gathering<E> items){
        return insertArray(position, Converter.gatheringToArray(items));
    }

    @Override
    public String join(){
        return join(" ");
    }

    @Override
    public String join(final String separator){

        final Iterator<E> iter = iterator();
        final StringBuilder sb = new StringBuilder();
        if(iter.hasNext()){
            sb.append(iter.next());
            while(iter.hasNext()){
                sb.append(separator).append(iter.next());
            }
        }
        return sb.toString();

    }

    @Override
    public Series<E> prepend(final E item){
        return insert(0, item);
    }

    @Override
    public Series<E> prependArray(final E... items){
        return insertArray(0, items);
    }

    @Override
    public Series<E> prependCollection(final Collection<E> items){
        return insertCollection(0, items);
    }

    @Override
    public Series<E> prependGathering(final Gathering<E> items){
        return insertGathering(0, items);
    }

    @Override
    public Series<E> remove(final E item){
        return removeArray(Converter.objectToArray(item));
    }

    @Override
    public abstract Series<E> removeArray(final E... items);

    @Override
    public Series<E> removeCollection(final Collection<E> items){
        return removeArray(Converter.collectionToArray(items));
    }

    @Override
    public Series<E> removeGathering(final Gathering<E> items){
        return removeArray(Converter.gatheringToArray(items));
    }

    @Override
    public String toString(){
        final Iterator<E> i = iterator();
        if(!i.hasNext()){
            return "[]";
        }

        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        for(;;){
            final E t = i.next();
            sb.append(t == this ? "(this Series)" : t);
            if(!i.hasNext()){
                return sb.append(']').toString();
            }
            sb.append(", ");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] asArray(){
        return (E[]) asList().toArray();
    }

    @Override
    public E[] asArray(final E[] placeHolder){
        return asList().toArray(placeHolder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <O> Series<O> transform(final Function<E, O> function){
        final Object[] data = new Object[size()];
        int offset = 0;
        for(final E item : this){
            data[offset++] = function.apply(item);
        }
        return (Series<O>) clone((E[]) Arrays.copyOf(data, offset));
    }

}
