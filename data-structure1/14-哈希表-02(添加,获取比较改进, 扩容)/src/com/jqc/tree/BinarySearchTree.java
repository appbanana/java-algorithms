package com.jqc.tree;

import java.util.*;

/**
 * 二叉搜索树
 * @author appbanana
 * @date 2019/10/17
 */
public class BinarySearchTree<E> extends BinaryTree<E> {

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
     * 二叉树是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return node(element) != null;
    }

    /**
     * 二叉树添加元素
     * @param element
     */
    public void add(E element) {
        elementNotNullCheck(element);

        if (root == null) {
//            root = new Node<E>(element, null);
            root = createNode(element, null);
            size++;
            // 添加之后判断是否满足平衡因子, 不满足的话需要修复, 满足的话更新节点高度
            afterAdd(root);
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
        // 根据上面cmp大小判断它在父节点的左边还是右边
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
        // 添加之后判断是否满足平衡因子, 不满足的话需要修复, 满足的话更新节点高度
        afterAdd(newNode);
    }

    /**
     * 二叉树删除元素
     * @param element
     * @return
     */
    public void remove(E element) {
         remove(node(element));
    }

    /**
     * 创建节点 暴露出去 因为后面AVLTree RBTree要使用
     * @param element 元素
     * @param parent 父节点
     * @return Node节点
     */
    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<E>(element, parent);
    }

    /**
     * 添加新节点之后的操作
     */
    protected void afterAdd(Node<E> node) { }

    /**
     * 删除节点之后的操作
     */
    protected void afterRemove(Node<E> node) { }

    /**
     * 删除节点
     * @param node 要删除的节点
     * @return
     */
    private void remove(Node<E> node) {
        if (node == null) return ;
        size--;

        if (node.hasTwoChildren()) {
            // 删除的节点有两个节点(度为2) 需要找到他的前驱或后继进行删除;
            Node<E> successor = successor(node);
            node.element = successor.element;
            // 删除后继节点
            node = successor;
        }

        // 下面处理的是度为1或者度为0的节点
        //  1)度为1的节点直接使用其子节点代替
        //  2)度为0的节点,直接将该节点删掉
        Node<E> replaceNode = node.left != null ? node.left : node.right;
        if (replaceNode != null) {
            // 度为1的节点
            // 更换replaceNode的父节点
            replaceNode.parent = node.parent;
            if (node.parent == null) {
                // 根节点
                root = replaceNode;
            } else if (node == node.parent.left) {
                node.parent.left = replaceNode;
            }else {
                node.parent.right = replaceNode;
            }
            // 删除之后 AVL去处理后续操作
            afterRemove(replaceNode);
        }else if (node.parent == null) {
            // 删除的是根节点, 而且根节点没有子节点
            root = null;
            // 删除之后 AVL去处理后续操作
            afterRemove(node);
        }else {
            // 度为0的节点
            if (node == node.parent.left) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }

            // 删除之后AVL去处理
            afterRemove(node);
        }
    }


    /**
     * 根据元素获取node
     * @param element
     * @return
     */
    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compareTo(element, node.element);
            if (cmp > 0) {
                node = node.right;
            }else if (cmp < 0) {
                node = node.left;
            }else {
                return node;
            }
        }
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

}
