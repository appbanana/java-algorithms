package com.jqc;

import com.jqc.tools.Integers;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Integer[] array = Integers.random(10, 0, 100);
        System.out.println(Arrays.toString(array));
        selectionSort(array);
        System.out.println(Arrays.toString(array));

    }

    /**
     * 冒泡排序 最原始的 未经优化的
     * @param array 待排序数组
     */
    static void bubbleSort1(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 0; begin < end; begin++) {
                if (array[begin + 1] < array[begin]) {
                    // 如果后一个元素比前一个小 交换顺序
                    int temp = array[begin];
                    array[begin] = array[begin + 1];
                    array[begin + 1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序优化1 针对有序数组
     * @param array
     */
    static void bubbleSort2(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            boolean isSorted = true;
            for (int begin = 0; begin < end; begin++) {
                if (array[begin + 1] < array[begin]) {
                    int temp = array[begin];
                    array[begin] = array[begin + 1];
                    array[begin + 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) break;
        }
    }

    /**
     * 冒泡排序 针对部分有序优化
     * @param array 待排数组
     */
    static void bubbleSort3(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 0; begin < end; begin++) {
                if (array[begin + 1] < array[begin]) {
                    int temp = array[begin];
                    array[begin] = array[begin + 1];
                    array[begin + 1] = temp;
                    sortedIndex = begin + 1;
                }
            }
            end = sortedIndex;
        }
    }

    static void selectionSort(Integer[] array) {
        // 循环次数
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                // 每一轮中找出一个最大值的索引
                if (array[begin] > array[maxIndex]) {
                    maxIndex = begin;
                }
            }

            // 最大值不是最后一个元素, 则需要将最大值和最后元素进行交换
            if (maxIndex != end) {
                int temp = array[end];
                array[end] = array[maxIndex];
                array[maxIndex] = temp;
            }
        }
    }


}
