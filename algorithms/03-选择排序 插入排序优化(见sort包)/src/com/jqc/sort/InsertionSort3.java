package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/12/25
 */

public class InsertionSort3 extends Sort {

    /**
     * 插入排序 优化思路1：利用二分搜索先找到带插入元素的位置，然后把元素插入到找到的位置
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            insert(begin, indexOf(begin));
        }
    }

    /**
     * 插入元素
     * @param source 当前索引
     * @param dest  二分查找到的 要插入的位置
     */
    private void insert(int source, int dest) {
        int cur = array[source];
        for (int i = source; i > dest; i--) {
            array[i] = array[i - 1];
        }
        array[dest] = cur;
    }
    /**
     * 二分查找带插入元素的位置
     * @param index 当前索引
     * @return 返回带插入元素的索引
     */
    private int indexOf(int index) {
        int begin = 0;
        int end = index;
        int element = array[index];
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (element < array[mid]) {
                end = mid;
            }else {
                begin = mid + 1;
            }
        }
        return begin;
    }

}
