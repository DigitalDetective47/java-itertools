package net.digitaldetective47.itertools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Interleave elements from multiple iterables
 * Throws a {@link MismatchedLength} if its iterators are exhausted at different
 * times
 */
public class ZipStrict<T> implements Iterator<T[]> {
    private Iterator<? extends T>[] sources;

    static public class MismatchedLength extends Error {
        private static String stringifyIterable(Iterable<?> iterable) {
            StringBuilder ret = new StringBuilder();
            for (Object element : iterable) {
                ret.append(element);
                ret.append(", ");
            }
            return ret.substring(0, ret.length() - 2);
        }

        public MismatchedLength(Iterable<? extends Iterator<?>> exhausted,
                Iterable<? extends Iterator<?>> notExhausted) {
            super(String.format("exhausted iterators: %s; non-exhausted iterators: %s",
                    stringifyIterable(exhausted), stringifyIterable(notExhausted)));
        }
    }

    public ZipStrict(Iterable<? extends T>... sources) {
        this.sources = (Iterator<? extends T>[]) new Iterator[sources.length];
        Arrays.parallelSetAll(this.sources, i -> Objects.requireNonNull(sources[i]).iterator());
    }

    public boolean hasNext() throws MismatchedLength {
        Collection<Iterator<? extends T>> exhausted = new LinkedList<Iterator<? extends T>>();
        Collection<Iterator<? extends T>> notExhausted = new LinkedList<Iterator<? extends T>>();
        Arrays.stream(sources).parallel().forEach(source -> (source.hasNext() ? notExhausted : exhausted).add(source));
        if (exhausted.isEmpty()) {
            return true;
        } else if (notExhausted.isEmpty()) {
            return false;
        } else {
            throw new MismatchedLength(exhausted, notExhausted);
        }
    }

    public T[] next() throws MismatchedLength {
        hasNext();
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

        public boolean hasNext() throws MismatchedLength {
            if (uSource.hasNext()) {
                if (vSource.hasNext()) {
                    return true;
                } else {
                    throw new MismatchedLength(Collections.singleton(uSource), Collections.singleton(vSource));
                }
            } else {
                if (vSource.hasNext()) {
                    throw new MismatchedLength(Collections.singleton(vSource), Collections.singleton(uSource));
                } else {
                    return false;
                }
            }
        }

        public DualTypePair<U, V> next() throws MismatchedLength {
            hasNext();
            return new DualTypePair<U, V>(uSource.next(), vSource.next());
        }

        public void forEachRemaining(BiConsumer<? super U, ? super V> action) throws MismatchedLength {
            while (hasNext()) {
                action.accept(uSource.next(), vSource.next());
            }
        }
    }
}
