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
import java.util.StringTokenizer;

public class Main {

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;

	int[][] dist;
	int[][] floydWarshall(int numVertices) {

		for (int k = 0; k < numVertices; k++)
			for (int i = 0; i < numVertices; i++)
				for (int j = 0; j < numVertices; j++)
					if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
							&& dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
		return dist;

	}

	void solve() throws Exception {
		int n = nextInt();
		int m = nextInt();
		dist = new int[n][n];
		for (int[] row : dist)
			Arrays.fill(row, Integer.MAX_VALUE);

		for (int i = 0; i < m; i++) {
				dist[nextInt() - 1][nextInt() - 1] = nextInt();
		}
		
		floydWarshall(n);
		int q = nextInt();
		while (q-- > 0) {
			int x = nextInt();
			int y = nextInt();
			if (dist[x - 1][y - 1] == Integer.MAX_VALUE)
				out.println("No Route");
			else
				out.println(dist[x - 1][y - 1]);
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
					new Main().solve();
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