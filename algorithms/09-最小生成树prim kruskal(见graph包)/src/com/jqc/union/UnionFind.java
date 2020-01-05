package com.jqc.union;

/**
 * @author appbanana
 * 并查集抽象类
 * @date 2019/12/26
 */
public abstract class UnionFind {

    protected int[] parents;

    public UnionFind(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be >= 1 ");
        }

        parents = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            parents[i] = i;
        }
    }


    /**
     * 查找v所属的集合(找根节点)
     * @param v
     * @return
     */
    public abstract int find(int v);

    /**
     * 合并v1, v2所在的集合
     * @param v1
     * @param v2
     */
    public abstract void union(int v1, int v2);

    /**
     * 检查v1, v2是否属于同一个集合
     * @param v1
     * @param v2
     * @return
     */
    public boolean isSame(int v1, int v2){
        return find(v1) == find(v2);
    }

    protected void rangeCheck(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("v is out of bounds");
        }
    }
}
