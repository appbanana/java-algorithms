package com.jqc.queue;

import com.jqc.heap.BinaryHeap;

import java.util.Comparator;

/**
 * 优先队列
 * @author appbanana
 * @date 2019/11/2
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> heap;


    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<E> comparator) {
        heap = new BinaryHeap<>(comparator);
    }


    public void clear() {
        heap.clear();
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * 入队列
     * @param element
     */
    public void enQueue(E element) {
        heap.add(element);
    }

    /**
     * 出队列
     * @return
     */
    public E deQueue() {
        return heap.remove();
    }

    public E front() {
        return heap.get();
    }



}
