package com.jqc.recursive;

/**
 * 汉若塔问题
 * @author appbanana
 * @date 2020/1/5
 */
public class Hanoi {

    public static void main(String[] args) {
        new Hanoi().hanoi(10, "A", "B", "C");

    }
    /**
     * 将n个碟子移动到p3柱子上
     * @param n 碟子数量
     * @param p1 柱子1
     * @param p2 中间柱子
     * @param p3 目标柱子
     */
    void hanoi(int n, String p1, String p2, String p3) {
        if (n == 1) {
            moiveTo(n, p1, p3);
            return;
        }

        // 下面的方法太巧妙了, 一般人都想不到 杰哥牛逼
        // 将n- 1个碟子移动到到p2柱子上
        hanoi(n - 1, p1, p3, p2);
        // 将最大的碟子移动到p3目标柱子上
        moiveTo(n, p1, p3);
        // 再将刚才的n - 1个碟子移动到p3柱子上
        hanoi(n - 1, p2, p1, p3);
    }

    void moiveTo(int num, String from, String to) {
        System.out.println("将" + num + "号盘子从" + from + "移动到" + to);
    }

}
