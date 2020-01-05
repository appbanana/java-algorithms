package com.jqc.union;

/**
 * @author appbanana
 * 基于rank优化Quick union并查集, 矮的的合并到高的上
 * @date 2019/12/26
 */
public class UnionFind_QU_R extends UnionFind_QU {
    private int[] ranks;

    public UnionFind_QU_R(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        // ranks 初始化
        for (int i = 0; i < capacity; i++) {
            ranks[i] = 1;
        }
    }

    /**
     * 矮的的合并到高的上
     * @param v1
     * @param v2
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        if (ranks[p1] < ranks[p2]) {
            parents[p1] = p2;
        }else if (ranks[p2] < ranks[p1]){
            parents[p2] = p1;
        }else {
            // 树高相等时 合并后高度要加1
            parents[p1] = p2;
            ranks[p2]++;
        }
    }
}
