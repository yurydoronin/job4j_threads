package ru.job4j.multithreading.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class EmailNotification.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.12.2019
 */
public class EmailNotification {

    /**
     * A quantity of threads.
     */
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * A threads' pool.
     */
    private final ExecutorService pool = Executors.newFixedThreadPool(SIZE);

    /**
     *
     * @param user, .
     */
    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getUsername());
            this.send(subject, body, user.getEmail());
        });
    }

    /**
     *
     * @param subject, .
     * @param body,    .
     * @param email,   .
     */
    public void send(String subject, String body, String email) {

    }

    /**
     * Closes the pool.
     */
    public void close() throws InterruptedException {
        this.pool.shutdown();
        this.pool.awaitTermination(1, TimeUnit.MINUTES);
    }
}
