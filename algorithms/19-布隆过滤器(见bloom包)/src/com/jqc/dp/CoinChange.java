package com.jqc.dp;

import java.util.Arrays;

/**
 * 零钱兑换 eg: 假设有25分、20分、5分、1分的硬币，现要找给客户41分的零钱，如何办到硬币个数最少
 * @author appbanana
 * @date 2020/1/10
 */
public class CoinChange {
    public static void main(String[] args) {
        System.out.println("coins1(41) = " + coins1(41));
        System.out.println("coins2(41) = " + coins2(41));
        System.out.println("coins3(41) = " + coins3(41));
        System.out.println("coins4(41) = " + coins4(41));
        System.out.println("coins5 = " + coins5(41, new int[]{1, 5, 20, 25}));
    }

    /**
     * 第一种解决方案 简单粗暴的方法 暴力递归
     * @param n 需要兑换的money
     * @return 最少兑换零钱的数量
     */
    static int coins1(int n) {
        // 如果n小于1的话, 返回整型最大值
        if (n < 1) return Integer.MAX_VALUE;
        if (n == 25 || n == 20 || n == 5 || n == 1) return 1;
        int min1 = Math.min(coins1(n - 25), coins1(n - 20));
        int min2 = Math.min(coins1(n - 5), coins1(n - 1));
        return Math.min(min1, min2) + 1;
    }

    /**
     * 方案2: 第一种方法有很多重复计算, 改进方法记忆化搜索,自顶向下的调用
     * @param n 需要兑换的money
     * @return 最少兑换零钱的数量
     */
    static int coins2(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        int[] faces = {1, 5, 20, 25};
        for (int face : faces) {
            if (n < face) break;
            dp[face] = 1;
        }
        return coins2(n, dp);
    }

    static int coins2(int n, int[] dp) {
        if (n < 1) return Integer.MAX_VALUE;
        if (dp[n] == 0) {
            int min1 = Math.min(coins2(n - 25, dp), coins2(n - 20, dp));
            int min2 = Math.min(coins2(n - 5, dp), coins2(n - 1, dp));
            dp[n] = Math.min(min1, min2) + 1;
        }
        return dp[n];
    }

    /**
     * 方案三 递推 自底向上调用 时间复杂度O(n) 空间复杂度O(n)
     * @param n 需要兑换的money
     * @return 最少兑换零钱的数量
     */
    static int coins3(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            int min = dp[i - 1];
            if (i >= 5) min= Math.min(dp[i - 5], min);
            if (i >= 20) min = Math.min(dp[i - 20], min);
            if (i >= 25) min = Math.min(dp[i - 25], min);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    /**
     * 方案四 跟方案三思路是一样的 只不过是可以打印出具体的方案
     * @param n
     * @return
     */
    static int coins4(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        int[] faces = new int[dp.length];
        for (int i = 1; i <= n; i++) {
            int min = dp[i - 1];
            faces[i] = 1;
            if (i >= 5 && dp[i - 5] < min) {
                min = dp[i - 5];
                faces[i] = 5;
            }
            if (i >= 20 && dp[i - 20] < min) {
                min = dp[i - 20];
                faces[i] = 20;
            }
            if (i >= 25 && dp[i - 25] < min) {
                min = dp[i - 25];
                faces[i] = 25;
            }
            dp[i] = min + 1;
//            print(faces, i);
        }
        print(faces, n);
        return dp[n];
    }

    static void print(int[] faces, int n) {
        System.out.print("[" + n + "] =");
        while (n > 0) {
            System.out.print(" " + faces[n]);
            n = n - faces[n];
        }
        System.out.println();
    }

    /**
     * 总结
     * 兑换零钱的通用实现
     * @param n
     * @param faces
     * @return
     */
    static int coins5(int n, int[] faces) {
        if (n < 1 || faces == null || faces.length == 0) return -1;
        int[] dp = new int[n + 1];
        Arrays.sort(faces);
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int face : faces) {
                // 如果当前money小于面值 后面的面值无需遍历, 直接跳出循环
                if (i < face) break;
                if (dp[i - face] < 0 || dp[i - face] >= min) continue;
                // 只有dp[i - face] < min时才有可能走到这里
                min = dp[i - face];
            }
            if (min == Integer.MAX_VALUE) {
                // 无法兑换直接返回-1
                dp[i] = -1;
            }else {
                dp[i] = min + 1;
            }
        }
        return dp[n];
    }
}
