package com.jqc;

import com.jqc.printer.BinaryTrees;
import com.jqc.tree.BinarySearchTree;

public class Main {

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };

        for (int i = 0; i < data.length; i++) {
            tree.add(data[i]);
        }

        BinaryTrees.print(tree);
    }



}
