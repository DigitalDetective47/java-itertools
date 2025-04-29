package net.digitaldetective47.itertools;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Enumerate the elements of an iterable by the order there are returned
 */
public class Enumerate<T> implements Iterator<Map.Entry<Integer, T>> {
    private Iterator<T> source;
    private int count;

    public Enumerate(Iterable<T> source, int start) {
        this.source = source.iterator();
        count = start;
    }

    public Enumerate(Iterable<T> source) {
        this(source, 0);
    }

    public boolean hasNext() {
        return source.hasNext();
    }

    public Map.Entry<Integer, T> next() {
        return new AbstractMap.SimpleImmutableEntry<Integer, T>(count++, source.next());
    }
}
