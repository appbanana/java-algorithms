package com.jqc.union;

/**
 * @author appbanana
 * 基于rank，采用路径减半优化并查集，
 *  路径减半：使路径上每隔一个节点就指向其祖父节点（parent的parent）
 * @date 2019/12/26
 */
public class UnionFind_QU_R_PH extends UnionFind_QU_R {

    public UnionFind_QU_R_PH(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (parents[v] != v) {
            parents[v] = parents[parents[v]];
            v = parents[v];
        }
        return v;
    }
}
