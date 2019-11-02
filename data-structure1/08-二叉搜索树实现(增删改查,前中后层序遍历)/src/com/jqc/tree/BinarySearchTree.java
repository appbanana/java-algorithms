package com.jqc.tree;

//import com.jqc.list.ArrayList;
//import com.jqc.list.Stack;
import com.jqc.printer.BinaryTreeInfo;

import java.util.*;

/**
 * 二叉搜索树
 * @author appbanana
 * @date 2019/10/17
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {
    // 记录元素数量
    private int size = 0;
    // 根节点
    private Node<E> root;
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
     * 树包含的元素数量
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 二叉树是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 二叉树是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return false;
    }

    /**
     * 二叉树添加元素
     * @param element
     */
    public void add(E element) {
        elementNotNullCheck(element);

        if (root == null) {
            root = new Node<>(element, null);
            size++;
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

        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;

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
     * 前序遍历 递归实现
     */
    public void preorderTraversal() {
        ArrayList<E> container = new ArrayList<>();
        preorderTraversal(root, container);
        System.out.println(container.toString());

    }
    private void preorderTraversal(Node<E> node, ArrayList<E> container) {
        if (node == null) return;

        container.add(node.element);
        preorderTraversal(node.left, container);
        preorderTraversal(node.right, container);
    }

    /**
     * 中序遍历 递归实现
     */
    public void inorderTraversal() {
        ArrayList<E> container = new ArrayList<>();
        preorderTraversal(root, container);
        System.out.println(container.toString());

    }
    private void inorderTraversal(Node<E> node, ArrayList<E> container) {
        if (node == null) return;

        inorderTraversal(node.left, container);
        container.add(node.element);
        inorderTraversal(node.right, container);
    }

    /**
     * 后序遍历 递归实现
     */
    public void postorderTraversal() {
        ArrayList<E> container = new ArrayList<>();
        postorderTraversal(root, container);
        System.out.println(container.toString());

    }
    private void postorderTraversal(Node<E> node, ArrayList<E> container) {
        if (node == null) return;

        postorderTraversal(node.left, container);
        postorderTraversal(node.right, container);
        container.add(node.element);
    }
    /**
     * 前序遍历 非递归实现
     * @param root 根几点
     * @return
     */
    public void preorder() {
        if (root == null) return ;
        // 使用自己实现的栈和动态数组来实现
//        Stack<Node<E>> stack = new Stack<>();
//        ArrayList<E> container = new ArrayList<>();

        Stack<Node<E>> stack = new Stack<>();
        ArrayList<E> container = new ArrayList<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            container.add(node.element);

            if (node.right != null) {
                // 右子节点 先入栈
                stack.push(node.right);
            }

            if (node.left != null) {
                // 左子节点 后入栈
                stack.push(node.left);
            }
        }
        System.out.println(container.toString());
    }

    /**
     * 中序遍历 非递归实现
     */
    public void inorder() {
        if (root == null) return;
        Stack<Node<E>> stack = new Stack<>();
        ArrayList<E> container = new ArrayList<>();

        Node<E> cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }else {
                Node<E> node = stack.pop();
                container.add(node.element);
                cur = node.right;
            }
        }

        System.out.println(container.toString());
    }

    /**
     * 后序遍历 非递归实现
     */
    public void postorder() {
        if (root == null) return;

        Stack<Node<E>> stack1 = new Stack<>();
        Stack<Node<E>> stack2 = new Stack<>();
        ArrayList<E> container = new ArrayList<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node<E> node = stack1.pop();
            stack2.push(node);
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }

        while (!stack2.isEmpty()) {
            container.add(stack2.pop().element);
        }

        System.out.println(container.toString());
    }

    /**
     * 层序遍历 非递归来实现
     */
    public void levelOrder() {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        ArrayList<E> container = new ArrayList<>();

        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            container.add(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }

        System.out.println(container.toString());
    }

    /**
     *  判断二叉搜索树是否是完全二叉树
     */
    public boolean isComplete() {
        if (root == null) return false;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean isLeaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            if (isLeaf && !node.isLeaf()) return false;

            if (node.hasTwoChildren()) {
                // 度为2的节点 全部入队
                queue.offer(node.left);
                queue.offer(node.right);
            }else if (node.left == null && node.right != null) {
                // 左边为空 右边不为空 一定不是完全二叉树
                return false;
            }else {
                // 只要左边的是叶子节点 后面一定都是叶子节点
                isLeaf = true;
            }
        }
        return false;
    }

    /**
     * 返回二叉搜索树的高度 递归实现
     * @return
     */
    public int heightRecursion() {
        if (root == null) return 0;
        return heightRecursion(root);
    }

    public int heightRecursion(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRecursion(node.left), heightRecursion(node.right));
    }

    /**
     * 返回二叉搜索树的高度
     * @return
     */
    public int height() {
        if (root == null) return 0;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        int height = 0;
        int levelSize = queue.size();

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            if (levelSize == 0) {
                // levelSize为0 意味着这一层遍历完, 即将开始下一层
                levelSize = queue.size();
                height += 1;
            }
        }

        return height;
    }
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
        }else if (node.parent == null) {
            // 删除的是根节点, 而且根节点没有子节点
            root = null;
        }else {
            // 度为0的节点
            if (node == node.parent.left) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
        }
    }

    /**
     * 寻找前驱节点
     * @param node 当前节点
     * @return 返回前驱节点
     */
    private Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        Node<E> p = node.left;
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
    private Node<E> successor(Node<E> node) {
        if (node == null) return null;
        // 比如示例中的2, 7, 9...
        Node<E> p = node.right;
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

    /**
     * 遍历时 对外提供访问类
     * @param <E>
     */
    public static abstract class Visitor<E> {
        boolean stop;
        /**
         * @return 如果返回true，就代表停止遍历
         */
        abstract boolean visit(E element);
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

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        // 父节点
        private Node<E> parent;

        /**
         * 初始化节点
         * @param element 节点存放的元素
         * @param parent 父节点
         */
        public Node(E element, Node<E> parent) {
            this.element = element;
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
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        Node<E> myNode = (Node<E>)node;
        String parentString = "null";
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentString + ")";
    }


}
