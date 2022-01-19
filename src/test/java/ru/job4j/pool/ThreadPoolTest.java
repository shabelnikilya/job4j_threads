package ru.job4j.pool;

import org.junit.Test;
import ru.job4j.concurrent.Count;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    private final int size = Runtime.getRuntime().availableProcessors();

    @Test
    public void workAddThreeIncrement() {
        Count count = new Count();
        ThreadPool pool = new ThreadPool(size);
        pool.work(count::increment);
        pool.work(count::increment);
        pool.work(count::increment);
        pool.shutdown();
        assertThat(count.get(), is(3));
    }

    @Test
    public void workAddValueToList() {
        List<String> str = new ArrayList<>();
        ThreadPool pool = new ThreadPool(size);
        pool.work(() -> str.add("test"));
        pool.shutdown();
        assertThat(str.get(0), is("test"));
    }
}