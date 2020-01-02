package com.jqc.graph;

import java.util.*;

/**
 * @author appbanana
 * 邻接表
 * @date 2019/12/28
 */

public class ListGraph<V, E> implements Graph<V, E> {
    // 顶点的map key: value ---> key是外界传过来的值， value是将其包装成顶点
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    // 存储边的容器 好处自动去重
    private Set<Edge<V, E>> edges = new HashSet<>();


    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    /**
     * 添加顶点
     * @param v 外界传过来的点
     */
    @Override
    public void addVertex(V v) {
        // 如果vertices里面包含这个v, 说明已经添加过了, 不需要重复添加
        if (vertices.containsKey(v)) return;
        // 将外界传过来的点包装成Vertex
        Vertex<V, E> vertex = new Vertex<>(v);
        vertices.put(v, vertex);
    }

    /**
     * 添加边
     * @param from 进边
     * @param to 出边
     */
    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    /**
     * 添加边
     * @param from 进边
     * @param to 出边
     * @param weight 边权重
     */
    @Override
    public void addEdge(V from, V to, E weight) {
        // 根据起始点(from)获取其对应的顶点(Vertex)
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            // 如果为空, 说明之前没有存储过, 接下来创建, 并添加到vertices(这是个map)中
            fromVertex = new Vertex<>(from);
            vertices.put(from, fromVertex);
        }

        // 这个同上
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }

        // 根据起始点, 终点创建边 并添加到集合中
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;

        // 如果remove返回结果为true, 说明之前存储过;为false, 说明之前没有存储过
        // 不这样搞的话到时你需要遍历fromVertex.outEdges, toVertex.inEdges找到这条边,更新weight,效率上不是很高
        // 下面这样处理的好处就是我不需要遍历, 先删除在添加 还是我杰哥机智
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

        edges.add(edge);
        // 将创建的边添加到起始点的出边的集合上
        fromVertex.outEdges.add(edge);
        // 将创建的边添加到终点点的进边的集合上
        toVertex.inEdges.add(edge);
    }

    /**
     * 删除顶点, 需要把这个点进边和出边包含所有的信息全删掉
     * @param v 顶点
     */
    @Override
    public void removeVertex(V v) {
        // 获取该顶点
        Vertex<V, E> vertex = vertices.get(v);
        if (vertex == null) return;
        // 不能边遍历边删除, 会出问题的, 可以借助Iterator来实现
        // 遍历该顶点的所有出边
        for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            // 该顶点的出边也是另外一个顶点（就是edge.to）的进边， 都要删除
            // edge.to 拿到这条边的终点 然后在终点中进边集合中删除这条边
            edge.to.inEdges.remove(edge);
            // 删除该顶点的出边， 将当前遍历到的元素edge从集合vertex.outEdges中删掉
            iterator.remove();
            edges.remove(edge);
        }

        // 大前提 目标点的进边集合中遍历
        for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            // 该顶点的进边也是另外一个顶点（就是edge.from）的出边， 都要删除
            // edge.from 拿到这条边的起始点 然后在起始点中出边集合中删除这条边
            edge.from.outEdges.remove(edge);
            // 删除该顶点的进边，将当前遍历到的元素edge从集合vertex.inEdges中删掉
            iterator.remove();
            edges.remove(edge);
        }

    }

    /**
     * 删除边
     * @param from 起始点
     * @param to 终点
     */
    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) return;

        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) return;

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        // 这个删除很巧 如果删除返回true 说明删除成功, 同时toVertex.inEdges和edges都需要删除这条边
        // 如果返回false 不需要做任何处理
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

    }

    /**
     * 广度遍历
     * 思路:其实跟二叉树的层序遍历很像
     * @param begin 开始点
     */
    @Override
    public void bfs(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;
        // 队列
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        // 记录已经遍历过的节点
        HashSet<Vertex<V, E>> visitedVertices = new HashSet<>();
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);

        while (!queue.isEmpty()) {
            Vertex<V, E> vertex = queue.poll();
            System.out.println(vertex.value);
            for (Edge<V, E> edge : vertex.outEdges) {
                // 往队列添加之前 先判断visitedVertices是否包含这个顶点，包含跳过；不包含添加队列
                if (visitedVertices.contains(edge.to)) continue;
                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            }
        }

    }

    /**
     *  深度遍历 非递归实现
     * @param begin 起始点
     */
    @Override
    public void dfs(V begin) {
        Vertex<V, E> fromVertex = vertices.get(begin);
        if (fromVertex == null) return;
        // 栈
        Stack<Vertex<V, E>> stack = new Stack<>();
        // 记录已经遍历过的节点
        HashSet<Vertex<V, E>> visitedVertices = new HashSet<>();
        stack.push(fromVertex);
        visitedVertices.add(fromVertex);
        System.out.println(fromVertex.value);
        while (!stack.isEmpty()) {
            Vertex<V, E> vertex = stack.pop();
            for (Edge<V, E> edge : vertex.outEdges) {
                // 如果之前遍历过 直接跳过
                if (visitedVertices.contains(edge.to)) continue;
                // 这比较特殊，还需要把from顶点加进去
                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                System.out.println(edge.to.value);
                // 因为要一路走到底，所以要break
                break;
            }
        }
    }

    /**
     * 深度遍历 递归版
     * @param begin 起始位置
     */
    public void dfs1(V begin) {
        Vertex<V, E> fromVertex = vertices.get(begin);
        if (fromVertex == null) return;
        dfs_r(fromVertex, new HashSet<Vertex<V, E>>());

    }

    public void dfs_r(Vertex<V, E> vertex, HashSet<Vertex<V, E>> visitedVertices) {
        if (vertex == null) return;
        System.out.println(vertex.value);
        visitedVertices.add(vertex);
        for (Edge<V, E> edge : vertex.outEdges) {
            if (visitedVertices.contains(edge.to)) continue;
            dfs_r(edge.to, visitedVertices);
        }

    }
        /**
         * 自定义输出
         */
    public void print() {
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out-------out---------out");
            System.out.println(vertex.outEdges);
            System.out.println("in-------in---------in");
            System.out.println(vertex.inEdges);
        });

        System.out.println("==========================");
        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });
    }

    // 顶点类
    private static class Vertex<V, E> {
        V value;
        // 顶点的进边
        Set<Edge<V, E>> inEdges = new HashSet<>();
        // 顶点的出边
        Set<Edge<V, E>> outEdges = new HashSet<>();

        Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex) obj).value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    // 边静态类
    private static class Edge<V, E> {
        // 边的起始点
        Vertex<V, E> from;
        // 边的终点
        Vertex<V, E> to;
        // 边权重
        E weight;

        /**
         * 边的构造器初始化
         * @param from 起始点
         * @param to 终点
         */
        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            Edge edg = (Edge) obj;
            return Objects.equals(from, edg.from) && Objects.equals(to, edg.to);
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }
}