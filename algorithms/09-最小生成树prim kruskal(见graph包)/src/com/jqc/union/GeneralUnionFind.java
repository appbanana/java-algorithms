package com.jqc.union;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author appbanana
 * 支持泛型的并查集
 * @date 2019/12/26
 */
public class GeneralUnionFind<V> {

    private HashMap<V, Node<V>> map = new HashMap<>();

    /**
     * 生成集合 自己指向自己
     * @param key
     */
    public void makeSet(V key) {
        if (map.containsKey(key)) return;
        Node<V> node = new Node<>(key);
        map.put(key, node);
    }

    public V find(V key) {
        Node<V> node = findNode(key);
        return node != null ? node.value : null;
    }

    public void union(V key1, V key2) {
        Node<V> p1 = findNode(key1);
        Node<V> p2 = findNode(key2);
        if (p1 == null || p2 == null) return;
        if (Objects.equals(p1.value, p2.value)) return;
        if (p1.rank < p2.rank) {
            p1.parent = p2;
        }else if (p1.rank > p2.rank) {
            p2.parent = p1;
        }else {
            p1.parent = p2;
            p2.rank++;
        }
    }

    public boolean isSame(V key1, V key2) {
        return Objects.equals(find(key1), find(key2));
    }

    /**
     * 找出v的根节点
     * @param v 键值
     * @return 节点
     */
    private Node<V> findNode(V key) {
        Node<V> node = map.get(key);
        if (node == null) return node;
        while (!Objects.equals(node.value, node.parent.value)) {
            node.parent = node.parent.parent;
            node = node.parent;
        }
        return node;
    }

    private static class Node<V> {
        V value;
        Node<V> parent = this;
        int rank = 1;
        public Node(V value) {
            this.value = value;
        }
    }
}
