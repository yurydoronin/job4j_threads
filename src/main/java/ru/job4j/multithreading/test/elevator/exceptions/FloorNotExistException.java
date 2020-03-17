package ru.job4j.multithreading.test.elevator.exceptions;

/**
 * Class OutOfFloorRangeException.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 24.03.2019
 */
public class FloorNotExistException extends RuntimeException {

    /**
     * A default constructor.
     */
    public FloorNotExistException() {

    }

    /**
     * A constructor.
     *
     * @param msg, a message.
     */
    public FloorNotExistException(String msg) {
        super(msg);
    }
}