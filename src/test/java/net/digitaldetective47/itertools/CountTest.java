package net.digitaldetective47.itertools;

import static org.junit.Assert.assertEquals;

import java.util.PrimitiveIterator;

import org.junit.Test;

public class CountTest {
    @Test
    public void testDefault() {
        PrimitiveIterator.OfLong stream = new Count();
        assertEquals(0, stream.nextLong());
        assertEquals(1, stream.nextLong());
        assertEquals(2, stream.nextLong());
    }

    @Test
    public void testCustom() {
        PrimitiveIterator.OfLong stream = new Count(12, -3);
        assertEquals(12, stream.nextLong());
        assertEquals(9, stream.nextLong());
        assertEquals(6, stream.nextLong());
    }
}
