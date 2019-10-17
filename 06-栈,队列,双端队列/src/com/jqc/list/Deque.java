package com.jqc.list;

/**
 * 双端队列 使用动态数组来实现
 * @author appbanana
 * @date 2019/10/17
 */
public class Deque<E> {

    private ArrayList<E> list = new ArrayList<E>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    /**
     * 从对头入队列
     * @param element
     */
    public void enQueueFront(E element) {
        list.add(0, element);
    }

    /**
     * 从对头出队列
     * @return
     */
    public E deQueueFront() {
        return list.remove(0);
    }

    /**
     * 从队尾入队
     * @param element
     */
    public void enQueueRear(E element) {
        list.add(element);
    }

    /**
     * 从队尾出队
     * @param element
     */
    public E deQueueRear(E element) {
        return list.remove(list.size() - 1);
    }

    /**
     * 获取队头元素
     * @return
     */
    public E front() {
        return list.get(0);
    }

    /**
     * 获取队尾元素
     * @return
     */
    public E rear() {
        return list.get(list.size() - 1);
    }
}
