package com.jqc;


import com.jqc.graph.Graph;
import com.jqc.graph.ListGraph;

public class Main {

    public static void main(String[] args) {
//        test1();
//        testBFS();
        testDFS();
    }

    /**
     * graph 简单图测试
     */
    static void test1() {
        ListGraph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("V1", "V0", 9);
        graph.addEdge("V1", "V2", 3);
        graph.addEdge("V2", "V0", 2);
        graph.addEdge("V2", "V3", 5);
        graph.addEdge("V3", "V4", 1);
        graph.addEdge("V0", "V4", 6);

        graph.removeEdge("V0", "V4");
        graph.removeVertex("V0");

        graph.print();
    }


    static void testBFS(){
        // Data.BFS_01 对应img/bfs_01 无向图
//        Graph<Object, Double> graph = undirectedGraph(Data.BFS_01);
//        graph.bfs("A");

        // Data.BFS_02 对应img/bfs_02 有向图
//        Graph<Object, Double> graph = directedGraph(Data.BFS_02);
//        graph.bfs(0);

        // Data.BFS_03 对应img/bfs_03 无向图
//        Graph<Object, Double> graph = undirectedGraph(Data.BFS_03);
//        graph.bfs(0);

        // Data.BFS_04 对应img/bfs_04 有向图
        Graph<Object, Double> graph = directedGraph(Data.BFS_04);
        graph.bfs(5);

    }

    static void testDFS() {
        // Data.DFS_01 对应img/dfs_01 无向图 答案不唯一 图片中的答案仅供参考
        Graph<Object, Double> graph = undirectedGraph(Data.DFS_01);
        graph.dfs(1);

        // Data.DFS_01 对应img/dfs_01 无向图 答案不唯一 图片中的答案仅供参考
//        Graph<Object, Double> graph = directedGraph(Data.DFS_02);
////        graph.dfs("a");

    }


    /**
     * 有向图
     */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>();
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     * @param data
     * @return
     */
    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>();
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }

}
