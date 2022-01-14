package ru.job4j.synch;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> in = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(in);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void testChangedCollections() {
        List<Integer> original = new ArrayList<>();
        original.add(1);
        SingleLockList<Integer> singleList = new SingleLockList<>(original);
        singleList.add(3);
        assertThat(singleList.size(), is(2));
        assertThat(original.size(), is(1));
    }

    @Test
    public void testIterator() {
        List<Integer> in = List.of(1, 2);
        SingleLockList<Integer> list = new SingleLockList<>(in);
        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertThat(it.next(), is(1));
        assertTrue(it.hasNext());
        assertThat(it.next(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorFailSafeWithException() {
        List<Integer> in = List.of(1, 2);
        SingleLockList<Integer> list = new SingleLockList<>(in);
        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertThat(it.next(), is(1));
        assertTrue(it.hasNext());
        assertThat(it.next(), is(2));
        list.add(3);
        assertThat(it.next(), is(3));
    }
}