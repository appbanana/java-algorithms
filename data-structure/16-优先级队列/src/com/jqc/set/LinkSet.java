package com.jqc.set;

import com.jqc.list.LinkList;

/**
 * 采用双链表来实现
 * @author appbanana
 * @date 2019/10/24
 */
public class LinkSet<E> implements Set<E> {

    private LinkList<E> linkList = new LinkList<E>();

    @Override
    public int size() {
        return linkList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkList.isEmpty();
    }

    @Override
    public void clear() {
        linkList.clear();
    }

    @Override
    public boolean contains(E element) {
        return linkList.contains(element);
    }

    @Override
    public void add(E element) {
        if (!linkList.contains(element)) {
            linkList.add(element);
        }
    }

    @Override
    public void remove(E element) {
        int index = linkList.indexOf(element);
        if (index != LinkList.ELEMENT_NOT_FOUND) {
            linkList.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) return;

        for (int i = 0; i < size(); i++) {
            if (visitor.visit(linkList.get(i))) return;
        }

    }
}
