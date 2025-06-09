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
    private boolean[] lastFromSource;
    private boolean removeAllowed;

    public ZipLongest(Supplier<? extends T> fill, Iterable<? extends T>... sources) {
        this.sources = (Iterator<T>[]) new Iterator[sources.length];
        Arrays.parallelSetAll(this.sources, i -> sources[i].iterator());
        this.fill = Objects.requireNonNull(fill);
        removeAllowed = Arrays.stream(this.sources).parallel().unordered().anyMatch(source -> {
            try {
                source.remove();
            } catch (UnsupportedOperationException e) {
                return false;
            } catch (IllegalStateException e) {
                return true;
            }
            throw new IllegalStateException();
        });
        lastFromSource = new boolean[sources.length];
        Arrays.fill(lastFromSource, false);
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
        for (int i = 0; i < lastFromSource.length; i++) {
            lastFromSource[i] = sources[i].hasNext();
            ret[i] = lastFromSource[i] ? sources[i].next() : fill.get();
        }
        return ret;
    }

    public void remove() {
        if (!removeAllowed) {
            throw new UnsupportedOperationException();
        }
        for (int i = 0; i < sources.length; i++) {
            if (lastFromSource[i]) {
                sources[i].remove();
            }
        }
    }

    /**
     * Type-safe version for two iterables of different types
     */
    static public class DualType<U, V> implements Iterator<DualTypePair<U, V>> {
        private Iterator<? extends U> uSource;
        private Supplier<? extends U> uFill;
        private boolean uLastFromSource;
        private Iterator<? extends V> vSource;
        private Supplier<? extends V> vFill;
        private boolean vLastFromSource;
        private boolean removeAllowed;

        public DualType(Supplier<? extends U> uFill, Supplier<? extends V> vFill,
                Iterable<? extends U> uSource, Iterable<? extends V> vSource) {
            this.uFill = uFill;
            this.vFill = vFill;
            this.uSource = uSource.iterator();
            this.vSource = vSource.iterator();
            try {
                this.uSource.remove();
            } catch (UnsupportedOperationException e) {
                removeAllowed = false;
                return;
            } catch (IllegalStateException e) {
            }
            try {
                this.vSource.remove();
            } catch (UnsupportedOperationException e) {
                removeAllowed = false;
                return;
            } catch (IllegalStateException e) {
            }
            removeAllowed = true;
            uLastFromSource = false;
            vLastFromSource = false;
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
            uLastFromSource = uSource.hasNext();
            vLastFromSource = vSource.hasNext();
            return new DualTypePair<U, V>(uSource.hasNext() ? uSource.next() : uFill.get(),
                    vSource.hasNext() ? vSource.next() : vFill.get());
        }

        public void forEachRemaining(BiConsumer<? super U, ? super V> action) {
            while (hasNext()) {
                uLastFromSource = uSource.hasNext();
                vLastFromSource = vSource.hasNext();
                action.accept(uSource.hasNext() ? uSource.next() : uFill.get(),
                        vSource.hasNext() ? vSource.next() : vFill.get());
            }
        }

        public void remove() {
            if (!removeAllowed) {
                throw new UnsupportedOperationException();
            }
            if (!(uLastFromSource || vLastFromSource)) {
                throw new IllegalStateException();
            }
            if (uLastFromSource) {
                uSource.remove();
                uLastFromSource = false;
            }
            if (vLastFromSource) {
                vSource.remove();
                vLastFromSource = false;
            }
        }
    }
}
