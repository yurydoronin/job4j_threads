package ru.job4j.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class ThreadPool.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.12.2019
 */
public class ThreadPool {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPool.class);

    /**
     * A quantity of threads.
     */
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * A threads' pool.
     */
    private final List<Thread> pool = new LinkedList<>();

    /**
     * A storage of tasks.
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    /**
     * A flag if the pool is running or not.
     */
    private volatile boolean isRunning = true;

    /**
     * Adds tasks (Runnable) into the queue.
     *
     * @param job, Runnable task.
     */
    public void work(Runnable job) throws InterruptedException {
        this.tasks.offer(job);
    }

    /**
     * Starts running the tasks.
     */
    public void start() {
        while (this.isRunning) {
            IntStream.range(0, SIZE)
                    .forEach(i -> pool.add(new Thread(() -> {
                        try {
                            tasks.poll().run();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            LOG.error(e.getMessage(), e);
                        }
                    })));
            pool.forEach(Thread::start);

        }
    }

    /**
     * Closes the pool.
     */
    public boolean shutdown() {
        this.pool.forEach(Thread::interrupt);
        return !isRunning;
    }
}
