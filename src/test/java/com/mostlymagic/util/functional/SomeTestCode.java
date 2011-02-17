package com.mostlymagic.util.functional;

public class SomeTestCode{

    @SuppressWarnings("null")
    public static void main(final String[] args){
        final Series<String> series = null;
        final Iterable<String> iterable =
            series.prepend("godzilla").append("king kong").asIterable();
    }

}
