package com.jqc.graph;

import java.util.List;
import java.util.Set;

/**
 * @author appbanana
 * @date 2019/12/28
 */
// 权重的比较需要添加一个属性供子类使用, 而接口不能添加属性, 从这开始把接口改成抽象类
public abstract class Graph<V, E> {

    protected WeightManager<E> weightManager;

    public Graph() {
    }

    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }
    /**
     * 边大小
     * @return
     */
    public abstract int edgesSize();

    /**
     * 顶点大小
     * @return
     */
    public abstract int verticesSize();

    /**
     * 添加顶点
     * @param v
     */
    public abstract void addVertex(V v);

    /**
     * 添加边
     * @param from 进边
     * @param to 出边
     */
    public abstract void addEdge(V from, V to);

    /**
     * 添加边
     * @param from 进边
     * @param to 出边
     * @param weight 边权重
     */
    public abstract void addEdge(V from, V to, E weight);

    /**
     * 删除顶点
     * @param v 顶点
     */
    public abstract  void removeVertex(V v);

    /**
     * 删除边
     * @param from 进边
     * @param to 出边
     */
    public abstract  void removeEdge(V from, V to);

    /**
     * 广度遍历
     * @param begin 开始点
     * @param visitor 遍历器，提供外界访问
     */
    public abstract void bfs(V begin, VertexVisit visitor);

    /**
     * 深度遍历
     * @param begin 起始点
     * @param visitor 遍历器，提供外界访问
     */
    public abstract void dfs(V begin, VertexVisit visitor);

    /**
     * 拓扑算法
     * @return
     */
    public abstract List<V> topologicalSort();

    /**
     * 最小生成树
     * @return
     */
    public abstract Set<EdgeInfo<V, E>> mst();

    /**
     * 遍历元素时,提供外界访问
     * @param <V>
     */
    public interface VertexVisit<V> {
        boolean visit(V value);
    }
    /**
     * 权重管理器 权重具体怎么比, 我也不知道, 暴露出去, 供用户自己使用
     * @param <E>
     */
    public interface WeightManager<E> {
        int compare(E w1, E w2);
        E add(E w1, E w2);
    }

    // 边信息 V 顶点 E边权重
    public static class EdgeInfo<V, E> {
        private V from;
        private V to;
        private E weight;

        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public V getFrom() {
            return from;
        }

        public void setFrom(V from) {
            this.from = from;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
