package ru.job4j.multithreading;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class SimpleBlockingQueue.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 04.12.2019
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    /**
     * A queue max size.
     */
    private final int maxSize;

    /**
     * A buffer, based on a queue.
     */
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    /**
     * A default constructor.
     */
    public SimpleBlockingQueue() {
        this.maxSize = Integer.MAX_VALUE;
    }

    /**
     * A constructor.
     *
     * @param bound, a set up queue size.
     */
    public SimpleBlockingQueue(int bound) {
        this.maxSize = bound;
    }

    /**
     * Adds objects (tasks) into the queue.
     *
     * @param value, a value should be put into the queue.
     * @throws InterruptedException, .
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (this.queue.size() == maxSize) {
            wait();
        }
        this.queue.offer(value);
        notify();
    }

    /**
     * Retrieves and returns the value from the queue.
     *
     * @return a value.
     * @throws InterruptedException, .
     */
    public synchronized T poll() throws InterruptedException {
        while (this.queue.isEmpty()) {
            wait();
        }
        T result = this.queue.poll();
        notify();
        return result;
    }

    /**
     * Gets the number of items in the queue.
     *
     * @return number of items in the queue.
     */
    public synchronized int size() {
        return queue.size();
    }
}
