package com.mostlymagic.util.functional.implementation.series;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.mostlymagic.util.functional.Series;
import com.mostlymagic.util.functional.helper.Function;
import com.mostlymagic.util.functional.helper.Predicate;

public class ArraySeriesTest{

    @Test
    public void testAdding(){
        final Series<String> series = ArraySeries.of("Hey", "you");

        final Series<String> complete =
            series
                .add("the")
                .addArray("rock", "steady")
                .addCollection(Arrays.asList("crew", "show", "what"))
                .addGathering(ArraySeries.of("you", "do"));
        assertEquals(complete,
            ArraySeries
                .of("Hey, you, the, rock, steady, crew, show, what, you, do"
                    .split("\\W+")));

    }

    @Test
    public void testInserting(){
        final Series<Integer> complete =
            ArraySeries
                .of(1, 10)
                .insert(1, 2)
                .insertArray(2, 3, 4, 5)
                .insertCollection(5, Arrays.asList(6, 7, 8, 9));
        assertEquals(ArraySeries.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), complete);
    }

    @Test
    public void testPrepending(){
        final Series<Integer> complete =
            ArraySeries
                .of(10)
                .prepend(9)
                .prependArray(6, 7, 8)
                .prependCollection(Arrays.asList(4, 5))
                .prependGathering(ArraySeries.of(1, 2, 3));
        assertEquals(ArraySeries.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), complete);
    }

    @Test
    public void testAppending(){
        final Series<Integer> complete =
            ArraySeries
                .of(1)
                .appendCollection(Arrays.asList(2, 3))
                .appendGathering(ArraySeries.of(4, 5))
                .appendArray(6, 7, 8, 9)
                .append(10);
        assertEquals(ArraySeries.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), complete);
    }

    @Test
    public void testRemoving(){
        final Series<Integer> series =
            ArraySeries
                .of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .remove(2)
                .removeArray(3, 4, 5)
                .removeCollection(Arrays.asList(6, 7))
                .removeGathering(ArraySeries.of(8, 9));
        assertEquals(series, ArraySeries.of(1, 10));
    }

    @Test
    public void testFiltering(){
        assertEquals(
            ArraySeries.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).filterWith(
                new Predicate<Integer>(){

                    @Override
                    public boolean apply(final Integer input){
                        return input.intValue() % 2 == 0;
                    }
                }), ArraySeries.of(2, 4, 6, 8, 10));
    }

    @Test
    public void testTransforming(){
        final Series<Integer> integers =
            ArraySeries.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
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
        final Series<Integer> original = ArraySeries.of(3, 4, 5);
        final Series<Integer> clone =
            original
                .prependArray(1, 2)
                .appendArray(9, 10)
                .insertCollection(5, Arrays.asList(6, 7, 8))
                .removeGathering(ArraySeries.of(6, 7, 8))
                .removeArray(9, 10)
                .deleteItems(0, 1);
        assertEquals(original, clone);
        assertEquals(original.hashCode(), clone.hashCode());
        assertEquals(original.toString(), clone.toString());
    }

    @Test
    public void testReverse(){
        final Series<Integer> original = ArraySeries.of(1, 2, 3, 4, 5);
        final Series<Integer> reverse = original.reverse();
        assertEquals(reverse, ArraySeries.of(5, 4, 3, 2, 1));
        final Series<Integer> backToOrig = reverse.reverse();
        assertEquals(original, backToOrig);
    }

    @Test
    public void testSequence(){
        final Series<Integer> subSequence =
            ArraySeries
                .of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subSequence(5, 19)
                .subSequence(0, 2);
        assertEquals(ArraySeries.of(6, 7), subSequence);

    }

}
