package com.jqc.tree;

import com.jqc.printer.BinaryTreeInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 抽出一些共有的代码, 因为后面的AVL树, 红黑树可能会用到
 * @author appbanana
 * @date 2019/10/20
 */
public class BinaryTree<E> implements BinaryTreeInfo {
    // 记录元素数量
    protected int size = 0;
    // 根节点
    protected Node<E> root;

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

    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 前序遍历 递归实现
     */
    public void preorderTraversal(Visitor<E> visitor) {
        preorderTraversal(root, visitor);
    }
    private void preorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        visitor.stop = visitor.visit(node.element);
        preorderTraversal(node.left, visitor);
        preorderTraversal(node.right, visitor);
    }

    /**
     * 中序遍历 递归实现
     */
    public void inorderTraversal(Visitor<E> visitor) {
        inorderTraversal(root, visitor);

    }
    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        inorderTraversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inorderTraversal(node.right, visitor);
    }

    /**
     * 后序遍历 递归实现
     */
    public void postorderTraversal(Visitor<E> visitor) {
        postorderTraversal(root, visitor);

    }
    private void postorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.stop) return;

        postorderTraversal(node.left, visitor);
        postorderTraversal(node.right, visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }
    /**
     * 前序遍历 非递归实现
     * @param root 根几点
     * @return
     */
    public void preorder(Visitor<E> visitor) {
        if (root == null) return ;
        // 使用自己实现的栈和动态数组来实现
//        Stack<Node<E>> stack = new Stack<>();
//        ArrayList<E> container = new ArrayList<>();

        Stack<Node<E>> stack = new Stack<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            if (visitor.visit(node.element)) return;

            if (node.right != null) {
                // 右子节点 先入栈
                stack.push(node.right);
            }

            if (node.left != null) {
                // 左子节点 后入栈
                stack.push(node.left);
            }
        }
    }


    /**
     * 中序遍历 非递归实现
     */
    public void inorder(Visitor<E> visitor) {
        if (root == null) return;
        Stack<Node<E>> stack = new Stack<>();

        Node<E> cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }else {
                Node<E> node = stack.pop();
                if (visitor.visit(node.element)) return;
                cur = node.right;
            }
        }

    }

    /**
     * 后序遍历 非递归实现
     */
    public void postorder(Visitor<E> visitor) {
        if (root == null) return;

        Stack<Node<E>> stack1 = new Stack<>();
        Stack<Node<E>> stack2 = new Stack<>();
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
            Node<E> node = stack2.pop();
            if (visitor.visit(node.element)) return;
        }
    }

    /**
     * 层序遍历 非递归来实现
     */
    public void levelOrder(Visitor<E> visitor) {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();

        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
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
     * 寻找前驱节点
     * @param node 当前节点
     * @return 返回前驱节点
     */
    protected Node<E> predecessor(Node<E> node) {
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
    protected Node<E> successor(Node<E> node) {
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
        protected abstract boolean visit(E element);
    }

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        // 父节点
        protected Node<E> parent;

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
    public Object string(Object node) {
        Node<E> myNode = (Node<E>)node;
        String parentString = "null";
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentString + ")";
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



}
