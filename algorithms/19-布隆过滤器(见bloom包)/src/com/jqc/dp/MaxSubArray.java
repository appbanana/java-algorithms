package com.jqc.dp;

/**
 * 求最大连续子序列和  动态规划实现 对比分治算法的解法(见divide包内)
 * @author appbanana
 * @date 2020/1/10
 */
public class MaxSubArray {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4 };
        System.out.println("maxSubArray1(nums) = " + maxSubArray1(nums));
        System.out.println("maxSubArray2(nums) = " + maxSubArray2(nums));

    }

    /**
     * 求最大连续子序列之和 时间复杂度O(n) 空间复杂度O(n)
     * @param array
     * @return
     */
    static int maxSubArray1(int[] array) {
        if (array == null || array.length == 0) return 0;
        int[] dp = new int[array.length];
        int max = dp[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            if (dp[i - 1] < 0) {
                dp[i] = array[i];
            }else {
                dp[i] = dp[i - 1] + array[i];
            }
            max = Math.max(dp[i], max);
        }

        return max;
    }

    /**
     * 求最大连续子序列之和 时间复杂度O(n) 空间复杂度O(1)
     * @param array
     * @return
     */
    static int maxSubArray2(int[] array) {
        if (array == null || array.length == 0) return -1;
        int max = array[0];
        int dp = max;
        for (int i = 0; i < array.length; i++) {
            if (dp <= 0) {
                dp = array[i];
            }else {
                dp += array[i];
            }
            max = Math.max(dp, max);
        }
        return max;
    }
}
