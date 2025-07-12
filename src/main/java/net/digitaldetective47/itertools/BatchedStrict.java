package net.digitaldetective47.itertools;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BatchedStrict<T> implements Iterator<T[]> {
    private Iterator<? extends T> source;
    private int batchSize;
    private T[] lookahead;

    public BatchedStrict(Iterable<? extends T> source, int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be positive");
        }
        this.source = source.iterator();
        this.batchSize = batchSize;
        lookahead = null;
    }

    public boolean hasNext() throws IllegalStateException {
        if (!source.hasNext()) {
            return false;
        }
        if (lookahead == null) {
            lookahead = (T[]) new Object[batchSize];
            for (int i = 0; i < batchSize; i++) {
                if (source.hasNext()) {
                    lookahead[i] = source.next();
                } else {
                    throw new IllegalStateException("BatchedStrict recieved incomplete batch");
                }
            }
        }
        return true;
    }

    public T[] next() throws IllegalStateException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T[] ret = lookahead;
        lookahead = null;
        return ret;
    }
}
