package com.jqc;

/**
 * 扩展为泛型, 可以存放任意类型的数组 模拟jdk1.8中中的动态数组(ArrayList)
 */
public class ArrayList<E> {
    // 记录动态数组大小
    private int size = 0;
    // 动态数组默认申请内存空间
    private static final int DEFAULT_CAPACITY = 5;
    // 元素找不到的标志
    private static final int ELEMENT_NOT_FOUND = -1;

    // 数组容器
    private E[] elements;

    /**
     * 构造器方法
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }
        /**
         * 数组的构造器
         * @param capacity
         */
    public ArrayList(int capacity) {
        capacity = Math.max(DEFAULT_CAPACITY, capacity);
        elements = (E[]) new Object[capacity];

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
            elements[i] = null;
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
    public boolean contain(E element) {
        return indexof(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 在指定位置插入元素
     * @param index 指定索引位置
     * @param element 元素
     */
    public void add(int index, E element) {
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
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        for (int i = index; i < size; i++) {
            elements[index] = elements[index + 1];
        }
        elements[size--] = null;
        return old;
    }

    /**
     * 查询元素是否存在
     * @param element 要查找的元素
     * @return
     */
    public int indexof(E element) {
//        for (int i = 0; i < size; i++) {
//            if (elements[i] == element) {
//                return i;
//            }
//        }
//        return ELEMENT_NOT_FOUND;

        // 扩展为泛型后 需要对下面的代码进行改进
        if (element == null) {
            // 空进入该方法
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        }else {
            // 非空对象进入该方法, 现在是扩展为泛型 很有可能自定义对象会重写equals方法
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(element)) return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }
    /**
     * 根据索引获取元素
     * @param index
     * @return
     */
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 指定位置更新元素
     * @param index 指定索引
     * @param element 新的元素
     * @return 返回旧的元素
     */
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * 索引检测
     * @param index
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:" + size);
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
        E[] newArray = (E[]) new Object[newCapacity];

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
