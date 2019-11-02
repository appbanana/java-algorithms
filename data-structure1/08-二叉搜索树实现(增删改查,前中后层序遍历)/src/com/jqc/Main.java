package com.jqc;

import com.jqc.printer.BinaryTrees;
import com.jqc.tree.BinarySearchTree;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {

        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
//                7, 4, 9, 2, 5, 8, 11
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);


        // 测试二叉树的添加
//        test1();
//        System.out.println("==================\n");
        // 测试二叉搜索树添加对象
//        test2();
        // 测试自定义比较器
//        test3();

//         // 测试删除
//        test4(bst);
//
//         // 测试前序
//        testPreorder(bst);
//         // 测试中序遍历
//        testInorder(bst);
//        // 测试后序遍历
//        testPostorder(bst);

        // 测试层序遍历
//        testLevelOrder(bst);

        // 测试完全二叉树
//        testComplete(bst);

        testHeight(bst);
    }

    static void test2() {
        BinarySearchTree<Person> bst = new BinarySearchTree<>();
        bst.add(new Person(10, "jack"));
        bst.add(new Person(12, "rose"));
        bst.add(new Person(6, "jim"));

        bst.add(new Person(10, "michael"));

        BinaryTrees.println(bst);
    }

    static void test3() {
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        BinarySearchTree<Person> tree = new BinarySearchTree<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });

        for (int i = 0; i < data.length; i++) {
            tree.add(new Person(data[i]));
        }

        BinaryTrees.print(tree);
    }


    static void test4(BinarySearchTree<Integer> bst) {

        System.out.println("=======================\n");

        bst.remove(9);
        BinaryTrees.println(bst);

    }

//    static void testPreorder(BinarySearchTree<Integer> bst) {
    static void testPreorder(BinarySearchTree<Integer> bst) {
        System.out.println("=========前序遍历==========");
        bst.preorderTraversal();
        System.out.println("=========非递归==========");
        bst.preorder();
    }

    static void testInorder(BinarySearchTree<Integer> bst) {

        System.out.println("=========中序遍历==========");
        bst.inorderTraversal();
        System.out.println("=========非递归==========");
        bst.inorder();
    }

    static void testPostorder(BinarySearchTree<Integer> bst) {

        System.out.println("=========后序遍历==========");
        bst.postorderTraversal();
        System.out.println("=========非递归==========");
        bst.postorder();
    }

    static void testLevelOrder(BinarySearchTree<Integer> bst) {
        System.out.println("=========层序遍历==========");
        bst.levelOrder();
    }

    static void testComplete(BinarySearchTree<Integer> bst) {
        System.out.println(bst.isComplete());
    }

    static void testHeight(BinarySearchTree<Integer> bst) {
        System.out.println("=========二叉搜索树高度==========");
        System.out.println(bst.heightRecursion());
        System.out.println("=========非递归==========");
        System.out.println(bst.height());
    }
}
