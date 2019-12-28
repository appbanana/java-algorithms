package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class BubbleSort1<T extends Comparable<T>> extends Sort<T> {

    // 思路:相邻两个元素比较, 如果前一个比后一个元素大,则交换位置(升序情况)
//    @Override
//    protected void sort() {
//        for (int end = array.length - 1; end > 0; end--) {
//            for (int begin = 0; begin < end; begin++) {
//                if (array[begin + 1] < array[begin]) {
//                    // 如果后一个元素比前一个小 交换顺序
//                    int temp = array[begin];
//                    array[begin] = array[begin + 1];
//                    array[begin + 1] = temp;
//                }
//            }
//        }
//    }

    // 思路:相邻两个元素比较, 如果前一个比后一个元素大,则交换位置(升序情况)
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 0; begin < end; begin++) {
                if (cmp(begin + 1, begin) < 0) {
                    swap(begin, begin + 1);
                }
            }
        }
    }
}
