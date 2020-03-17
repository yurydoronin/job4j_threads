package ru.job4j.multithreading.test.elevator;

/**
 * Class ElevatorMain
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 12.02.2020
 */
public class ElevatorMain {

    public static void main(String[] args) {
        new Elevator(5, 3, 2, 1).fork();
        new UserInput().fork();
    }
}
