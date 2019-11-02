package com.jqc.list;

/**
 * @author appbanana
 * @date 2019/10/17
 */

// 抽象类可以实现接口中的部分方法
public abstract class AbstractList<E> implements List<E> {
    // 记录元素长度
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }

    /**
     * 索引检测
     * @param index
     */
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
        }
    }

    /**
     * 添加索引检测
     * @param index
     */
    protected void addRangeCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("add index out of bounds");
        }
    }

    /**
     * 元素是否为空的检测
     * @param element 元素
     */
    protected void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must be not null");
        }
    }
}
