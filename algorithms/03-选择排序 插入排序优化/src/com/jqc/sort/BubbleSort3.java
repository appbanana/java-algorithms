package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class BubbleSort3 extends Sort {

//     冒泡排序 针对部分有序优化
//    @Override
//    protected void sort() {
//        for (int end = array.length - 1; end > 0; end--) {
//            int sortedIndex = 1;
//            for (int begin = 0; begin < end; begin++) {
//                if (array[begin + 1] < array[begin]) {
//                    int temp = array[begin];
//                    array[begin] = array[begin + 1];
//                    array[begin + 1] = temp;
//                    sortedIndex = begin + 1;
//                }
//            }
//            end = sortedIndex;
//        }
//    }


//     冒泡排序 针对部分有序优化
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 0; begin < end; begin++) {
                if (cmp(begin + 1, begin) < 0) {
                    swap(begin, begin + 1);
                    sortedIndex = begin + 1;
                }
            }
            end = sortedIndex;
        }
    }
}
