package com.jqc.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author appbanana
 * 希尔排序
 * @date 2019/12/26
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T>  {
    @Override
    protected void sort() {
        // 希尔排序
        //  获取步长 (ps:希尔本人提出)
//        List<Integer> stepSequence = shellStepSequence();
        // 改进后的步长
        List<Integer> stepSequence = sedgewickStepSequence();
        for (Integer step:stepSequence) {
            // 遍历步长, 对列进行排序
            sort(step);
        }
    }

    /**
     * 分成step列进行排序
     * @param step
     */
    private void sort(Integer step) {
        // 第一层for循环对列进行遍历
        for (int col = 0; col < step; col++) {
            // col、col+step、col+2*step、col+3*step 第二层for循环相当于对row进行遍历
            for (int begin = col + step; begin < array.length; begin+=step) {
                int cur = begin;
                while (cur > col && cmp(cur, cur - step) < 0) {
                    // 当前元素与他的上面同列的数据进行比较
                    swap(cur, cur - step);
                    cur -= step;
                }
            }
        }
    }
    /**
     * 生成希尔步长数组 最坏时间复杂度为O(n^2)
     * @return 返回步长数组
     */
    protected List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList();
        int step = array.length;
        // step = step / 2
        while ((step >>=1) > 0) {
            stepSequence.add(step);
        }
        return stepSequence;
    }

    /**
     * 希尔步长改进版 最坏情况下时间复杂度为O(n^(4/3))  具体公式见img/01-希尔排序改进版公式.png
     * @return 步长序列
     */
    protected List<Integer> sedgewickStepSequence() {
        List<Integer> stepSequence = new LinkedList();
        int k = 0;
        int step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int)Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            }else {
                int pow1 = (int)Math.pow(2, (k - 1) >> 1);
                int pow2 = (int)Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            stepSequence.add(0, step);
            k++;
        }
        return stepSequence;
    }
}
