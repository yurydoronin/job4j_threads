package ru.job4j.multithreading.nonblock;

/**
 * Class Base.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 09.12.2019
 */
public class Base {

    private int id;

    private volatile int version;

    private String name;

    public Base(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return id == base.id;
    }

    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(this.id);
    }

    @Override
    public String toString() {
        return String.format("model: %s, %s, %s", this.id, this.name, this.version);
    }
}
