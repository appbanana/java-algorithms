package com.jqc.circle;

import com.jqc.list.AbstractList;

/**
 * @author appbanana
 * @date 2019/10/17
 */

// 双循环双链表
public class CircleLinkList<E> extends AbstractList<E> {
    // 首结点
    private Node<E> first;
    // 尾结点
    private Node<E> last;

    private  class Node<E> {
        // 元素
        private E element;
        // 上一个节点
        private Node<E> prev;
        // 下一个节点
        private Node<E> next;


        public  Node(Node<E> pre,E element, Node<E> next) {
            this.prev = pre;
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            if (prev == null) {
                string.append("null");
            }else {
                string.append(prev.element);
            }
            string.append("_" + element + "_");

            if (next == null) {
                string.append("null");
            }else {
                string.append(next.element);
            }
            return string.toString();
        }
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        Node<E> node = node(index);
        return node.element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {

        if (index == size) {
            Node<E> oldLast = last;
            last = new Node(oldLast, element, first);
            if (oldLast == null) {
                // 添加首元素
                first = last;
                first.prev = first;
                first.next = first;
            }else {
                // 在末尾添加
                oldLast.next = last;
                first.prev = last;
            }
        }else {
            Node<E> next = node(index);
            Node<E> pre = next.prev;
            Node<E> newNode = new Node<>(pre, element, next);
            // 还需要改动两个位置 一个是next的pre指向新创建的节点, 另外一个是pre.next指向新创建的节点
            next.prev = newNode;
            pre.next = newNode;
            if (first == next) {
                first = newNode;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> cur = node(index);
        if (size == 1) {
            // 只有一个元素时,会产生循环引用, 需要单独处理
            first = null;
            last = null;
        }else {
            // 还是杰哥的方法简单粗暴 容易理解
            Node<E> prev = cur.prev;
            Node<E> next = cur.next;

            prev.next = next;
            next.prev = prev;

            if (cur == first) {
                // 如果删除节点是首结点
                first = next;
            }

            if (cur == last) {
                // 如果删除的节点是尾结点
                last = prev;
            }
        }

        size--;
        return cur.element;
    }

    @Override
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

    private Node<E> node(int index) {
        rangeCheck(index);
        int half = size >> 1;
        if (index < half) {
            // index 小于长度的一半 从前往后遍历
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }

        // index 大于长度一半 从后往前遍历
        Node<E> node = last;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size = ").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
//            Node<E> pre = node.pre;
//            Node<E> next = node.next;
//
//            string.append(pre == null ? "null" : pre.element);
//            string.append("_" + node.element + "_");
//            string.append(next == null ? "null" : next.element);

            string.append(node);

            node = node.next;

        }
        string.append("]");
        return string.toString();
    }
}
