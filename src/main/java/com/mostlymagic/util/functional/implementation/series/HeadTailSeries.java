package com.mostlymagic.util.functional.implementation.series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.mostlymagic.util.functional.Gathering;
import com.mostlymagic.util.functional.Series;
import com.mostlymagic.util.functional.helper.Function;
import com.mostlymagic.util.functional.helper.Predicate;
import com.mostlymagic.util.functional.utilities.ArrayHelper;
import com.mostlymagic.util.functional.utilities.Check;
import com.mostlymagic.util.functional.utilities.IteratorHelper;

public class HeadTailSeries<E> extends AbstractSeries<E>{

    private static final class EmptyHeadTailSeries extends
        AbstractSeries<Object>{

        @Override
        public List<Object> asList(){
            return Collections.emptyList();
        }

        @Override
        protected Series<Object> clone(final Object[] data){
            throw new UnsupportedOperationException();
        }

        @Override
        public Series<Object> deleteItemOffsets(final int... positions){
            return EMPTY;
        }

        @Override
        public Series<Object> insertAll(final int position,
            final Object... items){
            Check.notNull(items, "Null not allowed");
            if(items.length == 0){
                return this;
            }
            Check.that(position == 0 || position == END,
                "Only 0 or -1 are allowed as position");
            return ofItems(items);
        }

        @Override
        public Iterator<Object> iterator(){
            return IteratorHelper.emptyIterator();
        }

        @Override
        public Series<Object> removeAll(final Object... items){
            return this;
        }

        @Override
        public Series<Object> reverse(){
            return this;
        }

        @Override
        public int size(){
            return 0;
        }

        @Override
        public Series<Object> subSequence(final int from, final int to){
            Check.eq(from, 0, "Only 0 is allowed as start offset");
            Check.that(to == 0 || to == END,
                "Only 0 or -1 are allowed as end offset");
            return this;
        }

        @Override
        public Series<Object> replace(final Object item,
            final Object replacement){
            return this;
        }

        @Override
        public Series<Object> replaceAll(final Object item,
            final Object replacement){
            return this;
        }
    }

    private class DescendingIterator implements Iterator<E>{

        private HeadTailSeries<E> current = HeadTailSeries.this;

        @Override
        public boolean hasNext(){
            return current != null;
        }

        @Override
        public E next(){
            if(current == null){
                throw new IndexOutOfBoundsException();
            }
            final E item = current.head;
            current = current.tail;
            return item;
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

    }

    private static final Series<Object> EMPTY = new EmptyHeadTailSeries();

    public static <T> Series<T> empty(){
        @SuppressWarnings("unchecked")
        final Series<T> result = (Series<T>) EMPTY;
        return result;
    }

    @Override
    public Series<E> appendCollection(final Collection<E> items){
        return ofIterable(items).prependGathering(this);
    }

    public static <T> Series<T> ofIterable(final Iterable<T> iterable){
        final Iterator<T> reverseIterator =
            IteratorHelper.getReverseIterator(iterable);
        Series<T> result;
        if(reverseIterator.hasNext()){
            result =
                of(reverseIterator.next()).prependReverseIterator(
                    reverseIterator);
        } else{
            result = empty();
        }
        return result;
    }

    public static <T> HeadTailSeries<T> of(final T item){
        return new HeadTailSeries<T>(item, null);
    }

    public static <T> HeadTailSeries<T> of(final T item, final T... more){
        return new HeadTailSeries<T>(item, ofItems(more));
    }

    public static <T> HeadTailSeries<T> ofItems(final T... items){
        Check.notNull(items, "Null not allowed");
        Check.gt(items.length, 0, "Empty array not allowed");
        final Iterator<T> iterator = IteratorHelper.getReverseIterator(items);
        return of(iterator.next()).prependReverseIterator(iterator);
    }

    private final E head;

    private final int size;

    private final HeadTailSeries<E> tail;

