package com.jqc.greedy;

import java.util.Arrays;

/**
 * 最简单的海盗问题 看看最多能放多少件古董
 * @author appbanana
 * @date 2020/1/5
 */
public class Pirate {

    public static void main(String[] args) {
        // 古董的重量
        int[] weights = {3, 5, 4, 10, 7, 14, 2, 11};
        int capacity = 30;
        int count = 0, weight = 0;
        Arrays.sort(weights);
        System.out.println(Arrays.toString(weights));

        for (int i = 0; i < weights.length; i++) {
            int newWeight = weight + weights[i];
            if (newWeight > capacity) break;
            count++;
            weight = newWeight;
        }

        System.out.println("一共选了" + count + "件古董");
    }
}
