package net.digitaldetective47.itertools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;

/**
 * Interleave elements from multiple iterables
 * Throws a {@link IllegalStateException} if its iterators are exhausted at different
 * times
 */
public class ZipStrict<T> implements Iterator<T[]> {
    private Iterator<? extends T>[] sources;
    private boolean removeAllowed;

    public ZipStrict(Iterable<? extends T>... sources) {
        this.sources = (Iterator<? extends T>[]) new Iterator[sources.length];
        Arrays.parallelSetAll(this.sources, i -> sources[i].iterator());
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
    }

    public boolean hasNext() throws IllegalStateException {
        Collection<Iterator<? extends T>> exhausted = new LinkedList<Iterator<? extends T>>();
        Collection<Iterator<? extends T>> notExhausted = new LinkedList<Iterator<? extends T>>();
        Arrays.stream(sources).parallel().forEach(source -> (source.hasNext() ? notExhausted : exhausted).add(source));
        if (exhausted.isEmpty()) {
            return true;
        } else if (notExhausted.isEmpty()) {
            return false;
        } else {
            throw new IllegalStateException("ZipStrict Iterables are of different sizes");
        }
    }

    public T[] next() throws IllegalStateException {
        hasNext();
        T[] ret = (T[]) new Object[sources.length];
        Arrays.parallelSetAll(ret, i -> sources[i].next());
        return ret;
    }

    public void remove() {
        if (!removeAllowed) {
            throw new UnsupportedOperationException();
        }
        Arrays.stream(sources).parallel().unordered().forEach(Iterator::remove);
    }

    /**
     * Type-safe version for two iterables of different types
     */
    static public class DualType<U, V> implements Iterator<DualTypePair<U, V>> {
        private Iterator<? extends U> uSource;
        private Iterator<? extends V> vSource;
        private boolean removeAllowed;

        public DualType(Iterable<? extends U> uSource, Iterable<? extends V> vSource) {
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
        }

        public boolean hasNext() throws IllegalStateException {
            if (uSource.hasNext() == vSource.hasNext()) {
                return uSource.hasNext();
            } else{
                throw new IllegalStateException("ZipStrict Iterables are of different sizes");
            }
        }

        public DualTypePair<U, V> next() throws IllegalStateException{
            hasNext();
            return new DualTypePair<U, V>(uSource.next(), vSource.next());
        }

        public void remove() {
            if (!removeAllowed) {
                throw new UnsupportedOperationException();
            }
            uSource.remove();
            vSource.remove();
        }

        public void forEachRemaining(BiConsumer<? super U, ? super V> action) throws IllegalStateException {
            while (hasNext()) {
                action.accept(uSource.next(), vSource.next());
            }
        }
    }
}
