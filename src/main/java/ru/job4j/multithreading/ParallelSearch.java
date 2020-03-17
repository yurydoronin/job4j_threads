package ru.job4j.multithreading;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class ParallelSearch.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 04.12.2019
 */
@ThreadSafe
public class ParallelSearch {

    private static final Logger LOG = LoggerFactory.getLogger(ParallelSearch.class);

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(queue.poll());
                } catch (InterruptedException e) {
                    LOG.info("info message", e);
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();

        final Thread producer = new Thread(() -> {
            for (int index = 0; index < 5; index++) {
                try {
                    queue.offer(index);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    LOG.info("info message", e);
                    Thread.currentThread().interrupt();
                }
            }
            consumer.interrupt();
        });
        producer.start();
    }
}
