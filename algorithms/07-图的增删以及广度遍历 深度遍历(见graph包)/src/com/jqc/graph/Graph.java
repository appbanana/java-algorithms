package com.jqc.graph;

/**
 * @author appbanana
 * @date 2019/12/28
 */
public interface Graph<V, E> {
    /**
     * 边大小
     * @return
     */
    int edgesSize();

    /**
     * 顶点大小
     * @return
     */
    int verticesSize();

    /**
     * 添加顶点
     * @param v
     */
    void addVertex(V v);

    /**
     * 添加边
     * @param from 进边
     * @param to 出边
     */
    void addEdge(V from, V to);

    /**
     * 添加边
     * @param from 进边
     * @param to 出边
     * @param weight 边权重
     */
    void addEdge(V from, V to, E weight);

    /**
     * 删除顶点
     * @param v 顶点
     */
    void removeVertex(V v);

    /**
     * 删除边
     * @param from 进边
     * @param to 出边
     */
    void removeEdge(V from, V to);

    /**
     * 广度遍历
     * @param begin 开始点
     */
    void bfs(V begin);

    /**
     * 深度遍历
     * @param begin 起始点
     */
    void dfs(V begin);
}
