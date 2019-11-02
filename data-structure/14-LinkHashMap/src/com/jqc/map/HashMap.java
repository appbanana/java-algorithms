package com.jqc.map;

import com.jqc.printer.BinaryTreeInfo;
import com.jqc.printer.BinaryTrees;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author appbanana
 * @date 2019/10/25
 */
public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    // 桶数组默认容量
    private static final int DEFAULT_CAPACITY = 1 << 4;
    // 装填因子
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;



    // 记录存储元素数量
    private int size;
    // 桶数组 里面存放根节点, 如果产生哈希冲突, 直接转换为红黑树来实现
    private Node<K, V>[] table;

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];

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
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        return add(key, value);
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
        Node<K, V> node = node(key);
        return node != null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedList<Node<K, V>> queue = new LinkedList<>();

        int tableLength = table.length;
        for (int i = 0; i < tableLength; i++) {
            Node<K, V> root = table[i];
            if (root == null) continue;
            queue.offer(root);

            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) return true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null || size == 0) return;
        LinkedList<Node<K, V>> queue = new LinkedList<>();
        int length = table.length;
        for (int i = 0; i < length; i++) {
            Node<K, V> root = table[i];
            if (root == null) continue;
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                visitor.visit(node.key, node.value);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

        }
    }
    public void print(){
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            if (root == null) continue;

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
            System.out.println("---------------------------------------------------");
        }
    }

    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) { }
    /**
     * 创建节点
     * @param key
     * @param value
     * @param parent
     * @return
     */
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }


    private V add(K key, V value){
        // 添加之前先看看 是否要扩容
        ensureCapacity();

        // 更具key 找到对应的索引 key-->hash值-->找到对应的索引
        int index = index(key);
        Node<K, V> root= table[index];
        if (root == null){
            // 第一个节点为跟节点
//            root = new Node<>(key, value, null);
            root = createNode(key, value, null);
            table[index] = root;
            size++;

            // 添加完元素后进行 avl树调整
            afterAdd(root);
            return null;
        }

        // 暂存节点的父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        Node<K, V> result = null;
        int hash1 = (k1 == null) ? 0 : k1.hashCode();
        boolean searched = false;

        while(node != null){
//            cmp = compare(key, node.key, hash1, node.hash);
            parent = node;
            K k2 = node.key;
            int hash2 = node.hash;
            if (hash1 > hash2){
                cmp = 1;
            }else if (hash1 < hash2){
                cmp = -1;
            }else if (Objects.equals(k1, k2)){
                cmp = 0;
            }else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0){
                // hash值一样 但是不equals 同类可比 但是不相等 什么也不需要做 下面会根据此时cmp判断node向左找还是向右找
            }else if (searched){
                //从这开始往下 包括后面的else都是不具有可比较性 只能扫描
                // 对于扫描 扫描一次就够了 没必要每次都扫描 所以加了一个searched的标志符， 一旦搜索扫描过，直接比较id地址
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);

            }else{
                // 下面不具有可比较性 开始扫描 left不为空 扫描左边 右边不为空扫描右边
                if ((node.left != null && ((result = node(node.left, k1)) != null))
                        || (node.right != null && (result = node(node.right, k1)) != null)){
                    node =result;
                    cmp = 0;
                }else {
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                    searched = true;
                }
            }

            if (cmp > 0){
                node = node.right;
            }else if (cmp < 0){
                node = node.left;
            }else {
                // 相等的元素直接进行覆盖
                node.key = key;
                V oldValue = value;
                node.value = oldValue;
                return oldValue;
            }
        }

        // 接下来我要执行添加节点功能，一方面我需要父节点，另一方面我需要知道添加到父节点的左边还是右边
