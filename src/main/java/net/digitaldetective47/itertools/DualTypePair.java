package net.digitaldetective47.itertools;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

/**
 * Contains two objects of different types
 */
public class DualTypePair<U, V> implements Cloneable {
    public U u;
    public V v;

    public DualTypePair(U u, V v) {
        this.u = u;
        this.v = v;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DualTypePair) {
            return u.equals(((DualTypePair<?, ?>) obj).u) && v.equals(((DualTypePair<?, ?>) obj).v);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        return 7 * u.hashCode() ^ 11 * v.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getName(), u.toString(), v.toString());
    }

    /**
     * Create a {@link Consumer} which accepts a {@link DualTypePair} and calls the provided {@link BiConsumer} on its elements
     */
    static public <U, V> Consumer<DualTypePair<U, V>> consolidate(BiConsumer<? super U, ? super V> f) {
        return pair -> f.accept(pair.u, pair.v);
    }

    /**
     * Create a {@link Function} which accepts a {@link DualTypePair} and calls the provided {@link BiFunction} on its elements
     */
    static public <U, V, T> Function<DualTypePair<U, V>, T> consolidate(BiFunction<? super U, ? super V, ? extends T> f) {
        return pair -> f.apply(pair.u, pair.v);
    }

    /**
     * Create a {@link Predicate} which accepts a {@link DualTypePair} and calls the provided {@link BiPredicate} on its elements
     */
    static public <U, V> Predicate<DualTypePair<U, V>> consolidate(BiPredicate<? super U, ? super V> f) {
        return pair -> f.test(pair.u, pair.v);
    }

    /**
     * Create a {@link ToDoubleFunction} which accepts a {@link DualTypePair} and calls the provided {@link ToDoubleBiFunction} on its elements
     */
    static public <U, V> ToDoubleFunction<DualTypePair<U, V>> consolidate(ToDoubleBiFunction<? super U, ? super V> f) {
        return pair -> f.applyAsDouble(pair.u, pair.v);
    }

    /**
     * Create a {@link ToIntFunction} which accepts a {@link DualTypePair} and calls the provided {@link ToDoubleBiFunction} on its elements
     */
    static public <U, V> ToIntFunction<DualTypePair<U, V>> consolidate(ToIntBiFunction<? super U, ? super V> f) {
        return pair -> f.applyAsInt(pair.u, pair.v);
    }

    /**
     * Create a {@link ToLongFunction} which accepts a {@link DualTypePair} and calls the provided {@link ToLongBiFunction} on its elements
     */
    static public <U, V> ToLongFunction<DualTypePair<U, V>> consolidate(ToLongBiFunction<? super U, ? super V> f) {
        return pair -> f.applyAsLong(pair.u, pair.v);
    }
}
