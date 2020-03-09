package ru.job4j.multithreading;

import org.junit.Test;
import org.junit.Before;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 01.12.2019
 */
public class SafeThreadTest {

    private SafeThread<String> list;

    @Before
    public void beforeTest() throws InterruptedException {
        this.list = new SafeThread<>(5);
        Thread thread1 = new Thread(
                () -> {
                    this.list.add("1");
                    this.list.add("2");
                    this.list.add("3");
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    this.list.add("4");
                    this.list.add("5");
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }

    /**
     * Test add and get.
     */
    @Test
    public void whenNewElementThenAddToList() throws InterruptedException {
        Thread thread3 = new Thread(() -> this.list.add("88"));
        thread3.start();
        thread3.join();
        assertThat(list.get(5), is("88"));
        assertThat(list.size(), is(10));

    }

    /**
     * Test iterator.
     */
    @Test
    public void whenHasNextThenTrue() {
        Iterator<String> it = this.list.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("1"));
    }

    /**
     * Test NoSuchElementException.
     */
    @Test(expected = NoSuchElementException.class)
    public void whenNextThenThrowException() {
        Iterator<String> it = this.list.iterator();
        assertThat(it.next(), is("1"));
        assertThat(it.next(), is("2"));
        assertThat(it.next(), is("3"));
        assertThat(it.next(), is("4"));
        assertThat(it.next(), is("5"));
        it.next();
    }
}