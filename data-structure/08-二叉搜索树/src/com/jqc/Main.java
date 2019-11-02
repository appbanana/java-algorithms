package com.jqc;

import com.jqc.printer.BinaryTrees;
import com.jqc.tree.BinarySearchTree;
import com.jqc.tree.BinaryTree.Visitor;

public class Main {

    public static void main(String[] args) {

        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);

        // 测试改进后的前序遍历
        testPreorder(bst);
        // 测试改进后中序遍历
        testInorder(bst);
        // 测试改进后的后序遍历
        testPostorder(bst);
        // 测试层序遍历
        testLevelOrder(bst);

    }

    static void testPreorder(BinarySearchTree<Integer> bst) {

        System.out.println("========前序递归遍历==============");
        StringBuilder preString = new StringBuilder();
        bst.preorderTraversal(new Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                preString.append(element).append(" ");
                return false;
            }
        });
        System.out.println(preString.toString());

        System.out.println("========前序非递归遍历==============");
        StringBuilder preString2 = new StringBuilder();
        bst.preorder(new Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                preString2.append(element).append(" ");
                return false;
            }
        });
        System.out.println(preString2.toString());
    }

    static void testInorder(BinarySearchTree<Integer> bst) {
        System.out.println("========中序递归遍历==============");
        StringBuilder inorderStr = new StringBuilder();
        bst.inorderTraversal(new Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                inorderStr.append(element).append(" ");
                return false;
            }
        });
        System.out.println(inorderStr.toString());

        System.out.println("========中序非递归遍历==============");
        StringBuilder inorderStr2 = new StringBuilder();
        bst.inorderTraversal(new Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                inorderStr2.append(element).append(" ");
                return false;
            }
        });
        System.out.println(inorderStr2.toString());


    }

    static void testPostorder(BinarySearchTree<Integer> bst) {
        System.out.println("========后序递归遍历==============");
        StringBuilder postorderStr = new StringBuilder();
        bst.postorderTraversal(new Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                postorderStr.append(element).append(" ");
                return false;
            }
        });
        System.out.println(postorderStr.toString());

        System.out.println("========后序非递归遍历==============");
        StringBuilder postorderStr2 = new StringBuilder();
        bst.postorder(new Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                postorderStr2.append(element).append(" ");
                return false;
            }
        });
        System.out.println(postorderStr2.toString());

    }

    static void testLevelOrder(BinarySearchTree<Integer> bst) {
        System.out.println("========层序遍历==============");
        StringBuilder levelorderStr = new StringBuilder();
        bst.levelOrder(new Visitor<Integer>() {
            @Override
            protected boolean visit(Integer element) {
                levelorderStr.append(element).append(" ");
                return false;
            }
        });
        System.out.println(levelorderStr.toString());
    }



}
