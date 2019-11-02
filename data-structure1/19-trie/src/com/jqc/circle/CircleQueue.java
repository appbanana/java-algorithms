package com.jqc.circle;

import com.jqc.list.LinkList;

/**
 * 循环队列  使用数组来实现
 * 特点 先进先出
 * @author appbanana
 * @date 2019/10/17
 */
public class CircleQueue<E> {

    // 数组容器
    private E[] elements;
    // 数组开始存储的位置
    private int front = 0;
    // 循环队列的长度
    private int size = 0;

    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
    }
    size = 0;
        front = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void enQueue(E element) {
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    public E deQueue() {
        E frontElement = elements[front];
        elements[front] = null;
        // (front + 1) % elements.length
        front = index(1);
        size--;
        return frontElement;
    }

    public E front() {
        return elements[front];
    }

    /**
     * 由下表转化为真实的索引
     * @param index
     * @return
     */
    private int index(int index) {
        return (front + index) % elements.length;
    }

    /**
     * 扩容
     * @param capacity 待检验的容量大小
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (capacity <= oldCapacity) return;

        // 容量扩大为原来的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
//        int index = 0;
//        for (int i = front; i < front + size; i++) {
//            newElements[index++] = elements[index(i - front)];
//        }
        // 上面的方法也行, 这个方法也可以
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        // 重置front
        front = 0;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size = ").append(size).append(", ");
        string.append("front = ").append(front);
        string.append(", [");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(elements[i]);
        }
        string.append("]");
        return string.toString();
    }
}
