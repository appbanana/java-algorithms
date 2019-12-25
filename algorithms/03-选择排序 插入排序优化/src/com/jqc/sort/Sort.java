package com.jqc.sort;

import java.text.DecimalFormat;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public abstract class Sort implements Comparable<Sort>{
    //
    protected Integer[] array;
    // 记录排序所花时间
    private long time;
    // 元素交换次数
    private int swapCount;
    // 比较次数
    private int cmpCount;

    private DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(Integer[] array) {
        this.array = array;
        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    @Override
    public int compareTo(Sort o) {
        int result = (int)(time - o.time);
        if (result != 0) return result;
        result = swapCount - o.swapCount;
        if (result != 0) return result;

        return cmpCount - o.cmpCount;
    }

    /**
     * 交给子类去实现
     */
    protected void sort() {}

    /**
     * 传索引 交换两个元素
     * @param index1 索引1
     * @param index2 索引2
     */
    protected void swap(int index1, int index2) {
        swapCount++;
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * 传索引 比较这两个元素大小
     * @param index1 索引1
     * @param index2 索引2
     * @return
     */
    protected int cmp(int index1, int index2) {
        cmpCount++;
        return array[index1] - array[index2];
    }

    /**
     * 直接比较两个元素大小
     * @param e1 元素1
     * @param e2 元素2
     * @return
     */
    protected int cmpElement(int e1, int e2) {
        cmpCount++;
        return e1 - e2;
    }

    @Override
    public String toString() {
        String timeStr = "耗时:" + (time / 1000.0) + "s(" + time + "ms)";
        String cmpCountStr = "比较: " + numberString(cmpCount);
        String swapStr = "交换:" + numberString(swapCount);
        return "【" + getClass().getSimpleName() + "】\n"
                + timeStr + "\t"
                + cmpCountStr + "\t"
                + swapStr + "\t";
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }
}
