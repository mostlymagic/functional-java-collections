package com.mostlymagic.util.functional.utilities;

import java.text.MessageFormat;

/**
 * Assertion helper class.
 * 
 * @author Sean Patrick Floyd
 */
public final class Check{

    public static void notNull(final Object value, final Object... messageData){
        that(value != null, messageData);
    }

    public static void gte(final int value,
        final int reference,
        final Object... messageData){
        gt(value + 1, reference, messageData);

    }

    public static void gt(final int i,
        final int reference,
        final Object... messageData){
        that(i > reference, messageData);

    }

    public static void that(final boolean state, final Object... messageData){
        if(!state){
            fail(messageData);
        }
    }

    public static void fail(final Object... messageData){
        if(messageData == null || messageData.length == 0
            || !(messageData[0] instanceof String)){
            throw new IllegalArgumentException();
        } else{
            final String message = assembleMessage(messageData);
            throw new IllegalArgumentException(message);
        }
    }

    private static String assembleMessage(final Object... messageData){
        String message;
        if(messageData.length == 1){
            message = messageData[0].toString();
        } else{
            final Object[] tmpArray = new Object[messageData.length - 1];
            System.arraycopy(messageData, 1, tmpArray, 0,
                messageData.length - 1);
            message = MessageFormat.format(messageData[0].toString(), tmpArray);
        }
        return message;
    }

    public static void lte(final int value,
        final int reference,
        final Object... messageData){
        lt(value - 1, reference, messageData);

    }

    public static void lt(final int i,
        final int reference,
        final Object... messageData){
        that(i < reference, messageData);
    }

    public static void eq(final int i,
        final int reference,
        final Object... messageData){
        that(i == reference, messageData);
    }

}
