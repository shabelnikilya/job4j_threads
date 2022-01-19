package ru.job4j.pool;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.synch.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class ThreadPool {
    private final List<Thread> threads;
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool(int size) {
        this.threads = createNewThreadsAddToListAndStart(size);
    }

    public List<Thread> createNewThreadsAddToListAndStart(int size) {
        List<Thread> pool = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        tasks.poll().run();
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
            );
            pool.add(thread);
            thread.start();
        }
        return pool;
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
