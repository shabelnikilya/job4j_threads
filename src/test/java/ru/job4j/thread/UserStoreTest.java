package ru.job4j.thread;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStoreTest {

    private class ThreadStorage extends Thread {
        private final UserStore storage;
        private final User from;
        private final User to;
        private final int amount;

        public ThreadStorage(UserStore storage, User from, User to, int amount) {
            this.storage = storage;
            this.from = from;
            this.to = to;
            this.amount = amount;
        }

        @Override
        public void run() {
            storage.transfer(from.getId(), to.getId(), amount);
        }
    }

    @Test
    public void transferAmountWhenTrue() {
        User fromUser = new User(1, 200);
        User toUser = new User(2, 150);
        final UserStore store = new UserStore();
        store.add(fromUser);
        store.add(toUser);
        assertTrue(store.transfer(1, 2, 150));
        assertThat(store.getUser(2).getAmount(), is(new User(2, 300).getAmount()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferIllegalAmountUserOne() {
        User fromUser = new User(1, 200);
        User toUser = new User(2, 150);
        final UserStore store = new UserStore();
        store.add(fromUser);
        store.add(toUser);
        store.transfer(1, 2, 201);
    }

    @Test
    public void transferAmountWithThread() throws InterruptedException {
        User fromUser = new User(1, 200);
        User toUser = new User(2, 150);
        final UserStore store = new UserStore();
        store.add(fromUser);
        store.add(toUser);
        Thread first = new ThreadStorage(store, fromUser, toUser, 50);
        Thread second = new ThreadStorage(store, fromUser, toUser, 100);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(store.getUser(fromUser.getId()).getAmount(), is(50));
        assertThat(store.getUser(toUser.getId()).getAmount(), is(300));
    }
}