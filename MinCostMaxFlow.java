// to use:
        var f = new DijkstraMinCostMaxFlow(size);
        f.addEdge(from, to, cap, cost);
        int[] mf = f.minCostMaxFlow(0, size - 1); // 0: connections, 1:cost


    public class DijkstraMinCostMaxFlow {

        static class Edge {
            int from, to, capacity, cost, flow;
            Edge reverse;

            public Edge(int from, int to, int capacity, int cost) {
                this.from = from;
                this.to = to;
                this.capacity = capacity;
                this.cost = cost;
                this.flow = 0;
            }
        }

        private int n;
        private List<Edge>[] graph;
        private int[] potential;

        public DijkstraMinCostMaxFlow(int n) {
            this.n = n;
            graph = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
            }
            potential = new int[n];
        }

        public void addEdge(int from, int to, int capacity, int cost) {
            Edge forward = new Edge(from, to, capacity, cost);
            Edge reverse = new Edge(to, from, 0, -cost);
            forward.reverse = reverse;
            reverse.reverse = forward;
            graph[from].add(forward);
            graph[to].add(reverse);
        }

        private boolean dijkstra(int source, int sink, int[] dist, Edge[] parentEdge) {
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[source] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
            pq.add(new int[]{source, 0});

            while (!pq.isEmpty()) {
                int[] u = pq.poll();
                int current = u[0];
                int currentDist = u[1];

                if (currentDist > dist[current]) continue;

                for (Edge edge : graph[current]) {
                    int next = edge.to;
                    int newDist = dist[current] + edge.cost + potential[current] - potential[next];

                    if (edge.flow < edge.capacity && dist[next] > newDist) {
                        dist[next] = newDist;
                        parentEdge[next] = edge;
                        pq.add(new int[]{next, dist[next]});
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                if (dist[i] < Integer.MAX_VALUE) {
                    potential[i] += dist[i];
                }
            }

            return dist[sink] != Integer.MAX_VALUE;
        }

        public int[] minCostMaxFlow(int source, int sink) {
            int maxFlow = 0, minCost = 0;
            int[] dist = new int[n];
            Edge[] parentEdge = new Edge[n];

            Arrays.fill(potential, 0);

            while (dijkstra(source, sink, dist, parentEdge)) {
                int flow = Integer.MAX_VALUE;
                for (int v = sink; v != source; v = parentEdge[v].from) {
                    flow = Math.min(flow, parentEdge[v].capacity - parentEdge[v].flow);
                }

                for (int v = sink; v != source; v = parentEdge[v].from) {
                    parentEdge[v].flow += flow;
                    parentEdge[v].reverse.flow -= flow;
                    minCost += flow * parentEdge[v].cost;
                }

                maxFlow += flow;
            }

            return new int[]{maxFlow, minCost};
        }
    }
