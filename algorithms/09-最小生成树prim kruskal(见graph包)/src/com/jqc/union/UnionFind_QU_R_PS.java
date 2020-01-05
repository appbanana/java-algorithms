package com.jqc.union;

/**
 * @author appbanana
 * 基于rank，采用路径分裂优化并查集，
 *  路径分裂：使路径上的每个节点都指向其祖父节点（parent的parent）
 * @date 2019/12/26
 */
public class UnionFind_QU_R_PS extends UnionFind_QU_R {

    public UnionFind_QU_R_PS(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (parents[v] != v) {
            int parent = parents[v];
            parents[v] = parents[parent];
            v = parent;
        }
        return v;
    }
}
