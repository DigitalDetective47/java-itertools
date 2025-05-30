package net.digitaldetective47.itertools;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Interleave elements from multiple iterables
 * Ends once any of its iterables are exhausted
 */
public class ZipShortest<T> implements Iterator<T[]> {
    private Iterator<? extends T>[] sources;

    public ZipShortest(Iterable<? extends T>... sources) {
        this.sources = (Iterator<? extends T>[]) new Iterator[sources.length];
        Arrays.parallelSetAll(this.sources, i -> Objects.requireNonNull(sources[i]).iterator());
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
        private Iterator<? extends U> uSource;
        private Iterator<? extends V> vSource;

        public DualType(Iterable<? extends U> uSource, Iterable<? extends V> vSource) {
            this.uSource = uSource.iterator();
            this.vSource = vSource.iterator();
        }

        public boolean hasNext() {
            return uSource.hasNext() && vSource.hasNext();
        }

        public DualTypePair<U, V> next() {
            return new DualTypePair<U, V>(uSource.next(), vSource.next());
        }

        public void forEachRemaining(BiConsumer<? super U, ? super V> action) {
            while (hasNext()) {
                action.accept(uSource.next(), vSource.next());
            }
        }
    }
}
