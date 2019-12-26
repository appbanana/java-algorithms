package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/12/25
 */

public class InsertionSort1<T extends Comparable<T>> extends Sort<T> {

    /**
     * 思路: 前面的已经排好序, 后面插入的依次和前面的元素比较 如果比前面的元素小 交换位置
     */
    @Override
    protected void sort() {

        for (int begin = 1; begin < array.length; begin++) {
            int curr = begin;
            while (curr > 0 && cmp(curr, curr - 1) < 0) {
                // 如果当前元素比前一个元素小 交换位置
                swap(curr, curr - 1);
                curr--;
            }
        }
    }

}
