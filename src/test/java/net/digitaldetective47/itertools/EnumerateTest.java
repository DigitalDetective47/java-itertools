package net.digitaldetective47.itertools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class EnumerateTest {
    @Test
    public void testDefaultStart() {
        List<Object> objects = new ArrayList<Object>(5);
        for (int i = 0; i < 5; i++) {
            objects.add(new Object());
        }
        Enumerate<Object> stream = new Enumerate<Object>(objects);
        for (int i = 0; i < objects.size(); i++) {
            Map.Entry<Integer, Object> entry = stream.next();
            assertEquals(i, entry.getKey().intValue());
            assertSame(objects.get(i), entry.getValue());
        }
    }

    @Test
    public void testCustomStart() {
        List<Object> objects = new ArrayList<Object>(5);
        for (int i = 0; i < 5; i++) {
            objects.add(new Object());
        }
        Enumerate<Object> stream = new Enumerate<Object>(objects, 25);
        for (int i = 0; i < objects.size(); i++) {
            Map.Entry<Integer, Object> entry = stream.next();
            assertEquals(i + 25, entry.getKey().intValue());
            assertSame(objects.get(i), entry.getValue());
        }
    }
}
