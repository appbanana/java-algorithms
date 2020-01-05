package com.jqc.sort;

/**
 * @author appbanana
 * 归并排序
 * @date 2019/12/25
 */
public class MergeSort<T extends Comparable<T>> extends Sort<T> {
    private T[] leftArray;

    @Override
    protected void sort() {
        leftArray = (T[]) new Comparable[array.length >> 1];
        sort(0, array.length);
    }

    /**
     * [begin, end) 范围的数据把元素分割成单个元素 然后对数据进行归排序
     * @param begin 开始点
     * @param end 结束点
     */
    private void sort(int begin, int end) {
        // 如果里面只有一个元素 直接返回
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        // 拆分[begin, mid)范围内的分元素
        sort(begin, mid);
        // 拆分[mid, end)范围内的分元素
        sort(mid, end);
        // 元素分完后再归并
        merge(begin, mid, end);
    }

    /**
     * 将 [begin, mid) 和 [mid, end) 范围的序列合并成一个有序序列
     * @param begin
     * @param mid
     * @param end
     */
    private void merge(int begin, int mid, int end) {
        // 左边一半数组起始索引和结束索引
        int li = 0, le = mid - begin;
        // 右边一半数组的起始索引和结束索引
        int ri = mid, re = end;
        // 数组索引
        int ai = begin;

        // 备份左边数组
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin + i];
        }
        // 如果左边先结束,右边的不用动,直接结束就可以
        // 如果左右交替加入 则会进入下面
        while (li < le) {
            if (ri < re && cmpElement(array[ri], leftArray[li]) < 0) {
//                array[ai] = array[ri];
//                ri++;
//                ai++;
                // 上面三句可以整合成一句
                array[ai++] = array[ri++];
            }else {
                array[ai++] = leftArray[li++];
            }
        }
    }
}

