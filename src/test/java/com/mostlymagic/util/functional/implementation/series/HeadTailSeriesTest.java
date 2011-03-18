package com.mostlymagic.util.functional.implementation.series;

import static com.mostlymagic.util.functional.implementation.series.HeadTailSeries.ofItems;
import static com.mostlymagic.util.functional.implementation.series.HeadTailSeries.ofIterable;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.mostlymagic.util.functional.Series;

public class HeadTailSeriesTest extends AbstractSeriesTest{

    @Override
    protected <T> Series<T> createSeries(final T... items){
        return HeadTailSeries.ofItems(items);
    }

    @Test
    public void testCreation(){
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals(list, ofIterable(list).asList());
        final String[] arr = new String[] { "foo", "bar", "baz" };
        assertArrayEquals(arr, ofItems(arr).asArray());
        assertArrayEquals(arr, ofItems(arr).asArray(new String[arr.length]));
    }

}
