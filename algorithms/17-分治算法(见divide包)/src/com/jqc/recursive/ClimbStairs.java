package com.jqc.recursive;

/**
 * 爬楼梯问题
 * @author appbanana
 * @date 2020/1/5
 */
public class ClimbStairs {

    public static void main(String[] args) {
        System.out.println(climbStairs(10));
    }

    /**
     * 爬楼梯跟斐波那契数列很像
     * @param n
     * @return
     */
    static int climbStairs(int n) {
        if (n <= 2) return n;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }
}
