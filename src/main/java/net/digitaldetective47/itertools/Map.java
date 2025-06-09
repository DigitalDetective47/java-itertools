package net.digitaldetective47.itertools;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public class Map<T, R> implements Iterator<R> {
    private Iterator<? extends T> source;
    private Function<? super T, ? extends R> func;

    public Map(Function<? super T, ? extends R> func, Iterable<? extends T> source) {
        this.source = source.iterator();
        this.func = Objects.requireNonNull(func);
    }

    public boolean hasNext() {
        return source.hasNext();
    }

    public R next() {
        return func.apply(source.next());
    }

    public void remove() {
        source.remove();
    }
}
