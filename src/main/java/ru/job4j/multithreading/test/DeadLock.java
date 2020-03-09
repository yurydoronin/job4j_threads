package ru.job4j.multithreading.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Class DeadLock.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 2.0
 * @since 14.02.2020
 */
public class DeadLock {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DeadLock.class);

    private final Object a = new Object();
    private final Object b = new Object();
    private CountDownLatch cdl = new CountDownLatch(2);

    public void deadlock() {
        new Thread(() -> {
            synchronized (a) {
                cdl.countDown();
                try {
                    this.cdl.await();
                    System.out.println("A is waiting of B");
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
                synchronized (b) {
                    cdl.countDown();
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (b) {
                cdl.countDown();
                try {
                    this.cdl.await();
                    System.out.println("B is waiting of A");
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
                synchronized (a) {
                    cdl.countDown();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new DeadLock().deadlock();
    }
}
