package net.digitaldetective47.itertools;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class CycleTest {
    @Test
    public void test() {
        Integer[] numbers = { 1, 2, 3, 4, 5 };
        Cycle<Integer> stream = new Cycle<Integer>(Arrays.asList(numbers));
        Integer[] expectedOutput = { 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 };
        for (Integer integer : expectedOutput) {
            assertEquals(integer, stream.next());
        }
    }
}
