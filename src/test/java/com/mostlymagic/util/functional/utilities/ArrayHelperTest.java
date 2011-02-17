package com.mostlymagic.util.functional.utilities;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class ArrayHelperTest{

    private static final String SPACE = "\\s+";

    @Test
    public void testRemoveArrayItems(){
        final String first = "def";
        final String second = "jkl";
        final Object[] data =
            ("abc " + first + " ghi " + second + " " + first).split(SPACE);
        final Object[] stripped =
            ArrayHelper.removeArrayItems(data, new String[] { second, first });
        assertArrayEquals("abc ghi".split(SPACE), stripped);

    }

    @Test
    public void testCreateArrayFromIterable(){
        final Object[] data = "abc def ghi jkl".split(SPACE);
        assertArrayEquals(data,
            ArrayHelper.createArrayFromIterable(Arrays.asList(data)));
    }

    @Test
    public void testMergeArrays(){
        final String space = " ";
        final String prefix = "my dog";
        final String suffix = "has fleas";
        final String insertion = "sometimes";
        final String origStr = prefix + space + suffix;
        final Object[] original = origStr.split(SPACE);
        final Object[] insertionData = new String[] { insertion };

        assertArrayEquals(ArrayHelper.mergeArrays(original, insertionData, 0),
            (insertion + space + origStr).split(SPACE));
        assertArrayEquals(
            ArrayHelper.mergeArrays(original, insertionData, original.length),
            (origStr + space + insertion).split(SPACE));
        assertArrayEquals(ArrayHelper.mergeArrays(original, insertionData, 2),
            (prefix + space + insertion + space + suffix).split(SPACE));
    }

    @Test
    public void testRemoveArrayOffsets(){
        final int arraySize = 15;
        final boolean isEven = arraySize % 2 == 0;
        final Object[] items = new Object[arraySize];
        final int[] evenPositions = new int[arraySize / 2 + (isEven ? 0 : 1)];
        final int[] oddPositions =
            new int[evenPositions.length - (isEven ? 0 : 1)];
        int oddPosOffset = 0;
        int evenPosOffset = 0;
        for(int i = 0; i < arraySize; i++){
            items[i] = Integer.valueOf(i);
            if(i % 2 == 1){
                oddPositions[oddPosOffset++] = i;
            } else{
                evenPositions[evenPosOffset++] = i;
            }
        }
        final Object[] oddStripped =
            ArrayHelper.removeArrayOffsets(items, oddPositions);
        for(int i = 0; i < arraySize; i++){
            final int result =
                Arrays.binarySearch(oddStripped, Integer.valueOf(i));
            assertTrue(result > -1 == (i % 2 == 0));
        }
        final Object[] evenStripped =
            ArrayHelper.removeArrayOffsets(items, evenPositions);
        for(int i = 0; i < arraySize; i++){
            final int result =
                Arrays.binarySearch(evenStripped, Integer.valueOf(i));
            assertFalse(result > -1 == (i % 2 == 0));
        }

    }

}
