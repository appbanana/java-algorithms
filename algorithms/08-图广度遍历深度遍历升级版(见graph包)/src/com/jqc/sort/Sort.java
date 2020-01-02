package com.jqc.sort;

import com.jqc.Student;

import java.text.DecimalFormat;

/**
 * @author appbanana
 * @date 2019/11/6
 */
public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>>{
    //
    protected T[] array;
    // 记录排序所花时间
    private long time;
    // 元素交换次数
    private int swapCount;
    // 比较次数
    private int cmpCount;

    private DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(T[] array) {
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
        T temp = array[index1];
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
        return array[index1].compareTo(array[index2]);
    }

    /**
     * 直接比较两个元素大小
     * @param e1 元素1
     * @param e2 元素2
     * @return
     */
    protected int cmpElement(T e1, T e2) {
        cmpCount++;
        return e1.compareTo(e2);
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";

    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }

    private boolean isStable() {
        if (this instanceof RadixSort) return true;
        if (this instanceof CountingSort) return true;
        if (this instanceof ShellSort) return false;
        if (this instanceof SelectionSort) return false;
        Student[] students = new Student[20];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(i * 10, 10);
        }
        sort((T[]) students);
        for (int i = 1; i < students.length; i++) {
            int score = students[i].score;
            int prevScore = students[i - 1].score;
            if (score != prevScore + 10) return false;
        }
        return true;
    }
}
