package net.digitaldetective47.itertools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

public class EnumerateTest {
    @Test
    public void testDefaultStart() {
        Object[] objects = new Object[5];
        Arrays.parallelSetAll(objects, i -> new Object());
        Enumerate<Object> stream = new Enumerate<Object>(Arrays.asList(objects));
        for (int i = 0; i < objects.length; i++) {
            Map.Entry<Integer, Object> entry = stream.next();
            assertEquals(i, entry.getKey().intValue());
            assertSame(objects[i], entry.getValue());
        }
    }

    @Test
    public void testCustomStart() {
        Enumerate<Object> stream = new Enumerate<Object>(Collections.singleton(null), -3);
        assertEquals(-3, stream.next().getKey().intValue());
    }
}
