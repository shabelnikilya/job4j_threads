package ru.job4j.pool.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchObj<V> extends RecursiveTask<Integer> {
    private final V[] array;
    private final V findValue;
    private final int from;
    private final int to;

    public ParallelSearchObj(V[] array, V findValue, int from, int to) {
        this.array = array;
        this.findValue = findValue;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return getIndexWhenSmallArray();
        }
        int mid = (from + to) / 2;
        ParallelSearchObj<V> leftSearch = new ParallelSearchObj<>(array, findValue, from, mid);
        ParallelSearchObj<V> rightSearch = new ParallelSearchObj<>(array, findValue, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    public int getIndexWhenSmallArray() {
        for (int i = from; i <= to; i++) {
            if (findValue.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public static <V> int getIndexObject(V[] array, V findValue, int from, int to) {
        return new ForkJoinPool().invoke(new ParallelSearchObj<>(array, findValue, from, to));
    }
}
