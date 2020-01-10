package com.jqc.union;

/**
 * @author appbanana
 * 基于rank，采用路径压缩优化并查集，
 * 路径压缩： 在find时使路径上的所有节点都指向根节点，从而降低树的高度
 * 缺点：路径压缩使路径上的所有节点都指向根节点，所以实现成本稍高
 * @date 2019/12/26
 */
public class UnionFind_QU_R_PC extends UnionFind_QU_R {

    public UnionFind_QU_R_PC(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (parents[v] != v) {
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
