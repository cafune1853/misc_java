package com.doggy.datastructure;

import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private int v;
    private LinkedList<Integer>[] edges;

    public Graph(int v) {
        this.v = v;
        this.edges = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            edges[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int target){
        checkPointRange(source);
        checkPointRange(target);
        edges[source].add(target);
    }

    private void checkPointRange(int point) {
        if(point < 0 || point >= v){
            throw new IllegalArgumentException();
        }
    }

    /**
     * 广度优先遍历 与树的广度优先遍历类似 使用队列实现
     * @param source: 起始搜索点
     * @param target: 终止搜索点
     */
    public void bfs(int source, int target){
        checkPointRange(source);
        checkPointRange(target);
        Queue<Integer> partVisitedPoint = new LinkedList<>();
        partVisitedPoint.add(source);
        boolean[] visited = new boolean[v];
        visited[source] = true;
        // 用于记录每个节点的上一个访问节点是哪个，也就是当前节点是由哪个节点访问过来的。
        int[] preVisited = new int[v];
        for (int i = 0; i < v; i++) {
            preVisited[i] = -1;
        }
        if(source == target){
            print(preVisited, target);
            return;
        }
        while (!partVisitedPoint.isEmpty()){
            Integer start = partVisitedPoint.poll();
            for (Integer end : edges[start]) {
                if(!visited[end]){
                    partVisitedPoint.add(end);
                    visited[end] = true;
                    preVisited[end] = start;
                    if(end == target){
                        print(preVisited, target);
                        return;
                    }
                }
            }
        }
        System.out.print("Unreachable");
    }

    /**
     * 深度优先遍历
     * @param source
     * @param target
     */
    public void dfs(int source, int target){
        checkPointRange(source);
        checkPointRange(target);
        boolean[] visited = new boolean[v];
        visited[source] = true;
        int[] preVisited = new int[v];
        for (int i = 0; i < v; i++) {
            preVisited[i] = -1;
        }
        boolean found = tryDfs(source, target, visited, preVisited);
        if(found){
            print(preVisited, target);
        }else{
            System.out.print("Unreachable");
        }
    }

    public boolean tryDfs(int start, int target, boolean[] visited, int[] preVisited){
        if(start == target){
            return true;
        }
        for (Integer next : edges[start]) {
            if(!visited[next]){
                visited[next] = true;
                preVisited[next] = start;
                boolean found = tryDfs(next, target, visited, preVisited);
                if(found){
                    return true;
                }
            }
        }
        return false;
    }

    private void print(int[] preVisited, int end){
        if(preVisited[end] != -1){
            print(preVisited, preVisited[end]);
        }
        System.out.print(end + " -> ");
    }

    public static void main(String[] args) {
        Graph graph = new Graph(10);
        graph.addEdge(0, 1);
        graph.addEdge(0, 8);
        graph.addEdge(1, 7);
        graph.addEdge(7, 6);
        graph.addEdge(6, 9);
        graph.addEdge(9, 6);
        graph.addEdge(9, 8);
        graph.bfs(0, 8);
        System.out.println();
        graph.bfs(0, 3);
        System.out.println();
        graph.bfs(0, 0);
        System.out.println();
        System.out.println();
        graph.dfs(0, 8);
        System.out.println();
        graph.dfs(0, 3);
        System.out.println();
        graph.dfs(0, 0);
    }
}
