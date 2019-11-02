package com.jqc.trie;

import java.util.HashMap;

/**
 * @author appbanana
 * @date 2019/11/2
 */
public class Trie<V> {

    private int size;
    private Node<V> root = new Node<>(null);



    public void clear() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return  size == 0;
    }

    /**
     * 包含这个字符串 而且还是个单词
     * @param str 待判断的字符创
     * @return
     */
    public boolean contains(String str) {
        Node<V> node = node(str);
        return node != null ? node.isWord : false;
    }

    public V add(String str, V value) {
        keyCheck(str);
        Node<V> node = root;
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            Node<V> childNode = node.getChildren().get(ch);

            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = ch;
                node.children.put(ch, childNode);
            }

            node = childNode;
        }


        if (node.isWord) {
            // 原来这个词如果是一个单词 直接覆盖
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        // 原来不是单词
        node.isWord = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String str) {
        Node<V> node = node(str);
        // 如果node为空或者这个node不是某个单词的结尾, 而是其他词的一部分时, 直接返回
        // (ps:isWord标记他是不是某个词的结尾)
        if (node == null || !node.isWord) return null;
        size--;

        // 接下来它可能是某个词的结尾(ps:isWord被标记为true意味着这是某个词的结尾)
        V oldValue = node.value;
        if (node.children != null && !node.children.isEmpty()) {
            // 如果当前节点还有其他节点, 此时这个节点不能删, 只能把内容清空和将标志位置为false
            node.isWord = false;
            node.value = null;
            return oldValue;
        }

        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            // 父节点删除这个key键
            parent.children.remove(node.character);
            //children 是个map key是node.character, value是个node节点
            // 如果父节点是其他某个词的结尾, 不能删 直接break
            // 他的父节点children不为空,不为空以为着这个key还是其他node的key,所以不能删
            if (parent.isWord || !parent.children.isEmpty()) break;
            node = parent;
        }

        return oldValue;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null ? node.value : null;
    }

    /**
     * 是否以这个字符串开头
     * @param prefix
     * @return
     */
    public boolean startWith(String prefix) {
        Node<V> node = node(prefix);
        return node != null;
    }

    /**
     * 根据key获取对应的node节点
     * @param key
     * @return
     */
    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        for (int i = 0; i < key.length(); i++) {
            Character ch = key.charAt(i);
            Node<V> children = node.children.get(ch);
            if (children == null) return null;

            node = children;
        }
        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    // 私有静态类
    private static class Node<V> {
        HashMap<Character, Node<V>> children;
        V value;
        boolean isWord;
        Node<V> parent;
        Character character;

        public Node(Node<V> parent){
            this.parent = parent;
        }

        HashMap<Character, Node<V>> getChildren() {
            return children == null ? (children = new HashMap<>()) : children;
        }
    }

}
