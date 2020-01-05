package com.jqc.union;

/**
 * @author appbanana
 * 基于size优化Quick union并查集, size小的合并到size大的上
 * @date 2019/12/26
 */
public class UnionFind_QU_S extends UnionFind_QU {
    private int[] sizes;

    public UnionFind_QU_S(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        // size 初始化
        for (int i = 0; i < capacity; i++) {
            sizes[i] = 1;
        }
    }

    /**
     * 元素少的合并到元素多的上面
     * @param v1
     * @param v2
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        if (sizes[p1] < sizes[p2]) {
            parents[p1] = p2;
            sizes[p2] += sizes[p1];
        }else {
            parents[p2] = p1;
            sizes[p1] += sizes[p2];
        }
    }
}
