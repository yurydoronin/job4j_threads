package ru.job4j.multithreading.nonblock;

public class OptimisticException extends RuntimeException {

    public OptimisticException() {
    }

    public OptimisticException(String message) {
        super(message);
    }
}
