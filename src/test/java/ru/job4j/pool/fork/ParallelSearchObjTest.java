package ru.job4j.pool.fork;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelSearchObjTest {

    @Test
    public void whenFindIntegerObject() {
        final Integer[] array = {1, 3, 5, 7, 9, 11, 13, 2, 4, 6, 8, 10, 12};
        assertThat(ParallelSearchObj.getIndexObject(array, 12), is(12));
    }

    @Test
    public void whenNotFindIntegerObjectMinusOne() {
        final Integer[] array = {1, 3, 5, 7, 9, 11, 13, 2, 4, 6, 8, 10, 12};
        assertThat(
                ParallelSearchObj.getIndexObject(array, 66), is(-1));
    }

    @Test
    public void whenFromEqualsToMinusOne() {
        Integer[] oneElementArray = {1};
        assertThat(ParallelSearchObj.getIndexObject(oneElementArray, 3), is(-1));
    }

    @Test
    public void whenFromEqualsToAndFindObject() {
        Integer[] oneElementArray = {1};
        assertThat(ParallelSearchObj.getIndexObject(oneElementArray, 1), is(0));
    }
}