package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/12/25
 */

public class InsertionSort3<T extends Comparable<T>> extends Sort<T> {

    /**
     * 思路: 优化方案二 采用二分搜索查找待插入的位置,相比上一种优化, 是减少了比较次数, 挪动次数还是一样的
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
        T cur = array[source];
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
        T element = array[index];
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (cmpElement(element, array[mid]) < 0) {
                end = mid;
            }else {
                begin = mid + 1;
            }
        }
        return begin;
    }

}
