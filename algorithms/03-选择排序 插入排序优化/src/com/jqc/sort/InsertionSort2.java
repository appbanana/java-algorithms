package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/12/25
 */

public class InsertionSort2 extends Sort {

    /**
     * 插入排序 优化思路1：在InsertionSort基础上，我先找到当前元素带插入的位置， 然后在放进去， 减少交换的次数
     */
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur =begin;
            int currElement = array[cur];
            while (cur > 0 && cmpElement(currElement, array[cur - 1]) < 0) {
                // 如果当前元素比前面的小， 把他前面的元素向后移动一位
                array[cur] = array[cur - 1];
                cur--;
            }
            // 把当前元素放到带插入的位置
            array[cur] = currElement;
        }
    }


}
