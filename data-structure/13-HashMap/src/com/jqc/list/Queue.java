package com.jqc.list;

/**
 * 队列使用双链表来实现
 * 特点 先进先出
 * @author appbanana
 * @date 2019/10/17
 */
public class Queue<E> {

    private LinkList<E> list = new LinkList<>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void enQueue(E element) {
        list.add(element);
    }

    public E deQueue() {
        return list.remove(0);
    }

    public E front() {
        return list.get(0);
    }
}
