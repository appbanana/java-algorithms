package com.jqc.union;

/**
 * @author appbanana
 * Quick Find 实现并查集
 * @date 2019/12/26
 */
public class UnionFind_QF extends UnionFind {

    public UnionFind_QF(int capacity) {
        super(capacity);
    }

    /**
     * 直接找到根节点 时间复杂度为O(1)
     * @param v
     * @return 根节点
     */
    public int find(int v) {
        rangeCheck(v);
        return parents[v];
    }

    /**
     * 将v1中所有节点都嫁接到v2节点上
     * 时间复杂度为O(n)
     * @param v1
     * @param v2
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        // v1, v2在同一个集合中 直接返回
        if (p1 == p2) return;

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1) {
                parents[i] = p2;
            }
        }
    }

}
