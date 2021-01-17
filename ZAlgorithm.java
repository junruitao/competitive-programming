import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	private static final long MOD = 1000000007;
	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;

	private int n, q;
	Map<Integer, Long> m = new HashMap<Integer, Long>();
	int[] a;
	long ans;

	void solve() throws Exception {

		String pat = next();
		int[] z = computeZArray(pat.toCharArray());
		int maxz = 0, res = 0;
		n = pat.length();
		for (int i = 1; i < n; i++) {
			if (z[i] == n - i && maxz >= n - i) {
				res = n - i;
				break;
			}
			maxz = Math.max(maxz, z[i]);
		}
		if (res > 0)
			out.print(pat.substring(0, res));
		else
			out.print("Just a legend");
	}

	int[] computeZArray(char[] s) {
		int[] z = new int[s.length];
		int L = 0, R = 0;
		for (int i = 1; i < s.length; i++) {
			if (i > R) {
				L = R = i;
				while (R < s.length && s[R - L] == s[R])
					R++;
				z[i] = R - L;
				R--;
			} else {
				int k = i - L;
				if (z[k] < R - i + 1)
					z[i] = z[k];
				else {
					L = i;
					while (R < s.length && s[R - L] == s[R])
						R++;
					z[i] = R - L;
					R--;
				}
			}
		}
		return z;
	}

	private int[][] nextInts(int r, int c) throws IOException {
		int[] a[];
		a = new int[r][c];
		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				a[i][j] = nextInt();
		return a;
	}

	private int[] nextTuple(int n) throws IOException {
		int[] a;
		a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = nextInt();
		return a;
	}

	private int[] nextInts(int n) throws IOException {
		return nextTuple(n);
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
