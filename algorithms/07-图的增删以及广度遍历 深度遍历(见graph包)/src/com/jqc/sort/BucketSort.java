package com.jqc.sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author appbanana
 * 桶排序
 * @date 2019/12/26
 */
public class BucketSort {

    public static void main(String[] args) {
        Double[] array = {0.34, 0.45, 0.15, 0.25, 0.66, 0.22, 0.88};
        // 数组中存放链表
        List<Double>[] buckets = new List[array.length];
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = (int)(array[i] * array.length);
            List<Double> bucket = buckets[bucketIndex];
            if (bucket == null) {
                // 链表不存在, 创建链表, 并放到buckets数组中
                bucket = new LinkedList<>();
                buckets[bucketIndex] = bucket;
            }
            bucket.add(array[i]);
        }

        int index = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] == null) continue;
            buckets[i].sort(null);
            for (Double d: buckets[i]) {
                array[index++] = d;
            }
        }

        for (Double d: array) {
            System.out.println(d);
        }

    }
}
