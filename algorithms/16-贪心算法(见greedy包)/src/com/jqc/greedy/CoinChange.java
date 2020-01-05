package com.jqc.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 贪心算法解决换零钱问题 零钱数量最少
 * @author appbanana
 * @date 2020/1/5
 */
public class CoinChange {

    public static void main(String[] args) {
//        coinChange();
//        coinChange(new Integer[] {25, 10, 5, 1}, 41);

        coinChange(new Integer[] {25, 20, 5, 1}, 41);
    }

    static void coinChange() {
        int[] faces = {25, 5, 20, 1};
        int money = 41, count = 0;
        Arrays.sort(faces);
        for (int i = faces.length - 1; i >= 0; i--) {
            while (money >= faces[i]) {
                System.out.println("面值" + faces[i]);
                money -= faces[i];
                count++;
            }
        }
        if (money == 0) {
            System.out.println("找零钱数量" + count);
        }else {
            System.out.println("兑换失败");
        }

    }

    static void coinChange(Integer[] faces, int money) {
        Arrays.sort(faces, (Integer num1, Integer num2) -> {
            return num2 - num1;
        });
        int count = 0;
        for (int i = 0; i < faces.length; i++) {
            while (money >= faces[i]) {
                System.out.println("面值" + faces[i]);
                money -= faces[i];
                count++;
            }
        }
        if (money == 0) {
            System.out.println("找零钱数量" + count);
        }else {
            System.out.println("兑换失败");
        }


    }
}
