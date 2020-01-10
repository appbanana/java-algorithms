package com.jqc.recursive;

import com.jqc.tools.Times;

/**
 * 斐波那契数列
 * @author appbanana
 * @date 2020/1/5
 */
public class Fib {

    public static void main(String[] args) {
        Fib fib = new Fib();
        int n = 60;
//        Times.test("简单粗暴版", () -> {
//            System.out.println(fib.fib0(n));
//        });

        Times.test("尾递归优化", () -> {
            System.out.println(fib.fib1(n));
        });

        Times.test("简单for循环", () -> {
            System.out.println(fib.fib2(n));
        });

        Times.test("循环数组", () -> {
            System.out.println(fib.fib3(n));
        });

        Times.test("循环数组改进版", () -> {
            System.out.println(fib.fib4(n));
        });

        Times.test("循环数组最终版", () -> {
            System.out.println(fib.fib5(n));
        });
    }

    /**
     * 斐波那契方法1: 简单粗暴版
     * @param n
     * @return
     */
    int fib0(int n) {
        if (n <= 2) return 1;
        return fib0(n - 1) + fib0(n - 2);
    }
    /**
     * 斐波那契优化方法2 尾递归优化
     * @param n
     * @return
     */
    int fib1(int n) {
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        return fib1(n, array);
    }

    int fib1(int n , int[] array) {
        if (n < 3) return array[n];

        if (array[n] == 0) {
            array[n] = fib1(n - 1, array) + fib1(n - 2, array);
        }
        return array[n];
    }

    /**
     * 斐波那契方法3 简单for循环来实现
     * @param n
     * @return
     */
    int fib2(int n) {
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        for (int i = 3; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[n];
    }
    /**
     * 斐波那契方法4: 使用循环数组
     * @param n
     * @return
     */
    int fib3(int n) {
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
        }
        return array[n % 2];
    }

    /**
     * 斐波那契方法4: 使用循环数组改进版
     * @param n
     * @return
     */
    int fib4(int n) {
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i & 1] = array[(i - 1) & 1] + array[(i - 2) & 1];
        }
        return array[n & 1];
    }

    /**
     * for循环版
     * @param n
     * @return
     */
    int fib5(int n) {
        int first = 1;
        int second = 1;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }
}
