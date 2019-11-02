package com.jqc.map;

import com.jqc.printer.BinaryTreeInfo;
import com.jqc.printer.BinaryTrees;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author appbanana
 * @date 2019/10/24
 */
public class TreeMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    
    // 记录存储元素数量
    private int size;
    // 根节点
    private Node<K, V> root;
    // 自定义比较器
    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        elementNotNullCheck(key);
        if (root == null) {
            // 首次添加节点
            root = new Node<K, V>(key, value, null);
            size++;
            afterPut(root);
            return value;
        }

        Node<K, V> node = root;
        Node<K, V> parent = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            }else if (cmp < 0) {
                node = node.left;
            }else {
                // 相等 直接覆盖
                node.key = key;
                node.value = value;
                return value;
            }
        }

        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return value;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        // 层序遍历 比对
        if (root == null) return false;
        LinkedList<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (Objects.equals(node.value, value)) return true;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    public void print(){
        if (root == null || size == 0) return;
        BinaryTrees.println(new BinaryTreeInfo() {
            @Override
            public Object root() {
                return root;
            }

            @Override
            public Object left(Object node) {
                return ((Node<K, V>)node).left;
            }

            @Override
            public Object right(Object node) {
                return ((Node<K, V>)node).right;
            }

            @Override
            public Object string(Object node) {
                return node;
            }
        });

    }


    /**
     * 中序遍历作用 有序输出
     * @param visitor
     */
    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root, visitor);
    }

    public void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) return;

        traversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);

    }

    /**
     * 根据key找到对应的节点
     * @param key key值
     * @return
     */
    private Node<K, V> node(K key) {
        elementNotNullCheck(key);
        Node<K, V> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(key, node.key);
            if (cmp == 0) {
                return node;
            }else if (cmp > 0) {
                node = node.right;
            }else {
                node = node.left;
            }
        }

        return null;
    }

    private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;

        V oldValue = node.value;
        if (node.hasTwoChildren()) {
            // 删除的节点的度为2
            Node<K, V> s = successor(node);
            node.key = s.key;
            node.value = s.value;
            node = s;
        }

        // 接下来处理度为0或者1的情况
        Node<K, V> replacement = (node.left != null) ? node.left : node.right;
        if (replacement != null) {
            // 度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) {
                // // node是度为1的节点并且是根节点
                root = replacement;
            }else if (node == node.parent.left) {
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
            afterRemove(replacement);
        }else if (node.parent == null) {
            root = null;
        }else {
            // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            afterRemove(node);
        }
        return oldValue;
    }
    private void afterPut(Node<K, V> node) {
        // 添加的RBNode默认是红色
        Node<K, V> parent = node.parent;
        if (parent == null){
            // 如果添加的是根节点 直接染成黑色就可以
            black(node);
            return;
        }

        // 如果父节点是黑色的 有4中情况 不用作任何处理
        if (isBlack(parent))  return;

        // 获取到叔父节点
        Node<K, V> uncle = parent.sibling();
        Node<K, V> grand = parent.parent;

        if (isRed(uncle)) {
            // 叔父节点是红色, 在添加则会产生上溢, 此时父节点和叔父节点染黑吗grand上溢
            black(parent);
            black(uncle);
            // 祖父节点染红上溢
            afterPut(red(grand));
            return;
        }

        // 接下来处理的叔父节点是黑色的
        if (parent.isLeftChild()) {
            // L
            if (node.isLeftChild()) {
                // LL
                black(parent);
                red(grand);
                rotateRight(grand);
            }else {
                // LR
                black(node);
                red(grand);
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else {
            // R
            if (node.isLeftChild()) {
                // RL
                black(node);
                red(grand);
                rotateRight(parent);
                rotateLeft(grand);
            }else {
                // RR
                black(parent);
                red(grand);
                rotateLeft(grand);
            }
        }

    }

    /**
     * 寻找前驱节点
     * @param node 当前节点
     * @return 返回前驱节点
     */
    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;
        Node<K, V> p = node.left;
        if (p != null) {
            // 比如示例中的7, 3...
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        // 比如示例中的8,需要在他的父节点链中寻找
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 寻找该节点的后继节点
     * @param node 当前节点
     * @return 返回后继节点
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        // 比如示例中的2, 7, 9...
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        // 比如示例中的3, 5...
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }
    
    private void afterRemove(Node<K, V> node) {

        // 如果删除节点是红色 不需要做任何处理
        // 这种情况可以和黑色节点拥有1个红色节点合并
//        if (isRed(node)) return;

        // 删除黑色节点有三种情况
        // 1) 该黑色节点有2个红色子节点 (这中情况可以不用考虑,该黑色节点不可能被删除,
        //      删除的节点肯定是它的前驱或者后继节点)
        // 2) 该黑色节点有1个红色子节点(简称1红)
        // 3) 该黑色节点没有红色子节点 (简称0红)

        if (isRed(node)) {
            // 删除的黑色节点拥有1红色子节点
            black(node);
            return;
        }

        // 接下来就是删除黑色的叶子节点  可能会产生下溢
        Node<K, V> parent = node.parent;
        // 为空说明删除的是根节点
        if (parent == null) return;
        // 判断该节点是左节点还是右节点
        boolean left = parent.left == null || node.isLeftChild();
        // 获取该节点的兄弟节点
        Node<K, V> sibling = left ? parent.right : parent.left;

        if (left) {
            // 删除的是左子节点
            if (isRed(sibling)){
                black(sibling);
                red(parent);
                rotateLeft(parent);
                sibling = parent.right;
            }

            // 下面是删除节点的兄弟节点是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                // 没有红色子节点 父节点下溢
                boolean parentBlack = isBlack(parent);
                red(sibling);
                black(parent);
                if (parentBlack){
                    // 如果之前的父节点为黑色 还会产生下溢 接着执行删除
                    afterRemove(parent);
                }


            }else {
                // 兄弟节点中至少有一个红色节点
                if (isBlack(sibling.right)){
                    // 如果兄弟节点左边是黑色节点 需要先旋转一下
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                // 兄弟节点继承父节点的颜色
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        }else {
            if (isRed(sibling)) {
                // 兄弟节点是红色的(红兄弟) 将兄弟节点染黑 父节点染红 右旋
                black(sibling);
                red(parent);
                // 右旋父节点, 把红兄弟转换为黑兄弟
                rotateRight(parent);
                // 旋转后要更新兄弟节点
                sibling = parent.left;
            }

            // 接下来处理的是黑兄弟情况 有两种情况
            // 1) 黑兄弟至少有一个红色子节点
            // 2) 黑兄弟没有红色子节点
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 黑兄弟没有红色子节点, 黑兄弟没有子节点可借
                boolean isParentBlack = isBlack(parent);
                red(sibling);
                black(parent);
                if (isParentBlack) {
                    // 如果之前的父节点为黑色 还会产生下溢 接着执行删除
                    afterRemove(parent);
                }
            }else {
                // 黑兄弟至少有一个红色子节点
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                // 兄弟节点继承父节点的颜色
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }
    /**
     * 左旋转
     * @param grand 失衡的祖父节点
     */
    private void rotateLeft(Node<K, V> grand) {
//        AVLNode<K, V> parent = ((AVLNode<K, V>)grand).tallerNode();
//        AVLNode<K, V> node = parent.tallerNode();
        // 还是杰哥略屌 我都没有想到这些
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;

        grand.right = child;
        parent.left = grand;

        afterRotate(grand, parent, child);

    }

    /**
     * 右旋
     * @param grand  失衡的祖父节点
     */
    private void rotateRight(Node<K, V> grand) {
//        AVLNode<K, V> parent = ((AVLNode<K, V>)grand).tallerNode();
//        AVLNode<K, V> node = parent.tallerNode();
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;


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
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child){

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


    /**
     * 比较方法
     * @param key1
     * @param key2
     * @return
     */
    private int compare(K key1, K key2) {
        if (comparator != null) {
            return comparator.compare(key1, key2);
        }
        return ((Comparable<K>)key1).compareTo(key2);
    }

    /**
     * 当前节点是否为红色节点
     * @param node 当前节点
     * @return
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * 当前节点是否是黑色节点
     * @param node 当前节点
     * @return
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 节点染红
     * @param node 节点
     * @return
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 节点染黑
     * @param node 当前节点
     * @return
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 上色
     * @param node 当前节点
     * @param color 颜色(bool值)
     * @return RBNode
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node != null) node.color = color;
        return node;
    }

    /**
     * 返回当前节点颜色
     * @param node 节点
     * @return 颜色
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }


    /**
     * 校验key是否为空
     * @param key
     */
    private void elementNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must be not null");
        }
    }


    private static class Node<K, V> {
        // 节点颜色属性
        boolean color = RED;
        
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        // 父节点
        protected Node<K, V> parent;

        /**
         * 初始化节点
         * @param element 节点存放的元素
         * @param parent 父节点
         */
        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
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

        /**
         * 判断是否是左子节点
         * @return 返回bool值
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 判断该节点是不是右节点
         * @return 返回bool值
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 返回叔父节点
         * @return
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + key.toString();
        }
    }


}
