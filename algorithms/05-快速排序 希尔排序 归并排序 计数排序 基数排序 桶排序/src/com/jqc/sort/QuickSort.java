package com.jqc.sort;

/**
 * @author appbanana
 * 快速排序
 * @date 2019/12/25
 */
public class QuickSort <T extends Comparable<T>> extends Sort<T> {

    /**
     * 快速排序的本质：逐渐将每一个元素转成轴点元素，当每一个元素都当成轴点元素时，就排好序
     */

    @Override
    protected void sort() {
        // 快速排序 时间复杂度 O(nlogn)  空间复杂度O(logn), 实际上就是最大递归深度（n为元素个数）
        sort(0, array.length);
    }

    /**
     * [begin, end) 前闭后开，在这个范围内快速排序, 这样设计好处end - begin 就是元素个数
     * @param begin 开始位置
     * @param end 结束位置
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;
        // 寻找轴点位置 扫描元素 时间复杂度O(n)
        int mid = pivotIndex(begin, end);
        // 递归排序
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private  int pivotIndex(int begin, int end) {
        // 随机交换begin位置和(begin + 随机数(0, end - begin))的值, 避免最坏情况出现
        swap(begin, begin + (int)(Math.random() * (end - begin)));
        // 暂时选begin位置的原始为轴点元素
        T pivot = array[begin];
        // 因为最后一个元素是开区间,索引娶不到，所以需要先减1, 让元素指向最后一个元素
        end--;

        while (begin < end) {
            while (begin < end) {
                // 先扫描右边的元素
                if (cmpElement(pivot, array[end]) < 0) {
                    // 右边元素大于轴点元素, end减1
                    end--;
                }else {
                    // 右边元素小于轴点元素 否则把end位置的元素覆盖掉begin位置, 而且begin加1
                    array[begin++] = array[end];
                    // 一旦出现这种情况 需要中断while循环 接下来需要从左开始扫描
                    break;
                }
            }

            while (begin < end) {
                // 扫描左边的元素
                if (cmpElement(pivot, array[begin]) > 0) {
                    // 左边元素小于轴点元素
                    begin++;
                }else {
                    // 左边元素大于等于轴点元素
                    array[end--] = array[begin];
                    // 一旦出现这种情况, 中断while循环 在从右边扫描 如此交替
                    break;
                }
            }
        }
        array[begin] = pivot;
        return begin;
    }
}
