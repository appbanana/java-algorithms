package com.jqc.tree;
import java.util.Comparator;

/**
 * 红黑树 红黑树等价于4阶B树
 * @author appbanana
 * @date 2019/10/23
 */
public class RBTree<E> extends BBST<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;



    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        // 添加的RBNode默认是红色
        Node<E> parent = node.parent;
        if (parent == null){
            // 如果添加的是根节点 直接染成黑色就可以
            black(node);
            return;
        }

        // 如果父节点是黑色的 有4中情况 不用作任何处理
        if (isBlack(parent))  return;

        // 获取到叔父节点
        Node<E> uncle = parent.sibling();
        Node<E> grand = parent.parent;

        if (isRed(uncle)) {
            // 叔父节点是红色, 在添加则会产生上溢, 此时父节点和叔父节点染黑吗grand上溢
            black(parent);
            black(uncle);
            // 祖父节点染红上溢
            afterAdd(red(grand));
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

    @Override
    protected void afterRemove(Node<E> node) {
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
        Node<E> parent = node.parent;
        // 为空说明删除的是根节点
        if (parent == null) return;
        // 判断该节点是左节点还是右节点
        boolean left = parent.left == null || node.isLeftChild();
        // 获取该节点的兄弟节点
        Node<E> sibling = left ? parent.right : parent.left;

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

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<E>(element, parent);
    }

    /**
     * 当前节点是否为红色节点
     * @param node 当前节点
     * @return
     */
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    /**
     * 当前节点是否是黑色节点
     * @param node 当前节点
     * @return
     */
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 节点染红
     * @param node 节点
     * @return
     */
    private RBNode<E> red(Node<E> node) {
        return color(node, RED);
    }

    /**
     * 节点染黑
     * @param node 当前节点
     * @return
     */
    private RBNode<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 上色
     * @param node 当前节点
     * @param color 颜色(bool值)
     * @return RBNode
     */
    private RBNode<E> color(Node<E> node, boolean color) {
        if (node != null) ((RBNode<E>)node).color = color;
        return (RBNode<E>) node;
    }

    /**
     * 返回当前节点颜色
     * @param node 节点
     * @return 颜色
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>)node).color;
    }

    private class RBNode<E> extends Node<E> {
        // 节点颜色属性
        boolean color = RED;
        /**
         * 初始化节点
         * @param element 节点存放的元素
         * @param parent  父节点
         */
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }
}
