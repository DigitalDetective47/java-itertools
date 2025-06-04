package net.digitaldetective47.itertools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class RepeatTest {
    @Test
    public void testFinite() {
        Object element = new Object();
        Repeat<Object> stream = new Repeat<Object>(element, 15);
        for (int i = 0; i < 15; i++) {
            assertSame(element, stream.next());
        }
        assertFalse(stream.hasNext());
    }

    @Test
    public void testInfinite() {
        Object element = new Object();
        Repeat<Object> stream = new Repeat<Object>(element);
        for (int i = 0; i != -1; i++) {
            assertSame(element, stream.next());
        }
    }
}