    private HeadTailSeries(final E head, final HeadTailSeries<E> tail){
        this.head = head;
        this.tail = tail;
        this.size = (tail == null ? 1 : tail.size + 1);
    }

    @Override
    public Series<E> append(final E item){
        return reverse().prepend(item).reverse();
    }

    @Override
    public Series<E> appendAll(final E... items){
        return ofItems(items).prependGathering(this);
    }

    @Override
    public List<E> asList(){
        final List<E> list = new ArrayList<E>(size);
        for(final E e : this){
            list.add(e);
        }
        return list;
    }

    @Override
    protected Series<E> clone(final E[] data){
        return ofItems(data);
    }

    @Override
    public Series<E> deleteItemOffsets(final int... positions){
        if(positions == null || positions.length == 0){
            return this;
        }
        final int[] sortedPositions =
            ArrayHelper.createUniqueSortedCopy(positions);
        Check.gte(sortedPositions[0], 0, "Negative values not allowed");
        Check.lt(sortedPositions[sortedPositions[sortedPositions.length - 1]],
            size - 1, "Index out of bounds");
        int itemOffset = 0, arrOffset = 0, ref = sortedPositions[arrOffset];
        Series<E> copy = empty();
        if(sortedPositions.length == size){
            return copy;
        }
        for(final E e : this){
            if(itemOffset++ == ref){
                if(arrOffset < sortedPositions.length - 1){
                    ref = sortedPositions[++arrOffset];

                }
            } else{
                copy = copy.add(e);
            }
        }
        return copy;
    }

    @Override
    public Series<E> insertAll(final int position, final E... items){
        Check.gte(position, END, "Negative values below -1 not allowed");
        Check.lte(position, size, "Index out of Bound");
        final int effpos = position == END ? size : position;

        final Iterator<E> wrappedIterator =
            insertReverseIterator(effpos,
                IteratorHelper.getReverseIterator(items));

        return of(wrappedIterator.next()).prependReverseIterator(
            wrappedIterator);
    }

    private Iterator<E> insertReverseIterator(final int position,
        final Iterator<E> reverseItemsIterator){
        @SuppressWarnings("unchecked")
        final Iterator<E> wrappedIterator = IteratorHelper.chain(
        // end (reverse)
            subSequence(position, size()).reverse().asIterable().iterator(),
            // items to insert (reverse)
            reverseItemsIterator,
            // start (reverse)
            subSequence(0, position).reverse().asIterable().iterator());
        return wrappedIterator;
    }

    @Override
    public Iterator<E> iterator(){
        return new DescendingIterator();
    }

    @Override
    public HeadTailSeries<E> prepend(final E item){
        return new HeadTailSeries<E>(item, this);
    }

    @Override
    public Series<E> prependAll(final E... items){
        if(items.length == 0){
            return this;
        }
        Series<E> series = this;
        final Iterator<E> arrayIterator =
            IteratorHelper.getReverseIterator(items);
        while(arrayIterator.hasNext()){
            final E e = arrayIterator.next();
            series = series.prepend(e);
        }
        return series;

    }

    @Override
    public Series<E> prependGathering(final Gathering<E> items){
        return prependReverseIterator(IteratorHelper.getReverseIterator(items));
    }

    HeadTailSeries<E> prependReverseIterator(final Iterator<E> reverseIterator){
        HeadTailSeries<E> series = this;
        while(reverseIterator.hasNext()){
            series = series.prepend(reverseIterator.next());
        }
        return series;
    }

    @Override
    public Series<E> removeAll(final E... items){
        if(items == null || items.length == 0){
            return this;
        }
        final Set<E> setView = new HashSet<E>(Arrays.asList(items));
        if(setView.size() == size){
            return empty();
        }
        Series<E> copy = empty();
        for(final E e : this.reverse()){
            if(!setView.contains(e)){
                copy = copy.prepend(e);
            }
        }
        return copy;
    }

