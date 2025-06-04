package net.digitaldetective47.itertools;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class AccumulateTest {
    @Test
    public void testInt() {
        Accumulate<Integer, Integer> stream = Accumulate.noStartValue(Arrays.asList(1, 2, 3, 4, 5), (a, b) -> a + b);
        Integer[] expectedOutput = { 3, 6, 10, 15 };
        for (Integer integer : expectedOutput) {
            assertEquals(integer, stream.next());
        }
    }

    @Test
    public void testAsymmetric() {
        Accumulate<Integer, String> stream = new Accumulate<Integer, String>("", Arrays.asList(1, 2, 2, 1), (s, i) -> {
            StringBuilder ret = new StringBuilder(s);
            for (; i > 0; i--) {
                ret.append('W');
            }
            return ret.toString();
        });
        String[] expectedOutput = { "W", "WWW", "WWWWW", "WWWWWW" };
        for (String string : expectedOutput) {
            assertEquals(string, stream.next());
        }
    }
}
