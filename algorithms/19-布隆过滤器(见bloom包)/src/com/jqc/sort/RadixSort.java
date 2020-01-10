package com.jqc.sort;

/**
 * @author appbanana
 * 基数排序
 * @date 2019/12/26
 */
public class RadixSort extends Sort<Integer>{

    @Override
    protected void sort() {
//        sort1();
        sort2();
    }

    /**
     * 基数排序的第一种思路
     * 时间复杂度 O(d *(n + k)) d为最大值的位数， n为元素个数， k为进制数
     * 空间复杂度 O(n + k) k为进制数
     */
    private void sort0() {
        // 找出最大值
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // 个位数: array[i] / 1 % 10 = 3
        // 十位数：array[i] / 10 % 10 = 9
        // 百位数：array[i] / 100 % 10 = 5
        // 千位数：array[i] / 1000 % 10 = ...
        for (int divider = 1; divider <= max; divider *= 10) {
            countSort(divider);
        }
    }

    /**
     * 计数排序
     * @param devider 权重
     */
    private void countSort(int devider) {
        // 开辟内存空间 总共最多有10个数 所以开辟10个内存空间
        int[] counts = new int[10];

        // 统计每个整数(个位, 十位, 百位...)出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] / devider % 10]++;
        }

        // 统计整数累计出现的次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        int[] newArray = new int[array.length];
        for (int i = array.length; i >= 0; i--) {
            newArray[--counts[array[i] / devider % 10]] = array[i];
        }

        // 将排好序的数组放到原数组中
        for (int i = 0; i < array.length; i++) {
            array[i] = newArray[i];
        }
    }

    /**
     * 基数排序的第二种思路 使用二维数组来完成
     * 时间复杂度 O（kn + k）  k为多少进制，本题中使用时10进制， n为元素个数
     * 空间复杂度 O（dn） d为最大值的位数，n为元素个数
     */
    private void sort2() {
        // 找出最大值
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // 桶数组
        int[][] buckets = new int[10][array.length];
        // 个位数或者十位数或者百位数的数字对应数组中的索引
        int[] bucketsSize = new int[buckets.length];
        for (int diveider = 1; diveider <= max; diveider *= 10) {
            for (int i = 0; i < array.length; i++) {
                int num = array[i] / diveider % 10;
                // 先拿到num号桶，通过 bucketsSize[num]在拿到num号桶中元素的个数
                buckets[num][bucketsSize[num]++] = array[i];
            }
            int index = 0;
            // 遍历 依次拿到0~9号桶中元素
            for (int i = 0; i < buckets.length; i++) {
                for (int j = 0; j < bucketsSize[i]; j++) {
                    array[index++] = buckets[i][j];
                }
                // bucketsSize 清零 待下一次使用
                bucketsSize[i] = 0;
            }
        }
    }
}
