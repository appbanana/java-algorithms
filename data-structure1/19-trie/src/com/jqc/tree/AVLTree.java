package com.jqc.tree;

import java.util.Comparator;

/**
 * AVL树
 * @author appbanana
 * @date 2019/10/21
 */
public class AVLTree<E> extends BBST<E> {

    public AVLTree() {
        this(null);
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalance(node)) {
                // 平衡 更新高度
                updateHeight(node);
            }else {
                // 不平衡 恢复平衡 更新高度 如果不平衡的话 也是从第一个祖父节点开始失去平衡,
                // 如果这个祖父节点失衡问题修复, 那么他的组祖父问题也会解决 所以这里使用break
                reBalance(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        // 因为删除节点只会导致他的父节点或者祖父...节点 或者的祖父的上一级到时失衡 所以要寻找到失衡的那个节点
        while ((node = node.parent) != null) {
            if (isBalance(node)){
                // 如果平衡 更新高度
                updateHeight(node);
            }else {
                // 失衡的话 需要再度恢复平衡
                reBalance(node);
            }
        }
    }

    @Override
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        super.afterRotate(grand, parent, child);
        // 先更新较低的高度
        updateHeight(grand);
        // 先更新较高的高度
        updateHeight(parent);
    }

    /**
     * 重写父类的方法
     * @param element 元素
     * @param parent 父节点
     * @return
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<E>(element, parent);
    }

    /**
     * 再度恢复平衡
     * @param grand
     */
    private void reBalance(Node<E> grand) {
        AVLNode<E> parent = ((AVLNode<E>)grand).tallerNode();
        AVLNode<E> node = parent.tallerNode();
        if (parent.isLeftChild()) {
            // L
            if (node.isLeftChild()) {
                // LL
                rotateRight(grand);
            }else {
                // LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else {
            // R
            if (node.isLeftChild()) {
                // RL
                rotateRight(parent);
                rotateLeft(grand);
            }else {
                // RR
                rotateLeft(grand);
            }
        }
    }

    /**
     * 更新当前节点高度
     * @param node
     */
    private void updateHeight(Node<E> node) {
        ((AVLNode<E>)node).updateHeight();
    }
    /**
     * 判断当前节点是否平衡
     * @param node 当前节点
     * @return
     */
    private boolean isBalance(Node<E> node) {
        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }

    private static class AVLNode<E> extends Node<E>{
        int height;

        /**
         * 初始化节点
         * @param element 节点存放的元素
         * @param parent  父节点
         */
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 平衡因子
         * @return 返回左右子树的高度差
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            return leftHeight - rightHeight;
        }

        /**
         * 更新节点高度
         */
        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        /**
         * 返回左右子树中较高的子树
         * @return 返回较高的节点
         */
        public AVLNode<E> tallerNode() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if (leftHeight > rightHeight) return (AVLNode<E>)left;
            if (leftHeight < rightHeight) return (AVLNode<E>)right;
            return isLeftChild() ? (AVLNode<E>)left : (AVLNode<E>)right;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")_h(" + height + ")";
        }
    }
}
