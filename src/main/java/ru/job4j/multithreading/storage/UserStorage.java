package ru.job4j.multithreading.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Class UserStorage.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 30.11.2019
 */
@ThreadSafe
public class UserStorage {

    /**
     *
     */
    @GuardedBy("this")
    private ConcurrentMap<Integer, User> storage = new ConcurrentHashMap<>();

    /***
     * Getter.
     * @return .
     */
    public synchronized ConcurrentMap<Integer, User> getStorage() {
        return this.storage;
    }

    /**
     *
     * @param user, .
     */
    public synchronized boolean add(User user) {
        return this.storage.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized User getUser(int id) {
        return this.storage.get(id);
    }

    /**
     *
     * @param user, .
     * @return .
     */
    public synchronized boolean update(User user) {
        return this.storage.computeIfPresent(user.getId(), (k, v) -> user) != null;
    }

    /**
     *
     * @param user, .
     * @return .
     */
    public synchronized boolean delete(User user) {
        return null != this.storage.remove(user.getId());
    }

    /**
     *
     * @param fromId, .
     * @param toId,   .
     * @param amount, .
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = storage.get(fromId);
        if (userFrom.getAmount() >= amount) {
            User userTo = storage.get(toId);
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            result = true;
        }
        return result;
    }
}
