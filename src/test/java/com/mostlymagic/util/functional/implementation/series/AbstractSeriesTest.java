package com.mostlymagic.util.functional.implementation.series;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.mostlymagic.util.functional.Series;
import com.mostlymagic.util.functional.helper.Function;
import com.mostlymagic.util.functional.helper.Predicate;

public abstract class AbstractSeriesTest{

    protected abstract <T> Series<T> createSeries(T... items);

    @Test
    public void testAdding(){
        final Series<String> series = createSeries("Hey", "you");

        final Series<String> complete =
            series
                .add("the")
                .addArray("rock", "steady")
                .addCollection(Arrays.asList("crew", "show", "what"))
                .addGathering(createSeries("you", "do"));
        assertEquals(
            complete,
            createSeries("Hey, you, the, rock, steady, crew, show, what, you, do"
                .split("\\W+")));

    }

    @Test
    public void testInserting(){
        final Series<Integer> complete =
            createSeries(1, 10)
                .insert(1, 2)
                .insertAll(2 /* position */, 3, 4, 5)
                .insertCollection(5, Arrays.asList(6, 7, 8, 9));
        assertEquals(createSeries(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), complete);
    }

    @Test
    public void testPrepending(){
        final Series<Integer> complete =
            createSeries(10)
                .prepend(9)
                .prependAll(6, 7, 8)
                .prependCollection(Arrays.asList(4, 5))
                .prependGathering(createSeries(1, 2, 3));
        assertEquals(createSeries(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), complete);
    }

    @Test
    public void testAppending(){
        final Series<Integer> complete =
            createSeries(1)
                .appendCollection(Arrays.asList(2, 3))
                .appendGathering(createSeries(4, 5))
                .appendAll(6, 7, 8, 9)
                .append(10);
        assertEquals(createSeries(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), complete);
    }

    @Test
    public void testRemoving(){
        final Series<Integer> series =
            createSeries(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .remove(2)
                .removeAll(3, 4, 5)
                .removeCollection(Arrays.asList(6, 7))
                .removeGathering(createSeries(8, 9));
        assertEquals(series, createSeries(1, 10));
    }

    @Test
    public void testFiltering(){
        assertEquals(
            createSeries(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).filterWith(
                new Predicate<Integer>(){

                    @Override
                    public boolean apply(final Integer input){
                        return input.intValue() % 2 == 0;
                    }
                }), createSeries(2, 4, 6, 8, 10));
    }

    @Test
    public void testTransforming(){
        final Series<Integer> integers =
            createSeries(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final Series<String> strings =
            integers.transform(new Function<Integer, String>(){

                @Override
                public String apply(final Integer input){
                    return input.toString();
                }
            });
        final Series<Integer> integers2 =
            strings.transform(new Function<String, Integer>(){

                @Override
                public Integer apply(final String input){
                    return Integer.valueOf(input);
                }
            });
        assertEquals(integers, integers2);

    }

    @Test
    public void testUtilityMethods(){
        final Series<Integer> original = createSeries(3, 4, 5);
        final Series<Integer> clone =
            original
                .prependAll(1, 2)
                .appendAll(9, 10)
                .insertCollection(5, Arrays.asList(6, 7, 8))
                .removeGathering(createSeries(6, 7, 8))
                .removeAll(9, 10)
                .deleteItemOffsets(0, 1);
        assertEquals(original, clone);
        assertEquals(original.hashCode(), clone.hashCode());
        assertEquals(original.toString(), clone.toString());
    }

    @Test
    public void testReverse(){
        final Series<Integer> original = createSeries(1, 2, 3, 4, 5);
        final Series<Integer> reverse = original.reverse();
        assertEquals(reverse, createSeries(5, 4, 3, 2, 1));
        final Series<Integer> backToOrig = reverse.reverse();
        assertEquals(original, backToOrig);
    }

    @Test
    public void testSequence(){
        final Series<Integer> subSequence =
            createSeries(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subSequence(5, 8)
                .subSequence(0, 2);
        assertEquals(createSeries(6, 7), subSequence);
        assertEquals(subSequence.subSequence(0, subSequence.size()),
            subSequence);
        final Series<Integer> sequence = createSeries(1, 2, 3, 4, 5, 6, 7);
        assertEquals(sequence.head(), Integer.valueOf(1));
        assertEquals(sequence.tail(), createSeries(2, 3, 4, 5, 6, 7));

    }

    @Test
    public void testJoin(){
        final String[] items = new String[] { "foo", "bar", "baz", "phleem" };
        final String delim = "::";
        final String joined = createSeries(items).join(delim);
        assertArrayEquals(items, joined.split(delim));
    }

}
