package com.jqc.list;

/**
 * 思路: 借助自己已经实现的动态数组来实现
 * 栈特点 后进先出
 * @author appbanana
 * @date 2019/10/17
 */
public class Stack<E> {
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

    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }

    public E top() {
        return list.get(list.size() - 1);
    }

}
