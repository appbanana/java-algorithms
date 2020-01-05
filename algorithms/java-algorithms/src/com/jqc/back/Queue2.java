package com.jqc.back;

/**
 * 八皇后问题
 * @author appbanana
 * @date 2020/1/5
 */
public class Queue2 {
    public static void main(String[] args) {
        new Queue2().place8Queens();
    }

    // 索引代表行号 元素代表列号
    int[] queens;
    // 标志某一列是否有皇后 一个字节=8bit 每一个bit来标记是否有皇后
    byte cols;
    // 标志某一斜线上是否皇后(从左上角--->到右下角) 8皇后问题 总共有15条对角线, 因此需要2个字节就可以
    short leftTop;
    // 标志某一斜线上是否有皇后(从右上角---> 到左下角) 与上同理
    short rightTop;
    // 一共有多少种方法
    int ways;

    void place8Queens() {
        queens = new int[8];
        // 先放第0行
        place(0);
        System.out.println("皇后一共有" + ways + "种摆法");
    }

    /**
     * 从row行开始摆放皇后
     * @param row
     */
    void  place(int row) {
        if (row == 8) {
            // 如果最后能摆到最后一行, 说明此中摆法是可以的 way+1 统计总共方法数
            ways++;
            show();
            return;
        }
        for (int col = 0; col < 8; col++) {
            int cv = 1 << col;
            // 这一位不为0 说明这这个位的所在位置有皇后
            if ((cols & cv) != 0) continue;
            // 左上到右下对角线索引
            int lv =  1 << (row - col + 7);
            if ((leftTop & lv) != 0) continue;
            // 从右上到左下 对角线索引
            int rv = 1 << (row + col);
            if ((rightTop & rv) != 0) continue;
            // 这一行的皇后在col列上
            queens[row] = col;
            // 标记这一列有皇后
            cols |= cv;
            // 左上到右下对角线数组中, 索引为ltIndex对角线上有皇后
            leftTop |= lv;
            // 右上到左下对角线数组中, 索引为rtIndex对角线上有皇后
            rightTop |= rv;
            // 开始摆放下一行
            place(row + 1);

            // 重置
            cols ^= cv;
            leftTop ^= lv;
            rightTop ^= rv;

        }
    }


    void show() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
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
