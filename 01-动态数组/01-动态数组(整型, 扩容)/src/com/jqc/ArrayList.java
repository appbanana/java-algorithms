package com.jqc;

public class ArrayList {
    // 记录动态数组大小
    private int size = 0;
    // 动态数组默认申请内存空间
    private static final int DEFAULT_CAPACITY = 5;
    // 元素找不到的标志
    private static final int ELEMENT_NOT_FOUND = -1;

    // 数组容器
    private int[] elements;

    /**
     * 数组的构造器
     * @param capacity
     */
    public ArrayList(int capacity) {
        capacity = Math.max(DEFAULT_CAPACITY, capacity);
        elements = new int[capacity];
    }

    /**
     * 返回数组的长度
     * @return
     */
    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = 0;
        }
        size = 0;
    }

    /**
     * 数组是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某个元素
     * @param element
     * @return
     */
    public boolean contain(int element) {
        return indexof(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素
     * @param element
     */
    public void add(int element) {
        add(size, element);
    }

    /**
     * 在指定位置插入元素
     * @param index 指定索引位置
     * @param element 元素
     */
    public void add(int index, int element) {
        addRangeCheck(index);
        ensureCapacity(size + 1);
        for (int i = index; i < size; i++) {
            elements[size] = elements[size - 1];
        }
        elements[index] = element;
        size++;
    }

    /**
     * 删除指定索引位置的元素
     * @param index
     * @return
     */
    public int remove(int index) {
        rangeCheck(index);
        int old = elements[index];
        for (int i = index; i < size; i++) {
            elements[index] = elements[index + 1];
        }
        elements[size--] = 0;
        return old;
    }

    /**
     * 查询元素是否存在
     * @param element 要查找的元素
     * @return
     */
    public int indexof(int element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element) {
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }
    /**
     * 根据索引获取元素
     * @param index
     * @return
     */
    public int get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 指定位置更新元素
     * @param index 指定索引
     * @param element 新的元素
     * @return 返回旧的元素
     */
    public int set(int index, int element) {
        rangeCheck(index);
        int old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * 索引检测
     * @param index
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    /**
     * 添加索引检测
     * @param index
     */
    private void addRangeCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    /**
     * 扩容
     * @param capacity 现容器数组的长度
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (capacity <= oldCapacity) return;

        // 扩容
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        int[] newArray = new int[newCapacity];

        // 复制元素到新数组中
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }
        elements = newArray;
        System.out.println(oldCapacity + "扩容为" + newCapacity);

    }

    @Override
    public String toString() {
        StringBuilder string =  new StringBuilder();
        string.append("size = ").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(elements[i]);
        }

        string.append("]");
        return string.toString();
    }
}
