package ru.job4j.multithreading.nonblock;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.BaseStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Test.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 09.12.2019
 */
public class NonBlockingCashTest {

    private NonBlockingCash cash = new NonBlockingCash();

    @Before
    public void beforeTest() {
        cash.add(new Base(1, "Masha"));
        cash.add(new Base(2, "Dasha"));
        cash.add(new Base(3, "Sasha"));
    }

    /**
     * Testing addition of an element, and addition of a duplicate.
     */
    @Test
    public void whenNewModelThenAdd() {
        Base model = new Base(4, "Mike");
        assertThat(cash.add(model), is(true));
        assertThat(cash.add(model), is(false));

    }

    /**
     * Testing update.
     */
    @Test
    public void whenUpdateThenUpdateModel() {
        assertTrue(cash.update(new Base(1, "Petr")));
    }

    /**
     * Testing removing of a model.
     */
    @Test
    public void whenDeleteThenRemoveModel() {
        assertTrue(cash.delete(this.cash.get(3)));
    }
}