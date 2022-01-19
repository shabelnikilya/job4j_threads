package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification(ExecutorService pool) {
        this.pool = pool;
    }

    public void emailTo(User user) {
        pool.submit(() -> send(
                String.format("Notification %s to email %s", user.getUsername(), user.getEmail()),
                String.format("Add a new event to %s", user.getUsername()),
                user.getEmail()
        ));
    }

    public void close() {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(1, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
        }
    }

    public void send(String subject, String body, String email) {
    }
}
