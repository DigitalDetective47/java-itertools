package net.digitaldetective47.itertools;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Return only elements from an iterable that fulfill a condition
 */
public class Filter<T> implements Iterator<T> {
    private Iterator<? extends T> source;
    private Predicate<? super T> filter;
    // Used to implement hasNext; whether all future elements will be filtered out
    // cannot be determined soley based on the backing iterator
    private boolean hasLookahead;
    private T lookahead;

    public Filter(Predicate<? super T> filter, Iterable<? extends T> source) {
        this.filter = Objects.requireNonNull(filter);
        this.source = source.iterator();
        hasLookahead = false;
    }

    public boolean hasNext() {
        if (hasLookahead) {
            return true;
        }
        while (source.hasNext()) {
            lookahead = source.next();
            hasLookahead = true;
            if (filter.test(lookahead)) {
                return true;
            }
        }
        return false;
    }

    public T next() {
        if (hasLookahead) {
            hasLookahead = false;
            return lookahead;
        }
        T result;
        do {
            result = source.next();
        } while (!filter.test(result));
        return result;
    }

    public void remove(){
        source.remove();
    }
}
