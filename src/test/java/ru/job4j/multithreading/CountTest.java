package ru.job4j.multithreading;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 30.11.2019
 */
public class CountTest {

    private static class ThreadCount extends Thread {

        /**
         * A count.
         */
        private final Count count;

        /**
         * A constructor.
         *
         * @param count, a Count object.
         */
        private ThreadCount(final Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    /**
     * Test.
     *
     * @throws InterruptedException, .
     */
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));

    }
}