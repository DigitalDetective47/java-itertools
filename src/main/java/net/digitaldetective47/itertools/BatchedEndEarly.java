package net.digitaldetective47.itertools;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BatchedEndEarly<T> implements Iterator<T[]> {
    private Iterator<? extends T> source;
    private int batchSize;
    private T[] lookahead;

    public BatchedEndEarly(Iterable<? extends T> source, int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be positive");
        }
        this.source = source.iterator();
        this.batchSize = batchSize;
        lookahead = null;
    }

    public boolean hasNext() {
        if (lookahead == null) {
            lookahead = (T[]) new Object[batchSize];
            for (int i = 0; i < batchSize; i++) {
                if (source.hasNext()) {
                    lookahead[i] = source.next();
                } else {
                    lookahead = null;
                    return false;
                }
            }
        }
        return true;
    }

    public T[] next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T[] ret = lookahead;
        lookahead = null;
        return ret;
    }
}
