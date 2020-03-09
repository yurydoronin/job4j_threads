package ru.job4j.multithreading.storage;

import java.util.Objects;

/**
 * Class User.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 30.11.2019
 */
public class User {

    /**
     * A user's id.
     */
    private int id;

    /**
     * A user's amount of money.
     */
    private int amount;

    /**
     * A constructor.
     * @param id, .
     * @param amount, .
     */
    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     *
     * @return .
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return .
     */
    public int getAmount() {
        return amount;
    }

    /**
     *
     * @param amount, .
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(this.id); // This way is faster and better than Objects.hash(id).
    }
}
