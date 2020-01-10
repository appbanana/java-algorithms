package com.jqc;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class BinarySearch {
    public static int indexOf(Integer[] array, int element) {
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (element == array[mid]) {
                return mid;
            }else if (element > array[mid]) {
                begin = mid + 1;
            }else {
                end = mid;
            }
        }
        return -1;
    }
}
