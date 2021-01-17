import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Main {
	private static final long MOD = 1000000007;
	InputStream is;
	PrintWriter out;
	String INPUT = "";

	int maxLevel = 18;
	List<Integer> tree[];
	int depth[];
	int parent[][];
	int val[];
	int xor[];

	// pre-compute the depth for each node and their
	// first parent(2^0th parent)
	// time complexity : O(n)
	void dfs(int cur, int prev) {
		xor[cur] = val[cur] ^ xor[prev];
		depth[cur] = depth[prev] + 1;
		parent[cur][0] = prev;
		for (int i = 0; i < tree[cur].size(); i++) {
			if (tree[cur].get(i) != prev)
				dfs(tree[cur].get(i), cur);
		}
	}

	// Dynamic Programming Sparse Matrix Approach
	// populating 2^i parent for each node
	// Time complexity : O(nlogn)
	void precomputeSparseMatrix(int n) {
		for (int i = 1; i < maxLevel; i++) {
			for (int node = 1; node <= n; node++) {
				if (parent[node][i - 1] != -1)
					parent[node][i] = parent[parent[node][i - 1]][i - 1];
			}
		}
	}

	// Returning the LCA of u and v
	// Time complexity : O(log n)
	int lca(int u, int v) {
		if (depth[v] < depth[u])
			return lca(v, u);

		int diff = depth[v] - depth[u];

		// Step 1 of the pseudocode
		for (int i = 0; i < maxLevel; i++)
			if (((diff >> i) & 1) != 0)
				v = parent[v][i];

		// now depth[u] == depth[v]
		if (u == v)
			return u;

		// Step 2 of the pseudocode
		for (int i = maxLevel - 1; i >= 0; i--)
			if (parent[u][i] != parent[v][i]) {
				u = parent[u][i];
				v = parent[v][i];
			}

		return parent[u][0];
	}

	void addEdge(int u, int v) {
		tree[u].add(v);
		tree[v].add(u);
	}

	void solve() {

		int n = ni();
		int q = ni();
		val = new int[n + 1];
		tree = new List[n + 1];
		depth = new int[n + 1];
		xor = new int[n + 1];
		parent = new int[n + 1][maxLevel];
		for (int i = 1; i <= n; i++) {
			val[i] = ni();
			tree[i] = new ArrayList<Integer>();
		}
		for (int i = 1; i < n; i++)
			addEdge(ni(), ni());

		dfs(1, 0);
		precomputeSparseMatrix(n);

//		tr(val);
//		tr(parent);
//		tr(xor);
		while (q-- > 0) {
			int u = ni();
			int v = ni();
			int lca = lca(u, v);
			int x = xor[u] ^ xor[v] ^ xor[lca];
			tr(u,v,lca,x);
			sb.append(x == 0 ? "Alex" : "Bob").append(System.lineSeparator());
		}
		System.out.println(sb);
	}

	StringBuffer sb = new StringBuffer();

	void run() throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);
		Thread t = new Thread(null, null, "~", Runtime.getRuntime().maxMemory()) {
			@Override
			public void run() {
				long s = System.currentTimeMillis();
				solve();
				out.flush();
				if (!INPUT.isEmpty())
					tr(System.currentTimeMillis() - s + "ms");
			}
		};
		t.start();
		t.join();

		// long s = System.currentTimeMillis();
		// solve();
		// out.flush();
		// if(!INPUT.isEmpty())tr(System.currentTimeMillis()-s+"ms");
	}

	public static void main(String[] args) throws Exception {
		new Main().run();
	}

	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;

	private int readByte() {
		if (lenbuf == -1)
			throw new InputMismatchException();
		if (ptrbuf >= lenbuf) {
			ptrbuf = 0;
			try {
				lenbuf = is.read(inbuf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (lenbuf <= 0)
				return -1;
		}
		return inbuf[ptrbuf++];
	}

	private boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}

	private int skip() {
		int b;
		while ((b = readByte()) != -1 && isSpaceChar(b))
			;
		return b;
	}

	private double nd() {
		return Double.parseDouble(ns());
	}

	private char nc() {
		return (char) skip();
	}

	private String ns() {
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != ' ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}

	private char[] ns(int n) {
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while (p < n && !(isSpaceChar(b))) {
			buf[p++] = (char) b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}

	private char[][] nm(int n, int m) {
		char[][] map = new char[n][];
		for (int i = 0; i < n; i++)
			map[i] = ns(m);
		return map;
	}

	private int[] na(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}

	private int[][] na(int n, int m) {
		int[][] a = new int[n][];
		for (int i = 0; i < n; i++)
			a[i] = na(m);
		return a;
	}

	private int ni() {
		int num = 0, b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}

		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private long nl() {
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}

		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private static void tr(Object... o) {
		System.out.println(Arrays.deepToString(o));
	}
}