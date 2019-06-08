package com.zys.data.structure;

import java.util.LinkedList;

/**
 * @Author: Created by zys
 * @Description: 无向图，基于邻接表来实现
 * @Date: 2019/5/22 20:57
 * @Modified By:
 */
public class Graph {
    /**
     * 无向图的顶点个数
     */
    private int v;


    /**
     * 邻接表
     */
    private LinkedList<Integer> adj[];


    @SuppressWarnings("unchecked")
    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedList<>();
        }
    }


    /**
     * 无向图一次存两条边1
     *
     * @param s
     * @param t
     */
    public void addEdge(int s, int t) {
        // 顶点 s 与 t 相连
        adj[s].add(t);
        // 顶点 t 与 s 相连
        adj[t].add(s);
    }


    /**
     * 无向图的广度优先遍历
     *
     * @param s
     * @param t
     */
    public void bfs(int s, int t) {
        if (s == t) {
            return;
        }
        // 用于记录已访问过的顶点，避免顶点被重复访问
        boolean[] visited = new boolean[v];
        visited[s] = true;
        // 用于保存已访问过，但相连顶点还未被访问的顶点。
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        // 用于记录搜索路径，存储反向路径，比如顶点 2 到顶点 3，prev[3] 就等于 2
        int[] prev = new int[v];
        // 初始化，刚开始全都置为 -1
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }

        // 遍历顶点关联的边
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); i++) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    // 记录 s 到 顶点 q 路径
                    prev[q] = w;
                    // 当找到目标顶点t时，打印路径，并结束循环
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    // 顶点 q 置为 已访问
                    visited[q] = true;
                    // 将顶点 q 入队
                    queue.add(q);
                }
            }
        }
    }

    private boolean found = false;

    /**
     * 无向图的深度优先遍历
     *
     * @param s
     * @param t
     */
    public void dfs(int s, int t) {
        found = false;
        // 用于记录已访问过的顶点，避免顶点被重复访问
        boolean[] visited = new boolean[v];
        // 用于记录搜索路径，存储反向路径，比如顶点 2 到顶点 3，prev[3] 就等于 2
        int[] prev = new int[v];
        // 初始化，刚开始全都置为 -1
        for (int i = 0; i < v; i++) {
            prev[i] = -1;
        }
        recurDfs(s, t, prev, visited);
        print(prev, s, t);
    }

    /**
     * 解决递归的三部曲
     * 1. 推导出递归公式
     * 2. 找出 base case
     * 3. 找出递归的终止条件
     *
     * @param w
     * @param t
     * @param prev
     * @param visited
     */
    private void recurDfs(int w, int t, int[] prev, boolean[] visited) {
        if (found) {
            return;
        }
        // 递归终止条件
        if (w == t) {
            found = true;
            return;
        }
        visited[w] = true;
        for (int i = 0; i < adj[w].size(); i++) {
            // base case
            // 获取顶点 w 对应链表的第一节点
            int q = adj[w].get(i);
            // 判断该节点是否已经被访问过
            if (!visited[q]) {
                prev[q] = w;
                visited[w] = true;
                // 递归公式
                recurDfs(q, t, prev, visited);
            }
        }

    }

    /**
     * 递归打印 s->t 的路径
     *
     * @param prev
     * @param s
     * @param t
     */
    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }


}
