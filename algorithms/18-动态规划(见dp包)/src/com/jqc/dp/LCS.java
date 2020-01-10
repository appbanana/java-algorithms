package com.jqc.dp;

/**
 * 最长公共子序列
 * @author appbanana
 * @date 2020/1/10
 */
public class LCS {
    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5, 9, 10};
        int[] nums2 = {1, 4, 9, 10, 11, 15};
        System.out.println("lcs1(nums1, nums2) = " + lcs1(nums1, nums2));
        System.out.println("lcs2(nums1, nums2) = " + lcs2(nums1, nums2));
        System.out.println("lcs3(nums1, nums2) = " + lcs3(nums1, nums2));
        System.out.println("lcs4(nums1, nums2) = " + lcs4(nums1, nums2));


    }
    /**
     * 递归求最长公共子序列
     * 时间复杂度O(k) k = min(nums1.length, nums2.length)
     * 空间复杂度O(2^n) 当nums1.length = nums2.length
     * @param nums1 第一个数组
     * @param nums2 第二个数组
     * @return 最长公共子序列的长度
     */
    static int lcs1(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        return lcs(nums1, nums1.length, nums2, nums2.length);
    }

    static int lcs(int[] nums1, int i, int[] nums2, int j) {
        if (i == 0 || j == 0) return 0;

        if (nums1[i - 1] != nums2[j - 1]) {
            // 如果nums1[i-1] != nums[j-1], 那么就等于i-1或者j-1的前面的公共序列最大值
            return Math.max(lcs(nums1, i - 1, nums2, j),
                    lcs(nums1, i, nums2, j - 1));
        }
        // 如果nums1[i-1] == nums[j-1], 那么就等于他们的前面的公共序列加1
        return lcs(nums1, i - 1, nums2, j- 1) + 1;
    }

    /**
     * 非递归求最长公共子序列 时间复杂度O(n * m) n=nums1.length m = nums2.length
     * 空间复杂度O(n * m)
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[nums1.length][nums2.length];
    }

    /**
     * 非递归求最长公共子序列 优化方案1 利用滚动数组来实现
     * 时间复杂度O(n * m) n=nums1.length m = nums2.length  空间复杂度O(k) k = min(n, m)
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs3(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[] rowsNums = nums1, colsNums = nums2;
        if (nums1.length < nums2.length) {
            colsNums = nums1;
            rowsNums = nums2;
        }
        int[][] dp = new int[2][colsNums.length + 1];
        for (int i = 1; i <= rowsNums.length; i++) {
            for (int j = 1; j <= colsNums.length; j++) {
                if (rowsNums[i - 1] == colsNums[j - 1]) {
                    dp[i & 1][j] = dp[(i - 1) & 1][j - 1] + 1;
                }else {
                    dp[i & 1][j] = Math.max(dp[(i - 1) & 1][j], dp[i & 1][j - 1]);
                }
            }
        }

        return dp[rowsNums.length & 1][colsNums.length];
    }

    /**
     * 非递归求最长公共子序列 优化方案2 利用一维数组来实现
     * 时间复杂度O(n * m) n=nums1.length m = nums2.length  空间复杂度O(k) k = min(n, m)
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs4(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[] rowNums = nums1, colsNums = nums2;
        if (nums1.length < nums2.length) {
            // 长度短的当列 这样可以降低空间复杂度
            colsNums = nums1;
            rowNums = nums2;
        }
        int[] dp = new int[colsNums.length + 1];
        for (int i = 1; i <= rowNums.length; i++) {
            int cur = 0;
            for (int j = 1; j <= colsNums.length; j++) {
                int leftTop = cur;
                cur = dp[j];
                if (rowNums[i - 1] == colsNums[ j - 1]) {
                    dp[j] = leftTop + 1;
                }else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }
        return dp[colsNums.length];
    }
}
