package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class BubbleSort2 extends Sort {

     // 冒泡排序优化1 针对有序数组
//    @Override
//    protected void sort() {
//        for (int end = array.length - 1; end > 0; end--) {
//            boolean isSorted = true;
//            for (int begin = 0; begin < end; begin++) {
//                if (array[begin + 1] < array[begin]) {
//                    int temp = array[begin];
//                    array[begin] = array[begin + 1];
//                    array[begin + 1] = temp;
//                    isSorted = false;
//                }
//            }
//            if (isSorted) break;
//        }
//    }


    // 冒泡排序优化1 针对有序数组
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            boolean isSorted = true;
            for (int begin = 0; begin < end; begin++) {
                if (cmp(begin + 1, begin) < 0) {
                    swap(begin, begin + 1);
                    isSorted = false;
                }
            }
            if (isSorted) break;
        }
    }
}
