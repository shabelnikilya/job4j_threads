package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int numb;
        do {
            numb = get();
        } while (!count.compareAndSet(numb, ++numb));
    }

    public int get() {
        return count.get();
    }
}
