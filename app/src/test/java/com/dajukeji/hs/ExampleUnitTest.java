package com.dajukeji.hs;

import org.junit.Test;

import kotlin.random.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        for (int i = 0; i < 10; i++) {
            int s = Random.Default.nextInt(2);
            System.out.println(s);
        }
//        assertEquals(4, 2 + 2);
    }
}