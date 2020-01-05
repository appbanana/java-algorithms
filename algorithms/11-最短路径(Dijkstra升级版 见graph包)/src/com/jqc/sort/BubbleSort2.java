package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class BubbleSort2<T extends Comparable<T>> extends Sort<T> {

    // 改进方案一: 这种改进主要针对已经排好序(eg:升序), 扫描一遍直接结束
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


    // 改进方案一: 这种改进主要针对已经排好序(eg:升序), 扫描一遍直接结束
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
