package ru.job4j.thread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized User getUser(int id) {
        return users.get(id);
    }

    public synchronized boolean add(User user) {
        boolean resultAdd = !users.containsKey(user.getId());
        users.put(user.getId(), user);
        return resultAdd;
    }

    public synchronized boolean update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("This user is missing. unable to update amount");
        }
        users.put(user.getId(), user);
        return users.get(user.getId()).getAmount() == user.getAmount();
    }

    public synchronized boolean delete(User user) {
        users.remove(user.getId());
        return !users.containsKey(user.getId());
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser.getAmount() < amount) {
            throw new IllegalArgumentException("Insufficient funds, enter a different amount");
        }
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(toUser.getAmount() + amount);
        return update(fromUser) && update(toUser);
    }
}
