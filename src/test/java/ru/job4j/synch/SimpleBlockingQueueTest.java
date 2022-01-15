package ru.job4j.synch;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenProducerTwoAddAndConsumerOnePoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread consumer = new Thread(queue::poll);
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
        });
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll(), is(2));
    }

    @Test
    public void whenQueueOfTwoElementsUseLimit() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread consumer = new Thread(queue::poll);
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll(), is(2));
        assertThat(queue.poll(), is(3));
    }
}