package com.jqc.list;

/**
 *  单链表
 */
public class SingleList<E> {
    // 记录大小
    private int size = 0;
    // 首结点
    private Node first;
    // 元素找不到的标志
    private static final int ELEMENT_NOT_FOUND = -1;

    private static class Node<E> {
        // node的当前元素
        private E element;
        // node节点的下一个元素
        private Node next;

        /**
         * 内部node类初始化
         * @param element 当前元素
         * @param next 下一个节点指向
         */
        public Node(E element, Node next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            string.append(element + "_");
            if (next == null) {
                string.append("null");
            }else {
                string.append(next.element);
            }
            return string.toString();
        }
    }


    /**
     * 构造器方法
     */
    public SingleList() {
        super();
    }

    /**
     *
     * @return 返回链表长度
     */
    public int size() {
        return size;
    }

    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * 检验链表是否为空
     * @return bool
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
        return indexOf(element) != ELEMENT_NOT_FOUND;
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
        if (index == 0) {
            first = new Node(element, first);
        }else {
            Node pre  = node(index - 1);
            Node cur = pre.next;
            Node newNode = new Node(element, cur);
            pre.next = newNode;
        }
        size++;

    }

    /**
     * 删除指定索引位置的元素
     * @param index
     * @return
     */
    public E remove(int index) {
        Node<E> cur = node(index);
        if (index == 0) {
            first = cur.next;
        }else {
            Node pre = node(index - 1);
            pre.next = cur.next;
        }
        size--;
        return cur.element;
    }

    /**
     * 查询元素是否存在
     * @param element 要查找的元素
     * @return 返回该元素的索引
     */
    public int indexOf(E element) {
        if (element == null) {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        }else {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element.equals(element)) return i;
                node = node.next;
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
        Node<E> node = node(index);
        return node.element;
    }

    /**
     * 指定位置更新元素
     * @param index 指定索引
     * @param element 新的元素
     * @return 返回旧的元素
     */
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    /**
     * 根据索引返回对应的节点
     * @param index 索引
     * @return
     */
    private Node node(int index){
        rangeCheck(index);
        Node node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
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
            throw new IndexOutOfBoundsException("add index out of bounds");
        }
    }

    /**
     * 元素是否为空的检测
     * @param element 元素
     */
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must be not null");
        }
    }


    @Override
    public String toString() {
        StringBuilder string =  new StringBuilder();
        string.append("size = ").append(size).append(", ");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(node);
            node = node.next;
        }

        return string.toString();
    }
}


