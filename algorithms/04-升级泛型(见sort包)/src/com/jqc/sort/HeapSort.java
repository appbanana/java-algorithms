package com.jqc.sort;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public class HeapSort<T extends Comparable<T>> extends Sort<T> {

    private int heapSize;

    @Override
    protected void sort() {
        heapSize = array.length;
        // (heapSize >> 1) - 1 是倒数第一个非叶子节点
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            // 下滤建堆
            siftDown(i);
        }

        while (heapSize > 1) {
            // 交换堆顶和堆尾元素
            swap(0, --heapSize);
            siftDown(0);
        }
    }

    private void siftDown(int index) {
        T element = array[index];
        int half = heapSize >> 1;
        while (index < half) {
            int childIndex = (index << 1) + 1;
            T child = array[childIndex];

            if (childIndex + 1 < heapSize && cmp(childIndex, childIndex + 1) < 0) {
                // 左子节点值小于右子节点的值
                childIndex = childIndex + 1;
                child = array[childIndex];
            }
            if (cmpElement(element, child) >= 0) break;

            array[index] = child;
            index = childIndex;
        }

        array[index] = element;

    }
}
