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
        for (int i = 0 ; i <= total; i++) {
            count++;
        }
        notifyAll();
    }

    public synchronized void await() {
        while (count >= total) {
            System.out.println("Нить выполняет работу");
        }
            try {
                wait();
            } catch (InterruptedException i) {
                Thread.currentThread().interrupt();
            }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier count = new CountBarrier(5);
        Thread first = new Thread(
                () -> {
                    count.await();
                    System.out.println("Блокировка снята");
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println("Начато увеличение счетчика...");
                    count.count();
                    System.out.println("Счетчик увеличек.");
                }
        );
        first.start();
        second.start();
    }
}
