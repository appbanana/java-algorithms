package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/12/25
 */

public class InsertionSort2<T extends Comparable<T>> extends Sort<T> {

    /**
     * 思路: 优化方案一 挪动替换之前的交换 如果当前元素比前一个元素小 将其位置后移
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            T v = array[cur];
            while (cur > 0 && cmpElement(v, array[cur - 1]) < 0) {
                // 如果当前元素比前一个元素小 将前一个元素后移一位
                array[cur] = array[cur - 1];
                cur--;
            }
            array[cur] = v;
        }
    }
}

