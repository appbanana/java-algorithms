package com.jqc.tree;

import java.util.Comparator;

/**
 * @author appbanana
 * @date 2019/10/23
 */
public class BBST<E> extends BinarySearchTree<E> {

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }

    /**
     * 左旋转
     * @param grand 失衡的祖父节点
     */
    protected void rotateLeft(Node<E> grand) {
//        AVLNode<E> parent = ((AVLNode<E>)grand).tallerNode();
//        AVLNode<E> node = parent.tallerNode();
        // 还是杰哥略屌 我都没有想到这些
        Node<E> parent = grand.right;
        Node<E> child = parent.left;

        grand.right = child;
        parent.left = grand;

        afterRotate(grand, parent, child);

    }

    /**
     * 右旋
     * @param grand  失衡的祖父节点
     */
    protected void rotateRight(Node<E> grand) {
//        AVLNode<E> parent = ((AVLNode<E>)grand).tallerNode();
//        AVLNode<E> node = parent.tallerNode();
        Node<E> parent = grand.left;
        Node<E> child = parent.right;


        grand.left = child;
        parent.right = grand;

        afterRotate(grand, parent, child);

    }

    /**
     * 旋转之后的操作
     * @param grand 祖父节点
     * @param parent 父节点
     * @param node 节点
     */
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child){

        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        }else if (grand.isRightChild()) {
            grand.parent.right = parent;
        }else {
            // 根节点
            root = parent;
        }

        // 更新child的父节点
        if (child != null) {
            child.parent = grand;
        }
        // 更新parent的父节点
        parent.parent = grand.parent;
        // 更新grand的父节点
        grand.parent = parent;

//        // 先更新较低节点的高度
//        updateHeight(grand);
//        // 在更新较高节点的高度
//        updateHeight(parent);
    }

}
