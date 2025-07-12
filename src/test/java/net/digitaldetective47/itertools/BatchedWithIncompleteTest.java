package net.digitaldetective47.itertools;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;

public class BatchedWithIncompleteTest {
    @Test
    public void test() {
        BatchedWithIncomplete<Integer> stream = new BatchedWithIncomplete<Integer>(
                Arrays.asList(2, 5, 8, 3, 6, 9, 4, 7, 10), 3);
        Integer[][] expectedOutput = { { 2, 5, 8 }, { 3, 6, 9 }, { 4, 7, 10 } };
        for (Integer[] batch : expectedOutput) {
            assertArrayEquals(batch, stream.next());
        }
    }

    @Test
    public void testIncomplete() {
        BatchedWithIncomplete<Integer> stream = new BatchedWithIncomplete<Integer>(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3);
        Integer[][] expectedOutput = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10 } };
        for (Integer[] batch : expectedOutput) {
            assertArrayEquals(batch, stream.next());
        }
    }
}
