package com.jqc;

import com.jqc.printer.BinaryTrees;
import com.jqc.tools.Asserts;
import com.jqc.tree.AVLTree;


public class Main {

    public static void main(String[] args) {
//        test1();
        test2();

    }

    static void test1() {
        Integer data[] = new Integer[] {
                67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
        };


        AVLTree<Integer> avl = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            avl.add(data[i]);
        }
        BinaryTrees.print(avl);

        System.out.println();
        for (int i = 0; i < data.length; i++) {
            Asserts.test(avl.contains(data[i]));

        }
    }

    static void test2() {
        Integer data[] = new Integer[] {
                67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            avl.add(data[i]);
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(avl);
            System.out.println("---------------------------------------");
        }

        System.out.println("---------remove-----------------remove-------remove------");

        for (int i = 0; i < data.length; i++) {
            avl.remove(data[i]);
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(avl);
            System.out.println("---------------------------------------");
        }
    }

}