    @Override
    public HeadTailSeries<E> reverse(){
        if(size == 1){
            return this;
        }
        final Iterator<E> iterator = iterator();
        HeadTailSeries<E> series = of(iterator.next());
        while(iterator.hasNext()){
            series = series.prepend(iterator.next());
        }
        return series;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public Series<E> subSequence(final int from, final int to){
        Check.lte(to, size, "Index Out Of Bounds");
        Check.gte(from, 0, "Negative offsets not allowed");
        if(from == to){
            return empty();
        }
        final int effectiveTo = (to == END ? size : to);
        Check.lte(from, effectiveTo, "From must be lte to");
        HeadTailSeries<E> current = this;
        Series<E> sequence = empty();
        for(int ct = 0; ct < effectiveTo; ct++){
            if(ct >= from){
                sequence = sequence.add(current.head);
            }
            current = current.tail;
        }
        return sequence;
    }

    @Override
    public HeadTailSeries<E> tail(){
        return tail;
    }

    @Override
    public Series<E> insert(final int position, final E item){
        return subSequence(position, END).prepend(item).prependGathering(
            subSequence(0, position));
    }

    @Override
    public Series<E> insertCollection(final int position,
        final Collection<E> items){
        return insertGathering(position, ofIterable(items));
    }

    @Override
    public Series<E> insertGathering(final int position,
        final Gathering<E> items){
        return subSequence(position, END)
            .prependGathering(items)
            .prependGathering(subSequence(0, position));
    }

    @Override
    public Series<E> appendGathering(final Gathering<E> items){
        return items instanceof Series<?> ? ((Series<E>) items)
            .prependGathering(this) : ofGathering(items).prependGathering(this);
    }

    public static <T> Series<T> ofGathering(final Gathering<T> items){
        return ofIterable(items.asIterable());
    }

    @Override
    public Series<E> prependCollection(final Collection<E> items){
        return ofIterable(items).appendGathering(this);
    }

    @Override
    public Series<E> add(final E item){
        return append(item);
    }

    @Override
    public Series<E> addArray(final E... items){
        return ofItems(items).prependGathering(this);
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
    public Series<E> deleteItemOffset(final int position){
        return null;
    }

    @Override
    public Series<E> remove(final E item){
        final List<E> asList = asList();
        final boolean removed = asList.remove(item);
        return removed ? ofIterable(asList) : this;
    }

    @Override
    public Series<E> removeCollection(final Collection<E> items){
        final List<E> asList = asList();
        final boolean removed = asList.removeAll(items);
        return removed ? ofIterable(asList) : this;
    }

    @Override
    public Series<E> removeGathering(final Gathering<E> items){
        final List<E> asList = asList();
        boolean removed = false;
        for(final E e : items.asIterable()){
            if(asList.remove(e)){
                removed = true;
            }
        }
        return removed ? ofIterable(asList) : this;
    }

    @Override
    public Series<E> filterWith(final Predicate<E> predicate){
        final List<E> list = asList();
        final Iterator<E> iterator = list.iterator();
        boolean filtered = false;
        while(iterator.hasNext()){
            final E e = iterator.next();
            if(!predicate.apply(e)){
                iterator.remove();
                filtered = true;
            }
        }
        return filtered ? ofIterable(list) : this;
    }

    @Override
    public E head(){
        return head;
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

    @Override
    public <O> Series<O> transform(final Function<E, O> function){
        final List<O> list = new ArrayList<O>(size);
        for(final E e : this){
            list.add(function.apply(e));
        }
        return ofIterable(list);
    }

    @Override
    public Series<E> replace(final E item, final E replacement){
        final int position = indexOf(item);
        return position < 0 ? this : subSequence(position + 1, END).prepend(
            item).prependGathering(subSequence(0, position));
    }

    @Override
    public Series<E> replaceAll(final E item, final E replacement){
        Series<E> temp = this;
        final int[] indexesOf = indexesOf(item);

        for(@SuppressWarnings("unused")
        final int i : indexesOf){
            temp = temp.replace(item, replacement);
        }
        return null;
    }

}
