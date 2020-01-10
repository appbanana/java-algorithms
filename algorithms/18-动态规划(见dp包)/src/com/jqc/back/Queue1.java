package com.jqc.back;

/**
 * 八皇后问题
 * @author appbanana
 * @date 2020/1/5
 */
public class Queue1 {
    public static void main(String[] args) {
//        new Queue().placeQueue(4);
        new Queue1().placeQueue(8);
    }

    // 索引代表行号 元素代表列号
    int[] queens;
    // 标志某一列是否有皇后
    boolean[] cols;
    // 标志某一斜线上是否皇后(从左上角--->到右下角)
    boolean[] leftTop;
    // 标志某一斜线上是否有皇后(从右上角---> 到左下角)
    boolean[] rightTop;
    // 一共有多少种方法
    int ways;

    void placeQueue(int n) {
        if (n < 1) return;
        queens = new int[n];
        cols = new boolean[n];
        // 从左上角到右下角  一共有2*n - 1条
        leftTop = new boolean[(n << 1) - 1];
        // 同理 从右上角到左下角 一共也有2 * n - 1条
        rightTop = new boolean[leftTop.length];
        // 先放第0行
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法");
    }

    /**
     * 从row行开始摆放皇后
     * @param row
     */
    void  place(int row) {
        if (row == cols.length) {
            // 如果最后能摆到最后一行, 说明此中摆法是可以的 way+1 统计总共方法数
            ways++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            // cols[col] 这列不为0 说明这列有皇后
            if (cols[col]) continue;
            // 左上到右下对角线索引
            int ltIndex = row - col + cols.length - 1;
            if (leftTop[ltIndex]) continue;
            // 从右上到左下 对角线索引
            int rtIndex = row + col;
            if (rightTop[rtIndex]) continue;
            // 这一行的皇后在col列上
            queens[row] = col;
            // 标记这一列有皇后
            cols[col] = true;
            // 左上到右下对角线数组中, 索引为ltIndex对角线上有皇后
            leftTop[ltIndex] = true;
            // 右上到左下对角线数组中, 索引为rtIndex对角线上有皇后
            rightTop[rtIndex] = true;
            // 开始摆放下一行
            place(row + 1);

            // 重置
            cols[col] = false;
            leftTop[ltIndex] = false;
            rightTop[rtIndex] = false;

        }
    }


    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (queens[row] == col) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }
}
