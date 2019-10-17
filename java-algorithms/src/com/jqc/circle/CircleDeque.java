package com.jqc.circle;

/**
 * 循环双端队列 使用数组来实现
 * @author appbanana
 * @date 2019/10/17
 */
public class CircleDeque<E> {

    // 数组容器
    private E[] elements;
    // 数组开始存储的位置
    private int front = 0;
    // 循环队列的长度
    private int size = 0;

    private static final int DEFAULT_CAPACITY = 10;

    public CircleDeque() {
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

    /**
     * 从对头入队列
     * @param element
     */
    public void enQueueFront(E element) {
        ensureCapacity(size + 1);
        front = index(-1);
        elements[front] = element;
        size++;

    }

    /**
     * 从对头出队列
     * @return
     */
    public E deQueueFront() {
        E old = elements[front];
        elements[front] = null;
        // (front + 1) % elements.length
        front = index(1);
        size--;
        return old;
    }

    /**
     * 从队尾入队
     * @param element
     */
    public void enQueueRear(E element) {
        ensureCapacity(size + 1);
        int realIndex = index(size);
        elements[realIndex] = element;
        size++;
    }

    /**
     * 从队尾出队
     * @param element
     */
    public E deQueueRear() {
        int realIndex = index(size - 1);
        E old = elements[realIndex];
        elements[realIndex] = null;
        size--;
        return old;
    }

    /**
     * 获取队头元素
     * @return
     */
    public E front() {
        return elements[index(0)];
    }

    /**
     * 获取队尾元素
     * @return
     */
    public E rear() {
        return elements[index(size - 1)];
    }

    /**
     * 由下表转化为真实的索引
     * @param index
     * @return
     */
    private int index(int index) {
        index += front;
        if (index < 0) {
            index += elements.length;
        }
//        return index % elements.length;
        // 改进的地方 可以使用加减代替取余 前提是: y < 2*x  我杰哥就是机智
        return index - (index >= elements.length ? elements.length : 0);

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
