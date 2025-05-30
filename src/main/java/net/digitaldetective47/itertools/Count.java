package net.digitaldetective47.itertools;

import java.util.PrimitiveIterator;

/**
 * Counts numbers forever
 */
public class Count implements PrimitiveIterator.OfLong {
    long next;
    long step;

    public Count(long start, long step) {
        this.step = step;
        next = start;
    }

    /**
     * Uses a step value of 1
     */
    public Count(long start) {
        this(start, 1);
    }

    /**
     * Uses a start of 0 and a step of 1
     */
    public Count() {
        this(0);
    }

    public boolean hasNext() {
        return true;
    }

    public long nextLong() {
        long cur = next;
        next += step;
        return cur;
    }
}
