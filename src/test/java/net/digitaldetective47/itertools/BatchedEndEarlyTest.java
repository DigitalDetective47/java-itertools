package net.digitaldetective47.itertools;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;

public class BatchedEndEarlyTest {
    @Test
    public void test() {
        BatchedEndEarly<Integer> stream = new BatchedEndEarly<Integer>(Arrays.asList(2, 5, 8, 3, 6, 9, 4, 7, 10), 3);
        Integer[][] expectedOutput = { { 2, 5, 8 }, { 3, 6, 9 }, { 4, 7, 10 } };
        for (Integer[] batch : expectedOutput) {
            assertArrayEquals(batch, stream.next());
        }
    }

    @Test
    public void testIncomplete() {
        BatchedEndEarly<Integer> stream = new BatchedEndEarly<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3);
        Integer[][] expectedOutput = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        for (Integer[] batch : expectedOutput) {
            assertArrayEquals(batch, stream.next());
        }
        assertFalse(stream.hasNext());
    }
}
