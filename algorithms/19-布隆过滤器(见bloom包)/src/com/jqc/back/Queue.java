package com.jqc.back;

import java.util.Map;

/**
 * 八皇后问题
 * @author appbanana
 * @date 2020/1/5
 */
public class Queue {
    public static void main(String[] args) {
//        new Queue().placeQueue(4);
        new Queue().placeQueue(8);
    }


    // 索引代表行号 元素代表列号
    int[] cols;
    // 一共有多少种方法
    int ways;

    void placeQueue(int n) {
        if (n < 1) return;
        cols = new int[n];
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法");
    }

    /**
     * 从row行开始摆放皇后
     * @param row
     */
    void  place(int row) {
        if (row == cols.length) {
            ways++;
            show();
            return;
        }
        // 遍历这一行中 哪些列可以放皇后
        for (int col = 0; col < cols.length; col++) {
            if (isValid(row, col)) {
                // 可以放皇后 cols索引位置代表行， 值代表列，cols[row]代表row行，col列有皇后
                cols[row] = col;
                // 放完皇后后 下一行继续
                place(row + 1);
            }
        }
    }

    /**
     * 判断row行col列是否可以摆放皇后
     * @param row
     * @param col
     * @return
     */
    boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            // 如果这一列上已经有皇后 无须爱判断 肯定不能再放皇后 直接返回false
            if (cols[i] == col) return false;
            // cols[i]是皇后所在的行列，皇后斜线上也不能放， 转换为数学问题就是斜率为正负1的直线上不能放,
            // 斜率为正负1，所以绝对值是相等的
            if (row - i == Math.abs(col - cols[i])) return false;
        }
        return true;
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                // 皇后的位置打印1， 其他位置打印0
                if (cols[row] == col) {
                    System.out.print("1 ");
                }else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }
}
