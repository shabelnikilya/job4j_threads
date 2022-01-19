package ru.job4j.pool.fork;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelSearchObjTest {
    private final Integer[] array = {1, 3, 5, 7, 9, 11, 13, 2, 4, 6, 8, 10, 12};

    @Test
    public void whenFindIntegerObject() {
        ParallelSearchObj<Integer> searchObj = new ParallelSearchObj<>(
                array, 12, 0, array.length - 1);
        assertThat(searchObj.getIndexObject(), is(12));
    }

    @Test
    public void whenNotFindIntegerObjectMinusOne() {
        ParallelSearchObj<Integer> searchObj = new ParallelSearchObj<>(
                array, 66, 0, array.length - 1);
        assertThat(searchObj.getIndexObject(), is(-1));
    }

    @Test
    public void whenFindIntegerObjectInSmallPartArray() {
        ParallelSearchObj<Integer> searchObj = new ParallelSearchObj<>(
                array, 3, 0, 5);
        assertThat(searchObj.getIndexObject(), is(1));
    }

    @Test
    public void whenFromEqualsToMinusOne() {
        ParallelSearchObj<Integer> searchObj = new ParallelSearchObj<>(
                array, 3, 0, 0);
        assertThat(searchObj.getIndexObject(), is(-1));
    }

    @Test
    public void whenFromEqualsToAndFindObject() {
        ParallelSearchObj<Integer> searchObj = new ParallelSearchObj<>(
                array, 1, 0, 0);
        assertThat(searchObj.getIndexObject(), is(0));
    }
}