package net.digitaldetective47.itertools;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;

public class ZipShortestTest {
    @Test
    public void test() {
        Integer[] evens = { 10, 8, 6, 4, 2 };
        Integer[] odds = { 1, 3, 5, 7, 9 };
        ZipShortest<Integer> stream = new ZipShortest<Integer>(Arrays.asList(evens), Arrays.asList(odds));
        Integer[][] zipPairs = { { 10, 1 }, { 8, 3 }, { 6, 5 }, { 4, 7 }, { 2, 9 } };
        for (int i = 0; i < zipPairs.length; i++) {
            assertArrayEquals(zipPairs[i], stream.next());
        }
    }

    @Test
    public void testDifferentSizes() {
        Integer[] primes = { 2, 3, 5, 7, 11, 13, 17, 19 };
        Integer[] smalls = { 1, 2, 3, 4, 5 };
        ZipShortest<Integer> stream = new ZipShortest<Integer>(Arrays.asList(primes), Arrays.asList(smalls));
        Integer[][] zipPairs = { { 2, 1 }, { 3, 2 }, { 5, 3 }, { 7, 4 }, { 11, 5 } };
        for (int i = 0; i < zipPairs.length; i++) {
            assertArrayEquals(zipPairs[i], stream.next());
        }
        assertFalse(stream.hasNext());
    }
}
