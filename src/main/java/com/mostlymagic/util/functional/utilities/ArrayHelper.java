package com.mostlymagic.util.functional.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ArrayHelper{

    private ArrayHelper(){
    }

    public static Object[] removeArrayItems(final Object[] original,
        final Object[] items){

        final int[] deletions = new int[original.length];
        int delPos = 0;
        for(final Object object : items){
            for(int i = 0; i < original.length; i++){
                final Object reference = original[i];
                if(object == reference
                    || (object != null && object.equals(reference))){
                    boolean found = false;
                    for(final int j : deletions){
                        if(j == i){
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        deletions[delPos++] = i;
                    }
                }
            }
        }
        return removeArrayOffsets(original, Arrays.copyOf(deletions, delPos));
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] createArrayFromIterable(final Iterable<E> input){
        final List<E> data = new ArrayList<E>();
        for(final E e : input){
            data.add(e);
        }
        return (E[]) data.toArray();
    }

    public static Object[] mergeArrays(final Object[] original,
        final Object[] items,
        final int position){
        final Object[] container = new Object[original.length + items.length];
        System.arraycopy(original, 0, container, 0, position);
        System.arraycopy(items, 0, container, position, items.length);
        System.arraycopy(original, position, container,
            position + items.length, original.length - position);
        return container;
    }

    public static Object[] removeArrayOffsets(final Object[] original,
        final int[] positions){
        final int[] copiedPositions = createUniqueSortedCopy(positions);

        Check.gte(copiedPositions[0], 0, "Illegal Offset (negative): {0}",
            copiedPositions[0]);
        Check.lt(copiedPositions[copiedPositions.length - 1], original.length,
            "Illegal Offset (beyond array bounds): {0}",
            copiedPositions[copiedPositions.length - 1]);

        final Object[] result =
            new Object[original.length - copiedPositions.length];
        int originalOffset = 0, resultOffset = 0, lastPosition = 0;
        for(final int position : copiedPositions){
            final int length = position - originalOffset;
            System.arraycopy(original, originalOffset, result, resultOffset,
                length);
            originalOffset = position + 1;
            resultOffset += length;
            lastPosition = position;
        }
        if(lastPosition > 0 && lastPosition < original.length - 1){
            System.arraycopy(original, lastPosition + 1, result, resultOffset,
                (original.length - lastPosition) - 1);
        }
        return result;

    }

    private static int[] createUniqueSortedCopy(final int[] positions){
        final int[] copiedPositions =
            Arrays.copyOf(positions, positions.length);
        Arrays.sort(copiedPositions);
        int lastValue = 0, offset = 0;
        for(int i = 0; i < copiedPositions.length; i++){
            final int currValue = copiedPositions[i];
            if(i == 0 || currValue > lastValue){
                copiedPositions[offset++] = currValue;
                lastValue = currValue;
            }
        }
        return Arrays.copyOfRange(copiedPositions, 0, offset);

    }

}
