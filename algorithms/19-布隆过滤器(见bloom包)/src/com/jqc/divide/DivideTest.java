package com.jqc.divide;

/**
 * 求最大连续子序列和
 * @author appbanana
 * @date 2020/1/5
 */
public class DivideTest {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4 };
        System.out.println("maxSubarray1 = " + maxSubarray1(nums));
        System.out.println("maxSubarray2= " + maxSubarray2(nums));
        System.out.println("maxSubarray3(nums) = " + maxSubarray3(nums));
    }

    /**
     * 暴力求解  时间复杂度O(n^3) 空间复杂度O(1)
     * @param nums
     * @return
     */
    static int maxSubarray1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    /**
     * 暴力求解的优化 时间复杂度O(n^2) 空间复杂度O(1)
     * @param nums
     * @return
     */
    static int maxSubarray2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(sum, max);
            }
        }
        return max;
    }

    /**
     * 高级搞法  分治算法来求解
     * 空间复杂度O(logn) 时间复杂度O(nlogn)
     * @param nums
     * @return
     */
    static int maxSubarray3(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        return maxSubArray(nums, 0, nums.length);
    }

    static int maxSubArray(int[] nums, int begin, int end) {
        if (end - begin < 2) return nums[begin];

        int mid = (begin + end) >> 1;
        int leftSum = nums[mid - 1];
        int leftMax = leftSum;
        // 求出左边的做大值
        for (int i = mid - 2; i >= begin; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax, leftSum);
        }

        int rightSum = nums[mid];
        int rightMax = rightSum;
        for (int i = mid + 1; i < end; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax, rightSum);
        }
        return Math.max(leftMax + rightMax, (
                Math.max(maxSubArray(nums, begin, mid), maxSubArray(nums, mid, end))
        ));
    }
}
