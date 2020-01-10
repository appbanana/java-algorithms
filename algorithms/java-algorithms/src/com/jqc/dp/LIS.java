package com.jqc.dp;

/**
 * 最长上升子序列
 * @author appbanana
 * @date 2020/1/10
 */
public class LIS {
    public static void main(String[] args) {
        int[] nums = {10, 2, 2, 5, 1, 7, 101, 18};
        System.out.println("lengthOfLIS1(nums) = " + lengthOfLIS1(nums));
        System.out.println("lengthOfLIS2(nums) = " + lengthOfLIS2(nums));
        System.out.println("lengthOfLIS3(nums) = " + lengthOfLIS3(nums));
    }

    /**
     * 动态规划求最长上升子序列  时间复杂度O(n^2) 空间复杂度O(n)
     * @param nums
     * @return
     */
    static int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[j] + 1, dp[i]);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * 牌顶  类比扑克牌 大的在上面 小的上面
     * 时间复杂度O(Kn) 空间时间复杂度 O(n)  K为最长上升子序列长度, n为元素个数
     * @param nums
     * @return
     */
    static int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        // 牌堆的数量
        int len = 0;
        int[] top = new int[nums.length];
        for (int num : nums) {
            int j = 0;
            // 遍历所有的牌堆
            while (j < len) {
                if (top[j] >= num) {
                    // 如果堆顶元素比当前元素大，则覆盖当前堆顶元素
                    top[j] = num;
                    break;
                }
                j++;
            }
            // 新建堆
            if (j == len) {
                len++;
                top[j] = num;
            }
        }
        return len;
    }

    /**
     * 这个方法是对上一个方法的改进  这次利用二分搜索来找牌牌堆的位置
     * 时间复杂度O(nlogK) 空间复杂度O(n) K为最长上升子序列长度, n为元素个数
     * 牌顶  类比扑克牌 大的在上面 小的上面
     * @param nums
     * @return
     */
    static int lengthOfLIS3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        // 牌堆的数量
        int len = 0;
        int[] top = new int[nums.length];
        for (int num : nums) {
            int begin = 0;
            int end = len;
            // 利用二分搜索查找牌堆位置
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (num > top[mid]) {
                    begin = mid + 1;
                }else {
                    end = mid;
                }
            }
            // begin就是我们要找的位置 插入堆中 (ps: 这起始是用数组元素覆盖来模拟堆的思想)
            top[begin] = num;
            // 新建堆
            if (begin == len) len++;
        }
        return len;
    }


}
