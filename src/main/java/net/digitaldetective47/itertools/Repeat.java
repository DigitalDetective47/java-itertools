package net.digitaldetective47.itertools;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Repeat a single object multiple times
 */
public class Repeat<T> implements Iterator<T> {
    private T element;
    private long timesRemaining;

    /**
     * Yields the given element forever
     */
    public Repeat(T element) {
        this.element = element;
        timesRemaining = -1;
    }

    /**
     * @param times how many times this iterator should yield the given element
     */
    public Repeat(T element, long times) {
        if (times < 0) {
            throw new IllegalArgumentException("times cannot be negative");
        }
        this.element = element;
        timesRemaining = times;
    }

    public boolean hasNext() {
        return timesRemaining != 0;
    }

    public T next() {
        if (timesRemaining == 0) {
            throw new NoSuchElementException();
        }
        if (timesRemaining > 0) {
            timesRemaining--;
        }
        return element;
    }
}
