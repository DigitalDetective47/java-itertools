package net.digitaldetective47.itertools;

import static org.junit.Assert.assertArrayEquals;

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
}
