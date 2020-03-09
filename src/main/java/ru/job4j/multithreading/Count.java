package ru.job4j.multithreading;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Class Count.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 30.11.2019
 */
@ThreadSafe
public class Count {

    /**
     * A default value, which is equals 0.
     */
    @GuardedBy("this")
    private int value;

    /**
     * Increments a value field.
     */
    public synchronized void increment() {
        this.value++;
    }

    /**
     * Getter.
     * @return value.
     */
    public synchronized int get() {
        return this.value;
    }
}
