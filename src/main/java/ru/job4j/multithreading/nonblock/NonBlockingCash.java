package ru.job4j.multithreading.nonblock;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class Cache.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 09.12.2019
 */
public class NonBlockingCash {

    /**
     *
     */
    private ConcurrentHashMap<Integer, Base> store = new ConcurrentHashMap<>();

    /**
     *
     * @param model, .
     * @return .
     */
    public boolean add(Base model) {
        return this.store.putIfAbsent(model.getId(), model) == null;
    }

    /**
     *
     * @param model, .
     * @return .
     */
    public boolean update(Base model) {
        return store.computeIfPresent(model.getId(), (k, v) -> {
            if (model.getVersion() != this.store.get(k).getVersion()) {
                throw new OptimisticException("Concurrent modification of a model version.");
            }
            model.setVersion(model.getVersion() + 1);
            return model;
        }) != null;
    }

    /**
     *
     * @param model, .
     * @return .
     */
    public boolean delete(Base model) {
        if (model.getVersion() != this.store.get(model.getId()).getVersion()) {
            throw new OptimisticException("Concurrent modification of a model version.");
        }
        return store.remove(model.getId()) != null;
    }

    /**
     *
     * @param key, .
     * @return .
     */
    public Base get(int key) {
        return store.get(key);
    }
}
