package net.digitaldetective47.itertools;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

public class ZipLongestTest {
    @Test
    public void test() {
        ZipLongest<Integer> stream = new ZipLongest<Integer>(Optional.of(-1),
                Arrays.asList(10, 8, 6, 4, 2), Arrays.asList(1, 3, 5, 7, 9));
        Integer[][] zipPairs = { { 10, 1 }, { 8, 3 }, { 6, 5 }, { 4, 7 }, { 2, 9 } };
        for (Integer[] pair : zipPairs) {
            assertArrayEquals(pair, stream.next());
        }
    }

    @Test
    public void testDifferentSizes() {
        ZipLongest<Integer> stream = new ZipLongest<Integer>(Optional.of(-1),
                Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19), Arrays.asList(1, 2, 3, 4, 5));
        Integer[][] zipPairs = { { 2, 1 }, { 3, 2 }, { 5, 3 }, { 7, 4 }, { 11, 5 }, { 13, -1 }, { 17, -1 },
                { 19, -1 } };
        for (Integer[] pair : zipPairs) {
            assertArrayEquals(pair, stream.next());
        }
        assertFalse(stream.hasNext());
    }
}
