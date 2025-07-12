package net.digitaldetective47.itertools;

import java.util.Arrays;
import java.util.Iterator;

public class BatchedWithIncomplete<T> implements Iterator<T[]> {
    private Iterator<? extends T> source;
    private int batchSize;

    public BatchedWithIncomplete(Iterable<? extends T> source, int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be positive");
        }
        this.source = source.iterator();
        this.batchSize = batchSize;
    }

    public boolean hasNext() {
        return source.hasNext();
    }

    public T[] next() {
        T[] ret = (T[]) new Object[batchSize];
        for (int i = 0; i < batchSize; i++) {
            if (source.hasNext()) {
                ret[i] = source.next();
            } else {
                T[] trunc = (T[]) new Object[i];
                Arrays.parallelSetAll(trunc, j -> ret[j]);
                return trunc;
            }
        }
        return ret;
    }
}
