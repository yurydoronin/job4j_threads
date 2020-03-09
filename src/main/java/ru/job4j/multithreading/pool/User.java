package ru.job4j.multithreading.pool;

import java.util.Objects;

/**
 * Class User.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 1.0
 * @since 10.12.2019
 */
public class User {

    private String username;

    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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
        return Objects.equals(username, user.username)
                && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return 31 * (this.username != null ? this.username.hashCode() : 0) + this.email.hashCode();
    }
}
