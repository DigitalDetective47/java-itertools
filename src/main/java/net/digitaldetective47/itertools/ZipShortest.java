package net.digitaldetective47.itertools;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Interleave elements from multiple iterators
 */
public class ZipShortest<T> implements Iterator<T[]> {
    private Iterator<T>[] sources;

    public ZipShortest(Iterable<T>... sources) {
        this.sources = (Iterator<T>[]) new Iterator[sources.length];
        Arrays.parallelSetAll(this.sources, i -> sources[i].iterator());
    }

    public boolean hasNext() {
        return Arrays.stream(sources).parallel().unordered().allMatch(source -> source.hasNext());
    }

    public T[] next() {
        T[] ret = (T[]) new Object[sources.length];
        Arrays.parallelSetAll(ret, i -> sources[i].next());
        return ret;
    }
}
