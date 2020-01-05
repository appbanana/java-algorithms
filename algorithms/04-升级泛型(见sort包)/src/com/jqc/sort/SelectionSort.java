package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class SelectionSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        // 循环次数
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                // 每一轮中找出一个最大值的索引
                if (cmp(begin, maxIndex) > 0) {
                    maxIndex = begin;

                }
            }

            // 最大值不是最后一个元素, 则需要将最大值和最后元素进行交换
            if (maxIndex != end) {
                swap(maxIndex, end);
            }
        }
    }
}
