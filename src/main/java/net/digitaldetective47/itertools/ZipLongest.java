package net.digitaldetective47.itertools;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Interleave elements from multiple iterables
 * Uses a fill value if an iterable is exhausted early
 */
public class ZipLongest<T> implements Iterator<T[]> {
    private Iterator<? extends T>[] sources;
    private Supplier<? extends T> fill;

    public ZipLongest(Supplier<? extends T> fill, Iterable<? extends T>... sources) {
        this.sources = (Iterator<T>[]) new Iterator[sources.length];
        Arrays.parallelSetAll(this.sources, i -> Objects.requireNonNull(sources[i]).iterator());
        this.fill = Objects.requireNonNull(fill);
    }

    /**
     * Uses an optional to make an unambiguous constructor
     * 
     * @see java.util.Optional
     */
    public ZipLongest(Optional<? extends T> fillValue, Iterable<? extends T>... sources) {
        this(() -> fillValue.orElse(null), sources);
    }

    public boolean hasNext() {
        return Arrays.stream(sources).parallel().unordered().anyMatch(source -> source.hasNext());
    }

    public T[] next() {
        T[] ret = (T[]) new Object[sources.length];
        Arrays.parallelSetAll(ret, i -> sources[i].hasNext() ? sources[i].next() : fill.get());
        return ret;
    }

    /**
     * Type-safe version for two iterables of different types
     */
    static public class DualType<U, V> implements Iterator<DualTypePair<U, V>> {
        private Iterator<? extends U> uSource;
        private Supplier<? extends U> uFill;
        private Iterator<? extends V> vSource;
        private Supplier<? extends V> vFill;

        public DualType(Supplier<? extends U> uFill, Supplier<? extends V> vFill,
                Iterable<? extends U> uSource, Iterable<? extends V> vSource) {
            this.uFill = uFill;
            this.vFill = vFill;
            this.uSource = uSource.iterator();
            this.vSource = vSource.iterator();
        }

        /**
         * Uses an optional to make an unambiguous constructor
         * 
         * @see java.util.Optional
         */
        public DualType(Optional<? extends U> uFill, Optional<? extends V> vFill,
                Iterable<? extends U> uSource, Iterable<? extends V> vSource) {
            this(() -> uFill.orElse(null), () -> vFill.orElse(null), uSource, vSource);
        }

        public boolean hasNext() {
            return uSource.hasNext() || vSource.hasNext();
        }

        public DualTypePair<U, V> next() {
            return new DualTypePair<U, V>(uSource.hasNext() ? uSource.next() : uFill.get(),
                    vSource.hasNext() ? vSource.next() : vFill.get());
        }

        public void forEachRemaining(BiConsumer<? super U, ? super V> action) {
            while (hasNext()) {
                action.accept(uSource.hasNext() ? uSource.next() : uFill.get(),
                        vSource.hasNext() ? vSource.next() : vFill.get());
            }
        }
    }
}
