package ru.job4j.cas;

import org.junit.Test;
import ru.job4j.concurrent.Count;
import ru.job4j.concurrent.CountTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    private class ThreadCount extends Thread {
        private final CASCount count;

        public ThreadCount(CASCount count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final CASCount count = new CASCount();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));
    }
}
