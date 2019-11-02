package com.jqc.heap;

import com.jqc.printer.BinaryTreeInfo;
import java.util.Comparator;

/**
 * 大顶堆
 * @author appbanana
 * @date 2019/11/1
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

    public static final int DEFAULT_CAPACITY = 10;
    private E[] elements;

    public BinaryHeap() {
        this(null, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this(null, comparator);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(E[] array, Comparator<E> comparator) {
        super(comparator);
        if (array == null) {
            elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else {
            size = array.length;
            int capacity = Math.max(DEFAULT_CAPACITY, array.length);
            elements = (E[]) new Object[capacity];
            for (int i = 0; i < array.length; i++) {
                elements[i] = array[i];
            }
            heapify();
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementsNotNullCheck(element);
        elements[size++] = element;
        // 上滤数组中的最后一个元素
        siftUp(size - 1);

    }

    /**
     * 获取堆顶元素
     * @return
     */
    @Override
    public E get() {
        return elements[0];
    }

    @Override
    public E remove() {
        E oldValue = elements[0];
        elements[0] = elements[--size];
        siftDown(0);
        return oldValue;
    }

    /**
     * 删除堆顶元素 同时在插入一个元素
     * @param element 插入元素
     * @return
     */
    @Override
    public E replace(E element) {
        elementsNotNullCheck(element);
        E root = null;
        if (size == 0) {
            elements[size++] = element;
        }else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
    }

    /**
     * 元素是否为空校验
     * @param element
     */
    private void elementsNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must be not null");
        }
    }

    private void heapify() {

        // 自上而下的上滤
        for (int i = 0; i < size; i++) {
            siftUp(i);
        }

        // 在下而上的 下滤
        int half = size >> 1;
        // 因为二叉树有一半的是叶子节点, half - 1是非叶子节点倒数第一个
        for (int i = half - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 上滤操作
     * @param index
     */
    private void siftUp(int index) {
        E element = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            // 如果父节点比当前元素大,则停止搜索
            if (compareTo(parent, element) >= 0) break;

            // 能走到这里,说明父元素比当前元素小,则让当前元素上溢
            elements[index] = parent;
            index = parentIndex;
        }
        elements[index] = element;
    }


    /**
     * 下滤操作
     * @param index
     */
    private void siftDown(int index) {
        int half = (size >> 1);
        E element = elements[index];
        while (index < half) {
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];
            if ((childIndex + 1) < size && compareTo(elements[childIndex + 1], child) > 0) {
                // 如果右子节点比左子节点大, 就替换掉
                childIndex = childIndex + 1;
                child = elements[childIndex];
            }
            if (compareTo(element, child) >= 0) break;

            elements[index] = child;
            index = childIndex;
        }

        elements[index] = element;

    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer) node;
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        Integer index = (Integer) node;
        return elements[index];
    }

}
