package net.digitaldetective47.itertools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class FilterTest {
    @Test
    public void test() {
        Filter<Integer> stream = new Filter<Integer>(n -> n > 0, Arrays.asList(5, -3, 7, 2, -6, 4));
        assertEquals(5, stream.next().intValue());
        assertTrue(stream.hasNext());
        assertEquals(7, stream.next().intValue());
        assertEquals(2, stream.next().intValue());
        assertEquals(4, stream.next().intValue());
        assertFalse(stream.hasNext());
    }
}
