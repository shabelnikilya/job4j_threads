package ru.job4j.pool.fork;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelSearchObjTest {
    private final Integer[] array = {1, 3, 5, 7, 9, 11, 13, 2, 4, 6, 8, 10, 12};

    @Test
    public void whenFindIntegerObject() {
        assertThat(
                ParallelSearchObj.getIndexObject(
                    array,
                    12,
                    0,
                    array.length - 1), is(12));
    }

    @Test
    public void whenNotFindIntegerObjectMinusOne() {
        assertThat(
                ParallelSearchObj.getIndexObject(
                        array,
                        66,
                        0,
                        array.length - 1), is(-1));
    }

    @Test
    public void whenFindIntegerObjectInSmallPartArray() {
        assertThat(
                ParallelSearchObj.getIndexObject(
                        array,
                        3,
                        0,
                        5), is(1));
    }

    @Test
    public void whenFromEqualsToMinusOne() {
        assertThat(
                ParallelSearchObj.getIndexObject(
                        array,
                        3,
                        0,
                        0), is(-1));
    }

    @Test
    public void whenFromEqualsToAndFindObject() {
        assertThat(
                ParallelSearchObj.getIndexObject(
                        array,
                        1,
                        0,
                        0), is(0));
    }
}