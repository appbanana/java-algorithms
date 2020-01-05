package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class HeapSort<T extends Comparable<T>> extends Sort<T> {
    private int heapSize;

    @Override
    protected void sort() {
        // 堆排序 实际上是对选择排序的一种改进
        heapSize = array.length;

        // 先批量建堆
        for (int i = (heapSize >> 1) -1; i >= 0; i--) {
            shiftDown(i);
        }
        // 交换第一个元素和最后一个元素的位置 shiftDown
        while (heapSize > 1) {
            swap(0, --heapSize);
            shiftDown(0);
        }

    }

    private void shiftDown(Integer index) {
        T element = array[index];
        int half = heapSize >> 1;

        while (index < half) {
            // index必须是非叶子节点
            int childIndex = (index << 1) + 1;
            T child = array[childIndex];

            int rightIndex = childIndex + 1;
            // 右子节点值大于左子节点值
            if (rightIndex < heapSize && cmpElement(array[rightIndex],  child) > 0) {
                childIndex = rightIndex;
                child = array[childIndex];
            }

            // 大于最大节点的值 直接结束
            if (cmpElement(element, child) >= 0) break;

            //
            array[index] = child;
            index = childIndex;

        }
        array[index] = element;
    }
}
