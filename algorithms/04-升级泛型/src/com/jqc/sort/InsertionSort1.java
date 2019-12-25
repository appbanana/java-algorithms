package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/12/25
 */

public class InsertionSort1<T extends Comparable<T>> extends Sort<T> {
    /**
     * 插入排序 思路：假设第一个先排好序， 如果后面的比前面的元素大，就交换位置
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int curr =begin;
            while (curr > 0 && cmp(curr, curr - 1) < 0) {
                swap(curr - 1, curr);
                curr--;
            }
        }
    }
}
