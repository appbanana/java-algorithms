package com.jqc.map;

import java.util.Objects;

/**
 * @author appbanana
 * @date 2019/10/27
 */
public class LinkHashMap<K, V> extends HashMap<K, V> {

    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(node.value, value)) return true;
            node = node.next;
        }

        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        LinkedNode<K, V> node = first;
        while (node!= null) {
            if (visitor.visit(node.key, node.value)) return;
            node = node.next;
        }
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
        LinkedNode<K, V> node1 = (LinkedNode<K, V>)willNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>)removedNode;

        // 如果node1 和node2不相等 说明删除的是度为2的节点
        if (node1 != node2){
            // 交换linkedWillNode和linkedRemovedNode在链表中的位置
            // 交换prev
            LinkedNode<K, V> temp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = temp;

            if (node1.prev == null){
                first = node1;
            }else {
                node1.prev.next = node1;
            }
            if (node2.prev == null){
                first = node2;
            }else {
                node2.prev.next = node2;
            }

            // 交换next
            temp = node1.next;
            node1.next = node2.next;
            node2.next = temp;

            if (node1.next == null){
                last = node1;
            }else {
                node1.next.prev = node1;
            }

            if (node2.next == null){
                last = node2;
            }else {
                node2.next.prev = node2;
            }
        }

        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }
    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> newNode = new LinkedNode<>(key, value, parent);
        if (first == null) {
            // 首结点
            first = last = newNode;
        }else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        return newNode;
    }


    private static class LinkedNode<K, V> extends Node<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;
        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
