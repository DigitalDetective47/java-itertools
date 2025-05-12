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

    /**
     * Type-safe version for two iterables of different types
     */
    static public class DualType<U, V> implements Iterator<DualTypePair<U, V>> {
        private Iterator<U> uSource;
        private Iterator<V> vSource;

        public DualType(Iterable<U> uSource, Iterable<V> vSource) {
            this.uSource = uSource.iterator();
            this.vSource = vSource.iterator();
        }

        public boolean hasNext() {
            return uSource.hasNext() && vSource.hasNext();
        }

        public DualTypePair<U, V> next() {
            return new DualTypePair<U, V>(uSource.next(), vSource.next());
        }
    }
}
