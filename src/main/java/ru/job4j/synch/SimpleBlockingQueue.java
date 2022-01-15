package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private int limit = 5;

    public SimpleBlockingQueue() {
    }

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        while (queue.size() == limit) {
            try {
                wait();
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            }
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                wait();
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            }
        }
        notify();
        return queue.poll();
    }
}
