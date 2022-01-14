package ru.job4j.thread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {
    private final int total;
    @GuardedBy("this")
    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        notifyAll();
    }

    public synchronized void await() {
        while (count < total) {
            try {
                wait();
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Нить выполняет работу");
    }
}
