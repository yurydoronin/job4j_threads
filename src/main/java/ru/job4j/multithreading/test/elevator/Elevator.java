package ru.job4j.multithreading.test.elevator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Class Elevator.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 12.02.2020
 */
@ThreadSafe
public class Elevator extends RecursiveAction {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Elevator.class);

    /**
     * A current floor.
     */
    private int currentFloor;

    /**
     * Total floors.
     */
    private final int maxFloor;

    /**
     * A floor's height.
     */
    private final int height;

    /**
     * An elevator's speed.
     */
    private final int speed;

    /**
     * The time between opening and closing of elevator's doors.
     */
    private final int stopTime;

    /**
     * A flag, allowing to elevator move.
     */
    private boolean allowToMove;

    /**
     * A request queue.
     */
    private PriorityBlockingQueue<Integer> calls;

    public Elevator(int maxFloor, int height, int speed, int stopTime) {
        this.maxFloor = maxFloor;
        this.height = height;
        this.speed = speed;
        this.stopTime = stopTime;
        this.allowToMove = true;
        this.calls = new UserInput().getCalls();
    }

    /**
     *
     */
    private void move(int targetFloor) throws InterruptedException {
        if (targetFloor == currentFloor) {
            this.doorsOpenClose();
        } else {
            if (targetFloor > currentFloor) {
                this.moveUp(targetFloor);
            } else {
                this.moveDown(targetFloor);
            }
            doorsOpenClose();
            this.currentFloor = targetFloor;
        }
    }

    /**
     *
     * @param targetFloor, .
     * @throws InterruptedException, .
     */
    private void moveUp(int targetFloor) throws InterruptedException {
        for (int index = currentFloor; index <= targetFloor; index++) {
            message(index, targetFloor);
            TimeUnit.MILLISECONDS.sleep(movingTime());
        }
    }

    /**
     *
     * @param targetFloor, .
     * @throws InterruptedException, .
     */
    private void moveDown(int targetFloor) throws InterruptedException {
        for (int index = currentFloor; index >= targetFloor; index--) {
            message(index, targetFloor);
            TimeUnit.MILLISECONDS.sleep(movingTime());
        }
    }

    /**
     *
     */
    private void doorsOpenClose() {
        System.out.println("The doors are opening");
        try {
            TimeUnit.SECONDS.sleep(stopTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error(e.getMessage(), e);
        }
        System.out.println("Careful, the doors are closing.");
    }

    /**
     *
     * @return .
     */
    private int movingTime() {
        return this.height / this.speed;
    }

    /**
     *
     * @param currentFloor, .
     * @param targetFloor,  .
     */
    private void message(int currentFloor, int targetFloor) {
        if (currentFloor != targetFloor) {
            System.out.println(String.format("The elevator drove through the %s floor.", currentFloor));
        } else {
            System.out.println(String.format("The elevator is on the %s floor.", targetFloor));
        }
    }

    /**
     *
     */
    private void init() {
        int firstFloor = 1;
        this.currentFloor = new Random().nextInt((this.maxFloor - firstFloor) + 1);
        do {
            try {
                if (!calls.isEmpty()) {
                    move(calls.take());
                    Thread.sleep(200);
                } else {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.error(e.getMessage(), e);
            }
        } while (allowToMove);
    }

    @Override
    protected void compute() {
        this.init();
    }
}
