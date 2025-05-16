package net.digitaldetective47.itertools;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Enumerate the elements of an iterable by the order there are returned
 */
public class Enumerate<T> implements Iterator<Map.Entry<Integer, T>> {
    private Iterator<? extends T> source;
    private int count;

    public Enumerate(Iterable<? extends T> source, int start) {
        this.source = Objects.requireNonNull(source).iterator();
        count = start;
    }

    /**
     * Uses a start value of 0.
     */
    public Enumerate(Iterable<? extends T> source) {
        this(source, 0);
    }

    public boolean hasNext() {
        return source.hasNext();
    }

    public Map.Entry<Integer, T> next() {
        return new AbstractMap.SimpleImmutableEntry<Integer, T>(count++, source.next());
    }
}
