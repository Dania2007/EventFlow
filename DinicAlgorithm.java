
package eventflow.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DinicAlgorithm {

    private static class Edge {
        int to;
        int reverse;
        int capacity;

        Edge(int to, int reverse, int capacity) {
            this.to = to;
            this.reverse = reverse;
            this.capacity = capacity;
        }
    }

    private final List<List<Edge>> graph;
    private final int[] level;
    private final int[] pointer;
    private final int n;

    public DinicAlgorithm(int n) {
        this.n = n;
        graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        level = new int[n];
        pointer = new int[n];
    }

    public void addEdge(int from, int to, int capacity) {
        Edge forward = new Edge(
            to, graph.get(to).size(), capacity
        );

        Edge backward = new Edge(
            from, graph.get(from).size(), 0
        );

        graph.get(from).add(forward);
        graph.get(to).add(backward);
    }

    private boolean bfs(int source, int sink) {
        Arrays.fill(level, -1);

        int[] queue = new int[n];
        int head = 0;
        int tail = 0;

        queue[tail++] = source;
        level[source] = 0;

        while (head < tail) {
            int u = queue[head++];

            for (Edge edge : graph.get(u)) {
                if (edge.capacity > 0 && level[edge.to] == -1) {
                    level[edge.to] = level[u] + 1;
                    queue[tail++] = edge.to;
                }
            }
        }

        return level[sink] != -1;
    }

    private int dfs(int u, int sink, int pushed) {
        if (pushed == 0) {
            return 0;
        }

        if (u == sink) {
            return pushed;
        }

        for (; pointer[u] < graph.get(u).size();
             pointer[u]++) {

            Edge edge = graph.get(u).get(pointer[u]);

            if (edge.capacity > 0
                    && level[edge.to] == level[u] + 1) {

                int flow = dfs(
                    edge.to, sink,
                    Math.min(pushed, edge.capacity)
                );

                if (flow > 0) {
                    edge.capacity -= flow;

                    graph.get(edge.to)
                         .get(edge.reverse).capacity += flow;

                    return flow;
                }
            }
        }

        return 0;
    }

    public int maxFlow(int source, int sink) {
        int totalFlow = 0;

        while (bfs(source, sink)) {
            Arrays.fill(pointer, 0);

            int pushed;

            while ((pushed = dfs(
                    source, sink, Integer.MAX_VALUE)) > 0) {
                totalFlow += pushed;
            }
        }

        return totalFlow;
    }

    public static void runDemo() {
        // EventFlow sample resource allocation network.
        // Node 0: Central event organizer
        // Node 1: Technical events resource pool
        // Node 2: Cultural events resource pool
        // Node 3: Final allocation destination
        //
        // Edge capacities represent available resource units.

        DinicAlgorithm dinic = new DinicAlgorithm(4);

        dinic.addEdge(0, 1, 10);
        dinic.addEdge(0, 2, 5);
        dinic.addEdge(1, 2, 4);
        dinic.addEdge(1, 3, 8);
        dinic.addEdge(2, 3, 10);

        System.out.println(
            "\n===== EventFlow: Large-Scale Resource Allocation =====");

        System.out.println("Source: Central event organizer");
        System.out.println("Resource pools: Technical and cultural events");

        int result = dinic.maxFlow(0, 3);

        System.out.println(
            "Maximum resource flow: " + result + " units");

        System.out.println(
            "Calculated using BFS level graphs and DFS flow paths.");
    }
}
