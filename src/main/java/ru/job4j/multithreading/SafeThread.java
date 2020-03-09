package ru.job4j.multithreading;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.list.SimpleArrayList;

import javax.annotation.Nonnull;
import java.util.Iterator;


/**
 * Class SafeThread, a multithreaded collection.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 01.12.2019
 */
@ThreadSafe
public class SafeThread<T> implements Iterable<T> {

    /**
     * A container based on the SimpleArrayList class.
     */
    @GuardedBy("this")
    private final SimpleArrayList<T> list;

    /**
     * A constructor.
     *
     * @param size, a container's size.
     */
    public SafeThread(int size) {
        this.list = new SimpleArrayList<>(size);
    }

    public synchronized int size() {
        return this.list.size();
    }

    /**
     * Adds a value to a container.
     *
     * @param value, any value with a definite type T.
     */
    public synchronized void add(T value) {
        this.list.add(value);
    }

    /**
     * Gets an element from a container.
     *
     * @param index, an element's index.
     * @return an element.
     */
    public synchronized T get(int index) {
        return list.get(index);
    }

    /**
     * @return iterator.
     */
    @Override
    @Nonnull
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

    /**
     * Copies a container.
     *
     * @param list, the list, based on SimpleArrayList class.
     * @return а copy of list.
     */
    private SimpleArrayList<T> copy(SimpleArrayList<T> list) {
        SimpleArrayList<T> duplicate = new SimpleArrayList<>(list.size());
        for (T value : list) {
            duplicate.add(value);
        }
        return duplicate;
    }
}
