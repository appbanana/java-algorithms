package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class BubbleSort1 extends Sort {

    // 冒泡排序的思路(升序): 如果后一个元素比前一个小,就交换顺序
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

    // 冒泡排序的思路(升序): 如果后一个元素比前一个小,就交换顺序
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
