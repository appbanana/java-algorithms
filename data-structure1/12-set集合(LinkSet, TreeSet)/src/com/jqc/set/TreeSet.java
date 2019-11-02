package com.jqc.set;

import com.jqc.tree.BinaryTree;
import com.jqc.tree.RBTree;

import java.util.Comparator;

/**
 * @author appbanana
 * @date 2019/10/24
 */
public class TreeSet<E> implements Set<E> {

    private RBTree<E> rbTree;

    public TreeSet() {
        this(null);
    }
    public TreeSet(Comparator<E> comparator) {
        rbTree = new RBTree<>(comparator);
    }

    @Override
    public int size() {
        return rbTree.size();
    }

    @Override
    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public void clear() {
        rbTree.clear();
    }

    @Override
    public boolean contains(E element) {
        return rbTree.contains(element);
    }

    @Override
    public void add(E element) {
        if (!contains(element)) {
            rbTree.add(element);
        }
    }

    @Override
    public void remove(E element) {
        rbTree.remove(element);
    }

    @Override
    public void traversal(Visitor visitor) {
        if (visitor == null) return;
        rbTree.inorder(new BinaryTree.Visitor<E>() {
            @Override
            protected boolean visit(E element) {
                if (visitor.visit(element)) return true;
                return false;
            }
        });

    }
}
