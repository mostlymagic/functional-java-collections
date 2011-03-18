package com.mostlymagic.util.functional.utilities;

import static com.mostlymagic.util.functional.utilities.IteratorHelper.arrayIterator;
import static com.mostlymagic.util.functional.utilities.IteratorHelper.chain;
import static com.mostlymagic.util.functional.utilities.IteratorHelper.emptyIterator;
import static com.mostlymagic.util.functional.utilities.IteratorHelper.getReverseIterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.mostlymagic.util.functional.Gathering;
import com.mostlymagic.util.functional.Series;
import com.mostlymagic.util.functional.implementation.series.HeadTailSeries;

public class IteratorHelperTest{

    private static String[] createArray(){
        return new String[] { "abc", "def", "ghi" };
    }

    private Series<String> createSeries(){
        return HeadTailSeries.of("foo", "bar", "baz");
    }

    @Test
    public void testArrayIterator(){
        final String[] arr = createArray();
        final Iterator<String> iterator = arrayIterator(arr);
        for(final String item : arr){
            assertTrue(iterator.hasNext());
            assertEquals(item, iterator.next());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testChain(){
        final List<Integer> list1 = createList();
        final List<Integer> list2 = createList();
        final List<Integer> list3 = createList();
        final Iterator<Integer> it =
            chain(list1.iterator(), list2.iterator(), list3.iterator());
        for(final List<Integer> list : Arrays.asList(list1, list2, list3)){
            for(final Integer integer : list){
                assertTrue(it.hasNext());
                assertEquals(integer, it.next());
            }
        }

    }

    private List<Integer> createList(){
        return Arrays.asList(1, 2, 3);
    }

    @Test
    public void testEmptyIterator(){
        final Iterator<BigInteger> iterator = emptyIterator();
        assertFalse(iterator.hasNext());
        try{
            iterator.next();
            fail("Iterator succeeded where it should fail");
        } catch(final Exception e){
            // ok
        }
        try{
            iterator.remove();
            fail("Iterator succeeded where it should fail");
        } catch(final Exception e){
            // ok
        }
    }

    @Test
    public void testGetReverseIteratorForArrayOfT(){
        final String[] array = createArray();
        final Iterator<String> it = getReverseIterator(array);
        for(int i = array.length - 1; i >= 0; i--){
            assertTrue(it.hasNext());
            assertEquals(array[i], it.next());
        }

    }

    @Test
    public void testGetReverseIteratorForGatheringOfT(){
        final Gathering<String> gathering = createSeries();
        final Iterator<String> reverseIterator = getReverseIterator(gathering);
        final Iterator<String> iterator = gathering.asIterable().iterator();
        final List<String> reverse = new ArrayList<String>(gathering.size());
        final List<String> forward = new ArrayList<String>(gathering.size());
        while(iterator.hasNext()){
            assertTrue(reverseIterator.hasNext());
            reverse.add(reverseIterator.next());
            forward.add(iterator.next());
        }
        assertFalse(reverseIterator.hasNext());
        Collections.reverse(reverse);
        assertEquals(forward, reverse);
    }

    @Test
    public void testGetReverseIteratorForIterableOfT(){
        final List<Integer> list = createList();
        final List<Integer> reverse = new ArrayList<Integer>(list);
        Collections.reverse(reverse);
        final Iterator<Integer> reverseIterator = getReverseIterator(list);
        for(final Integer integer : reverse){
            assertTrue(reverseIterator.hasNext());
            assertEquals(integer, reverseIterator.next());
        }
    }

    @Test
    public void testGetReverseIteratorForSeriesOfT(){
        final Series<String> series = createSeries();
        final Iterator<String> it1 = getReverseIterator(series);
        final Iterator<String> it2 = series.reverse().asIterable().iterator();
        while(it1.hasNext()){
            assertTrue(it2.hasNext());
            assertEquals(it1.next(), it2.next());
        }
        assertFalse(it2.hasNext());
    }

}