//        Node<K, V> newNode = new Node<>(key, value, parent);
        Node<K, V> newNode = createNode(key, value, parent);

        if (cmp > 0){
            parent.right = newNode;
        }else{
            parent.left = newNode;
        }

        size++;
        // 添加完元素后进行 avl树调整
        afterAdd(newNode);

        return null;

    }

    /**
     * 删除节点
     * @param node
     * @return
     */
    private V remove(Node<K, V> node) {
        if (node == null) return null;

        Node<K, V> willNode = node;
        size--;
        V oldValue = node.value;

        if (node.hasTwoChildren()) {
            // 度为2的节点
            Node<K, V> s = successor(node);
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            node = s;
        }

        Node<K, V> replacement = node.left != null ? node.left : node.right;
        // replacement可能为空, node肯定不为空
        int index = index(node);

        if (replacement != null) {
            // 删除度为1的节点
            // 替换父节点
            replacement.parent = node.parent;
            if (node.parent == null) {
                table[index] = replacement;
            }else if (node.isLeftChild()) {
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
            fixAfterRemove(replacement);
        }else if (node.parent == null) {
            // 删除度为0的根节点
            table[index] = null;
            fixAfterRemove(node);
        }else {
            // 删除度为0的节点
            if (node.isLeftChild()) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            fixAfterRemove(node);
        }

        afterRemove(willNode, node);
        return oldValue;
    }




    private void afterAdd(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        // 如果添加的是跟节点 直接染成黑色就可以
        if (parent == null) {
            black(node);
            return;
        }
        // 如果父节点是黑色的 有4中情况 不用作任何处理
        if (isBlack(parent)) return;

        // 获取到叔父节点
        Node<K, V> uncle = parent.sibling();
        Node<K, V> grand = parent.parent;

        // 叔父节点是红色
        if (isRed(uncle)){
            black(parent);
            black(uncle);
            afterAdd(red(grand));
            return;
        }
        // 叔父节点是黑色
        if (parent.isLeftChild()){
            // L
            if (node.isLeftChild()){
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
            if (node.isLeftChild()){
                black(node);
                red(grand);
                // RL
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

    private void fixAfterRemove(Node<K, V> node) {
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
                    fixAfterRemove(parent);
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
                    fixAfterRemove(parent);
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


    private void rotateLeft(Node<K, V> grand) {

        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;

        afterRotate(grand, parent, child);

    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;

        grand.left = child;
        parent.right = grand;

        afterRotate(grand, parent, child);

    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child){
        // 让parent 成为新的跟节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()){
            grand.parent.left = parent;
        }else if (grand.isRightChild()){
            grand.parent.right = parent;
        }else {
            table[index(grand)] = parent;
        }

        // 更新child的parent
        if (child != null){
            child.parent = grand;
        }
        grand.parent = parent;
    }

    /**
     * 由key 找到对应的索引
     * @param key
     * @return
     */
    private int index(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        // 虽然你实现了hashcode 鬼知道你怎么实现的呀 保险起见，
        // java官方又对hash高低16位异或处理(扰动处理)
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    /**
     * 由node获取对应的索引
     * @param node
     * @return
     */
    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }

    private int hash(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }

    /**
     * 校验是否要动态扩容
     */
    private void ensureCapacity() {
        if (size / table.length <= DEFAULT_LOAD_FACTOR) return;

        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K, V>> queue = new LinkedList<>();

        for (int i = 0; i < oldTable.length; i++) {
            Node<K, V> root = oldTable[i];
            if (root == null) continue;

            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }

                moveNode(node);
            }
        }

    }

    /**
     * 移动节点 需要把节点原来的父节点全清掉
     * @param newNode
     */
    private void moveNode(Node<K, V> newNode) {
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = index(newNode);
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            afterAdd(root);
            return;
        }

        Node<K, V> node = root;
        Node<K, V> parent = root;

        K key1 = newNode.key;
        int hash1 = newNode.hash;
        int cmp = 0;

        while (node != null) {
            parent = node;
            K key2 = node.key;
            int hash2 = node.hash;

            if (hash1 > hash2) {
                cmp = 1;
            }else if (hash1 < hash2) {
                cmp = -1;
            }else if (key1 != null && key2 != null
                    && key1.getClass() == key2.getClass()
                    && key1 instanceof Comparable
                    && ((cmp = ((Comparable) key1).compareTo(key2))) != 0) {

            }else {
                cmp = System.identityHashCode(key1) - System.identityHashCode(key2);
            }

            if (cmp > 0) {
                node = node.right;
            }else if (cmp < 0) {
                node = node.left;
            }
        }

        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }

        // 修复红黑树性质
        afterAdd(newNode);

    }

    /**
     * 由key找到对应的node
     * @param key
     * @return
     */
    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K key1) {
        int hash1 = (key1 == null) ? 0 : key1.hashCode();
        int cmp = 0;
        Node<K, V> result = null;
        while (node != null) {
            K key2 = node.key;
            int hash2 = node.hash;

            if (hash1 > hash2) {
                node = node.right;
            }else if (hash1 < hash2) {
                node = node.left;
            }else if (Objects.equals(key1, key2)) {
                return node;
            }else if (key1 != null && key2 != null
                    && key1.getClass() == key2.getClass()
                    && key1 instanceof Comparable
                    && (cmp = ((Comparable) key1).compareTo(key2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            }else if (node.right != null && (result = node(node.right, key1)) != null) {
                return result;
            }else {
                node = node.left;
            }
        }
        return null;
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


    protected static class Node<K, V> {
        // 节点颜色属性
        boolean color = RED;

        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        // 父节点
        protected Node<K, V> parent;
        int hash; // key的hash值


        /**
         * 初始化节点
         * @param element 节点存放的元素
         * @param parent 父节点
         */
        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = key == null ? 0 : key.hashCode();
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
