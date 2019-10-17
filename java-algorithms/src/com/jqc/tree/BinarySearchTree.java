package com.jqc.tree;

import com.jqc.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 二叉搜索树
 * @author appbanana
 * @date 2019/10/17
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {
    // 记录元素数量
    private int size = 0;
    // 根节点
    private Node<E> root;
    // 比较器
    private Comparator<E> comparator;

    /**
     * 构造器
     */
    public BinarySearchTree() {
        this(null);
    }
    /**
     * 含比较器的初始化
     * @param comparator
     */
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }
    /**
     * 树包含的元素数量
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 二叉树是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 二叉树是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return false;
    }

    /**
     * 二叉树添加元素
     * @param element
     */
    public void add(E element) {
        elementNotNullCheck(element);

        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            parent = node;
            cmp = compareTo(element, node.element);
            if (cmp > 0) {
                node = node.right;
            }else if (cmp < 0) {
                node = node.left;
            }else {
                // 相等 直接覆盖
                node.element = element;
                return;
            }
        }

        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;

    }

    /**
     * 二叉树删除元素
     * @param element
     * @return
     */
    public E remove(E element) {
        return null;
    }

    /**
     * 元素是否为空校验
     * @param element
     */
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element not be null");
        }
    }

    /**
     * 比较方法
     * @param e1 第一个元素
     * @param e2 第二个元素
     * @return  当e1 > e2, 返回正数; 当e1 == e2, 返回0; 当e1 < e2, 返回负数
     */
    private int compareTo(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        // 父节点
        private Node<E> parent;

        /**
         * 初始化节点
         * @param element 节点存放的元素
         * @param parent 父节点
         */
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 判断叶子节点
         * @return
         */
        public boolean isLeaf(){
            return left == null && right == null;
        }

        /**
         * 判断该节点是包含左右两个子节点
         * @return
         */
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>)node;
        String parentString = "null";
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentString + ")";
    }



}
