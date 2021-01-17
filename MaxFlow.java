import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Queue;

public class Main {

	private static final long infin = 1000000000000L;

	public static void main(String args[]) {
		InputReader in = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		n = in.nextInt();
		int m = in.nextInt();
		int x = in.nextInt();
		long ans = 0;

		int source = 0;
		int sink = 2 * n + 1;
		MaxFlowGraph g = new MaxFlowGraph(sink + 1, source, sink);

		for (int i = 1; i <= n; i++) {
			int tmp = in.nextInt();
			ans += tmp;
			g.addEdge(source, i, tmp);
		}

		int dist[][] = new int[n][n];
		for (int i = 1; i <= n; i++) {
			int tmp = in.nextInt();
			g.addEdge(n + i, sink, tmp);
			Arrays.fill(dist[i - 1], x + 1);
			dist[i - 1][i - 1] = 0;
		}

		for (int i = 0; i < m; i++) {
			int u = in.nextInt() - 1;
			int v = in.nextInt() - 1;
			dist[v][u] = dist[u][v] = in.nextInt();
		}

		for (int u = 0; u < n; u++)
			for (int v = 0; v < n; v++)
				for (int k = 0; k < n; k++)
					dist[u][v] = Math.min(dist[u][v], dist[u][k] + dist[k][v]);

		for (int u = 0; u < n; u++)
			for (int v = 0; v < n; v++)
				if (dist[u][v] <= x)
					g.addEdge(u + 1, n + v + 1, infin);

		long flow = g.getFlow(source, sink);

//		Helper.tr(ans, flow);

		w.println(ans - flow);

		w.close();
	}

	static int n;

	static class MyComparator implements Comparator<Node> {
		@Override
		public int compare(Node n0, Node n1) {
			if (n0.dist > n1.dist)
				return 1;
			else if (n0.dist < n1.dist)
				return -1;

			return 0;
		}
	}

	static public class Node {
		int x;
		long dist;

		public Node(int i, long j) {
			x = i;
			dist = j;
		}
	}

	static class InputReader {

		private InputStream stream;
		private byte[] buf = new byte[8192];
		private int curChar;
		private int snumChars;
		private SpaceCharFilter filter;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int snext() {
			if (snumChars == -1)
				throw new InputMismatchException();
			if (curChar >= snumChars) {
				curChar = 0;
				try {
					snumChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (snumChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}

		public int nextInt() {
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = snext();
			}

			int res = 0;

			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = snext();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public long nextLong() {
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = snext();
			}

			long res = 0;

			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = snext();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public String readString() {
			int c = snext();
			while (isSpaceChar(c))
				c = snext();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = snext();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public boolean isSpaceChar(int c) {
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	}
}

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