
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class MyFirstJavaProgram {

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;

	class Edge implements Comparable<Edge>{
		int from, to, cost;

		public Edge(int nextInt, int nextInt2, int nextInt3) {
			from = nextInt;
			to = nextInt2;
			cost = nextInt3;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

      	public int compareTo(Edge e)
		{
 			return e.cost-cost;
		}
	};

	Edge a[];

	int get(int x) {
		if (x == root[x])
			return x;
		return root[x] = get(root[x]);
	}

	void merge(int a, int b) {
		root[a] = b;
	}

	int root[];

	void solve() throws Exception {
		int t = nextInt();
		while (t-- > 0) {
			int N = nextInt();
			int k = nextInt();

			a = new Edge[k];
			root = new int[N + 1];
			for (int i = 1; i <= N; i++)
				root[i] = i;

			for (int i = 0; i < k; i++) {
				a[i] = new Edge(nextInt(), nextInt(), nextInt());
			}
			long ans = 0;
//			Arrays.sort(a, Comparator.comparing(Edge::getCost).reversed());
			Arrays.sort(a);
			int count = 0;
			for (int i = 0; i < k && count<N-1; i++) {
				int from = get(a[i].from);
				int to = get(a[i].to);
				if (from != to) {
					out.println(from+" "+to+" "+a[i].cost);
					ans += a[i].cost;
					merge(from, to);
					count ++;
				}
			}
			out.println(ans);
		}
	}

	static int nextInt() throws IOException {
		return parseInt(next());
	}

	static long nextLong() throws IOException {
		return parseLong(next());
	}

	static double nextDouble() throws IOException {
		return parseDouble(next());
	}

	static String next() throws IOException {
		while (tok == null || !tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}
		return tok.nextToken();
	}

	public static void main(String[] args) {
		new Thread(null, null, "Main", 0x4000000) {
			public void run() {
				try {
					in = new BufferedReader(new InputStreamReader(System.in));
					out = new PrintWriter(new OutputStreamWriter(System.out));
					new MyFirstJavaProgram().solve();
					in.close();
					out.close();
				} catch (Throwable e) {
					e.printStackTrace();
					exit(1);
				}
			}
		}.start();
	}
}
