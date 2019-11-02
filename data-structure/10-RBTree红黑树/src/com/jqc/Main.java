package com.jqc;

import com.jqc.printer.BinaryTrees;
import com.jqc.tree.BinarySearchTree;
import com.jqc.tree.RBTree;

public class Main {

    public static void main(String[] args) {
        test(new RBTree<Integer>());
    }

    static void test(BinarySearchTree<Integer> tree) {
        Integer data[] = new Integer[] {
                67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
        };

        for (int i = 0; i < data.length; i++) {
            tree.add(data[i]);
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(tree);
            System.out.println("---------------------------------------");
        }

        System.out.println("---------remove-----------------remove-------remove------");

        for (int i = 0; i < data.length; i++) {
            tree.remove(data[i]);
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(tree);
            System.out.println("---------------------------------------");
        }
    }

}
