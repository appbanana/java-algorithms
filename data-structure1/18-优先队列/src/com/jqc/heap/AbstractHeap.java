package com.jqc.heap;

import java.util.Comparator;

/**
 * 接口里面的方法都是抽象方法, 抽象类可以不完全实现接口中所有方法,
 * 但是继承与抽象类的子类必须实现接口中剩下的其他方法
 * @author appbanana
 * @date 2019/11/1
 */
public abstract class AbstractHeap<E> implements Heap<E>{

    // 堆长度大小
    protected int size;
    // 比较器
    private Comparator<E> comparator;

    public AbstractHeap() {
        this(null);
    }

    public AbstractHeap(Comparator<E> comparator) {
        size = 0;
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected int compareTo(E e1, E e2) {
        if (comparator != null) return comparator.compare(e1, e2);
       return  ((Comparable<E>) e1).compareTo(e2);
    }
}
