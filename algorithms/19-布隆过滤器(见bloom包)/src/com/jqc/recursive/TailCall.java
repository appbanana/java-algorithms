package com.jqc.recursive;

/**
 * 尾递归优化 尾递归不好想
 * @author appbanana
 * @date 2020/1/5
 */
public class TailCall {

    public static void main(String[] args) {
        System.out.println(fib(10));
        System.out.println(factorial(5));
    }

    /**
     * 斐波那契数列尾递归优化
     * @param n
     * @return
     */
    static int fib(int n) {
        return fib(n, 1, 1);
    }

    static int fib(int n, int first, int second) {
        if (n <= 2) return second;
        return fib(n - 1, second, first + second);
    }

    /**
     * 阶乘尾递归优化
     * @param n
     * @return
     */
    static int factorial(int n) {
        return factorial(n, 1);
    }

    static int factorial(int n, int result) {
        if (n == 1) return result;
        return factorial(n -1, n * result);
    }
}
