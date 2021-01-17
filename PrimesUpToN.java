import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MyFirstJavaProgram {

	private static final long MOD = 1000000007;
	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;
	private int[] a;
	private int n;
	List<Integer> primes;
	private Map<Integer, Integer> m;

	List<Integer> sieve(int n) {
		List<Integer> ret = new ArrayList<Integer>();
		boolean[] sieve = new boolean[n + 1];
		for (int p = 2; p <= n; p++) {
			if (!sieve[p]) {
				for (int i = p * 2; i <= n; i += p)
					sieve[i] = true;
				ret.add(p);
			}
		}
		return ret;
	}



	void solve() throws Exception {
		n = nextInt();
		a = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			a[i] = nextInt();
			a[i] += a[i - 1];
		}
		primes = getPrimeNumbers(n);
		m = new HashMap<Integer, Integer>();
		long ans = getMax(1);
		out.println(ans);
	}

	private int getMax(int i) {
		if (i > n)
			return 0;
		Integer res = m.get(i);
		if (res == null) {
			res = 0;
			for (int p : primes) {
				if (p + i - 1 <= n)
					res = Math.max(res, a[p + i - 1] - a[i - 1] + getMax(p + i + 1));
			}
			res = Math.max(res, getMax(i + 1));
			m.put(i, res);
		}
		return res;
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