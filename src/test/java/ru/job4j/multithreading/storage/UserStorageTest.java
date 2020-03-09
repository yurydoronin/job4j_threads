package ru.job4j.multithreading.storage;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.multithreading.storage.User;
import ru.job4j.multithreading.storage.UserStorage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 30.11.2019
 */
public class UserStorageTest {

    private UserStorage bank = new UserStorage();

    @Before
    public void setUp() {
        bank.add(new User(1, 2000));
        bank.add(new User(2, 1000));
    }

    /**
     * Test add.
     */
    @Test
    public void whenNewUserThenAddUser() {
        assertThat(this.bank.add(new User(3, 5000)), is(true));
        assertThat(this.bank.add(new User(4, 8000)), is(true));
    }

    /**
     * Test transfer when money is not enough.
     */
    @Test
    public void whenMoneyIsNotEnoughThenNoAction() throws InterruptedException {
        Thread thread = new Thread(() -> this.bank.transfer(2, 1, 2000));
        thread.start();
        thread.join();
        assertThat(this.bank.getStorage().get(1).getAmount(), is(2000));
        assertThat(this.bank.getStorage().get(2).getAmount(), is(1000));
    }

    /**
     * Test transfer.
     */
    @Test
    public void whenMoneyIsEnoughThenTransferMoney() throws InterruptedException {
        Thread thread1 = new Thread(() -> this.bank.transfer(2, 1, 500));
        Thread thread2 = new Thread(() -> this.bank.transfer(1, 2, 200));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(this.bank.getStorage().get(1).getAmount(), is(2300));
        assertThat(this.bank.getStorage().get(2).getAmount(), is(700));
        assertThat(this.bank.transfer(1, 2, 200), is(true));
    }

    /**
     * Test update.
     */
    @Test
    public void whenHasUserThenUpdateUser() {
        assertThat(this.bank.update(new User(2, 5000)), is(true));
    }

    /**
     * Test delete.
     */
    @Test
    public void whenHasThenRemoveUser() {
        assertThat(this.bank.delete(new User(1, 2000)), is(true));
    }
}