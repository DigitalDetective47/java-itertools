package net.digitaldetective47.itertools;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

/**
 * Yields partial sums of an iterable using the provided accumulation function
 */
public class Accumulate<T, A> implements Iterator<A> {
    private A sum;
    private BiFunction<? super A, ? super T, ? extends A> accumulator;
    private Iterator<? extends T> source;

    public Accumulate(A start, Iterable<? extends T> source,
            BiFunction<? super A, ? super T, ? extends A> accumulator) {
        sum = start;
        this.source = source.iterator();
        this.accumulator = Objects.requireNonNull(accumulator);
    }

    static <T> Accumulate<T, T> noStartValue(Iterable<? extends T> source, BinaryOperator<T> accumulator) {
        Accumulate<T, T> ret = new Accumulate<T, T>(null, source, accumulator);
        ret.sum = ret.source.next();
        return ret;
    }

    public boolean hasNext() {
        return source.hasNext();
    }

    public A next() {
        return sum = accumulator.apply(sum, source.next());
    }

    public void remove() {
        source.remove();
    }
}
