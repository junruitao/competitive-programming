// to use:
		MaxFlowGraph g = new MaxFlowGraph(sink + 1, source, sink);
		g.addEdge(from, to, cap);
		long flow = g.getFlow(source, sink);


class MaxFlowGraph {
	final long INF = (long) 1e13;

	public class Edge {
		public int from;
		public int to;
		public long flow;
		public long cap;

		Edge(int from, int to, long cap) {
			this.from = from;
			this.to = to;
			this.cap = cap;
			this.flow = 0;
		}

		boolean isOpen() {
			return cap > flow;
		}

		long getLeft() {
			return cap - flow;
		}
	}

	public ArrayList<Edge> e = new ArrayList<>();
	long[] dist;
	int[] parEdge;
	int[] first;
	ArrayList<Integer>[] g;
	int source;
	int sink;

	public void setzeroflow() {
		for (int i = 0; i < e.size(); i++) {
			Edge ee = e.get(i);
			ee.flow = 0;
		}
	}

	public MaxFlowGraph(int size, int source, int sink) {
		this.source = source;
		this.sink = sink;
		dist = new long[size];
		parEdge = new int[size];
		first = new int[size];
		g = new ArrayList[size];
		for (int i = 0; i < size; i++)
			g[i] = new ArrayList<>();
	}

	public void addEdge(int from, int to, long cap) {
		int size = e.size();
		e.add(new Edge(from, to, cap));
		e.add(new Edge(to, from, 0));
		g[from].add(size);
		g[to].add(size + 1);
	}

	private boolean bfs() {
		Arrays.fill(dist, INF);
		dist[source] = 0;
		Queue<Integer> q = new ArrayDeque<>();
		q.add(source);
		while (!q.isEmpty()) {
			int v = q.poll();
			for (int j : g[v]) {
				Edge curr = e.get(j);
				int to = curr.to;

				if (curr.isOpen() && dist[to] > dist[v] + 1) {
					dist[to] = dist[v] + 1;
					q.add(to);
					if (to == sink)
						return true;

				}

			}
		}
		return dist[sink] != INF;
	}

	private boolean dfs(int v, int pe) {
		parEdge[v] = pe;
		if (v == sink)
			return true;
		if (dist[v] >= dist[sink])
			return false;
		for (; first[v] < g[v].size(); first[v]++) {
			Edge curr = e.get(g[v].get(first[v]));
			if (curr.isOpen() && dist[curr.to] == dist[v] + 1 && dfs(curr.to, g[v].get(first[v])))
				return true;
		}
		return false;
	}

	long pushFlow() {
		long add = INF;
		int v = sink;
		while (v != source) {
			Edge curr = e.get(parEdge[v]);
			add = Math.min(add, curr.getLeft());
			v = curr.from;
		}
		v = sink;
		while (v != source) {
			int i = parEdge[v];
			e.get(i).flow += add;
			e.get(i ^ 1).flow -= add;
			v = e.get(i).from;
		}
		return add;
	}

	public long getFlow(int source, int sink) {

		this.source = source;
		this.sink = sink;

		long flow = 0;
		while (bfs()) {
			Arrays.fill(first, 0);
			while (dfs(source, -1))
				flow += pushFlow();
		}
		return flow;
	}
}
