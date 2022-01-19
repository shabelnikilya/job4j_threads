package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void addOneBaseAndDeleteOneFalse() {
        Base first = new Base(1, 1);
        Base second = new Base(2, 1);
        Cache cache = new Cache();
        first.setName("first");
        second.setName("second");
        cache.add(first);
        assertFalse(cache.delete(second));
    }

    @Test
    public void addTwoBaseAndDeleteOneTrue() {
        Base first = new Base(1, 1);
        Base second = new Base(2, 1);
        Cache cache = new Cache();
        first.setName("first");
        second.setName("second");
        cache.add(first);
        cache.add(second);
        assertTrue(cache.delete(second));
    }

    @Test
    public void updateWithNotFindBaseInCache() {
        Base first = new Base(1, 1);
        Base second = new Base(2, 1);
        Cache cache = new Cache();
        first.setName("first");
        second.setName("second");
        cache.add(first);
        assertFalse(cache.update(second));
    }

    @Test
    public void updateWithCorrectVersionAndVersionUpByOne() {
        Base first = new Base(1, 1);
        Base second = new Base(1, 1);
        Cache cache = new Cache();
        first.setName("first");
        second.setName("second");
        cache.add(first);
        assertTrue(cache.update(second));
        assertThat(cache.getBase(1).getName(), is("second"));
        assertThat(cache.getBase(1).getVersion(), is(2));
    }

    @Test(expected = OptimisticException.class)
    public void updateWithIncorrectVersionException() {
        Base first = new Base(1, 1);
        Base second = new Base(1, 2);
        Cache cache = new Cache();
        first.setName("first");
        second.setName("second");
        cache.add(first);
        cache.update(second);
    }
}