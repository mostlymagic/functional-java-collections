package com.mostlymagic.util.functional.implementation.gathering;

import java.util.Iterator;

import com.mostlymagic.util.functional.Gathering;

/**
 * @author Sean Patrick Floyd
 */
public abstract class AbstractGathering<E> implements Gathering<E>{

    @Override
    public String join(){
        return join(" ");
    }

    @Override
    public String join(final String separator){

        final Iterator<E> iter = asIterable().iterator();
        final StringBuilder sb = new StringBuilder();
        if(iter.hasNext()){
            sb.append(iter.next());
            while(iter.hasNext()){
                sb.append(separator).append(iter.next());
            }
        }
        return sb.toString();

    }

}
