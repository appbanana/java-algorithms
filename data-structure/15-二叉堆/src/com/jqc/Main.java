package com.jqc;

import com.jqc.heap.BinaryHeap;
import com.jqc.printer.BinaryTrees;
import com.jqc.tree.BinaryTree;

import java.util.Comparator;

public class Main {


    public static void main(String[] args) {

//        test1();
//        test2();
//        test3();

        test4();
    }

    static void test1() {

        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        BinaryTrees.println(heap);
        heap.remove();
        BinaryTrees.println(heap);

        System.out.println(heap.replace(20));
        BinaryTrees.println(heap);
    }

    static void test2() {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data);
        BinaryTrees.println(heap);

    }
    static void test3() {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        BinaryTrees.println(heap);
    }

    static void test4() {
        // Top K 问题
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        // 找出最大的前k个数
        int k = 3;
        Integer[] data = {51, 30, 39, 92, 74, 25, 16, 93,
                91, 19, 54, 47, 73, 62, 76, 63, 35, 18,
                90, 6, 65, 49, 3, 26, 61, 21, 48};

        for (int i = 0; i < data.length; i++) {
            if (i < k) {
                heap.add(data[i]);
            }else {
                if (data[i] > heap.get()) {
                    heap.replace(data[i]);
                }
            }
        }
        BinaryTrees.print(heap);
    }

}
