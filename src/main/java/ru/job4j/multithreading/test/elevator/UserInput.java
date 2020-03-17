package ru.job4j.multithreading.test.elevator;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.multithreading.test.elevator.exceptions.FloorNotExistException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RecursiveAction;

/**
 * Class UserInput.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 12.02.2020
 */
@ThreadSafe
public class UserInput extends RecursiveAction {

    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserInput.class);

    /**
     * Поток ввода.
     */
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Очередь заявок.
     */
    private final PriorityBlockingQueue<Integer> calls = new PriorityBlockingQueue<>();

    public UserInput() {
    }

    @Override
    protected void compute() {
        boolean flag = true;
        do {
            System.out.println("1 - A call from the elevator");
            System.out.println("2 - A call from the floor");
            System.out.println("0 - Exit app");
            int answer = ask("Choose a call from: ");
            if (answer == 0) {
                flag = false;
            } else {
                answer = ask("Choose a floor: ");
                calls.put(answer);
            }
        } while (flag);
    }

    public PriorityBlockingQueue<Integer> getCalls() {
        return calls;
    }

    /**
     * A user's request.
     *
     * @param s, request.
     * @return the answer.
     */
    private int ask(String s) {
        List<Integer> range = List.of(0, 1, 2);
        boolean invalid = true;
        boolean found = false;
        int level = -1;
        System.out.println(s);
        do {
            try {
                level = Integer.parseInt(reader.readLine());
                for (int number : range) {
                    if (level == number) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new FloorNotExistException("the key is not found");
                }
                invalid = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            } catch (FloorNotExistException e) {
                System.out.println("Please enter the correct floor");
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        } while (!invalid);
        return level;
    }
}
