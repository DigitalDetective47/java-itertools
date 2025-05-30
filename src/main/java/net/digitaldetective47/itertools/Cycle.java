package net.digitaldetective47.itertools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Repeat the contents of an iterable, going back to the beginning once the
 * iterable is exhausted
 */
public class Cycle<T> implements Iterator<T> {
    private Iterator<? extends T> source;
    private ArrayList<T> saved;
    private int savedPos;

    public Cycle(Iterable<? extends T> source) {
        this.source = source.iterator();
        if (source instanceof Collection) {
            saved = new ArrayList<T>(((Collection<T>) source).size());
        } else {
            saved = new ArrayList<T>();
        }
        savedPos = 0;
    }

    public boolean hasNext() {
        return true;
    }

    public T next() {
        if (source != null) {
            if (source.hasNext()) {
                T ret = source.next();
                saved.add(ret);
                return ret;
            }
            source = null;
            saved.trimToSize();
        }
        T ret = saved.get(savedPos++);
        if (savedPos >= saved.size()) {
            savedPos = 0;
        }
        return ret;
    }
}
