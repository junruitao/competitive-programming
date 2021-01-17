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

public class MyFirstJavaProgram {

	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;

	private static final long MOD = 1000000007;
	int a[];

	long power(long a, long b) {
		long x = 1, y = a;
		while (b > 0) {
			if (b % 2 != 0) {
				x = (x * y) % MOD;
			}
			y = (y * y) % MOD;
			b /= 2;
		}
		return x % M;
	}

	 
	long modular_inverse(long n, long mod){
		return power(n, mod-2, mod);
	}
	
	void solve() throws Exception {

		int n = nextInt();
		int m = nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = nextInt();

		Arrays.sort(a);

		long ans = 0;

		long cnr = 1;
		for (int i = m - 1; i < n; i++) {
			ans = (ans + cnr * a[i]) % MOD;
			cnr = (((i+1)*cnr%MOD)*modular_inverse(i-m+2, MOD))%MOD;
			out.println(cnr);
		}
		out.println(ans);
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