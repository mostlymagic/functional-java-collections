package com.mostlymagic.util.functional.implementation.series;




import com.mostlymagic.util.functional.Series;

public class ArraySeriesTest extends AbstractSeriesTest{

    @Override
    protected <T> Series<T> createSeries(final T... items){
        return ArraySeries.of(items);
    }

}
