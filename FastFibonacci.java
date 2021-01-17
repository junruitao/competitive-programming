import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.exit;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
 
public class MyFirstJavaProgram {
 
	private static final long MOD = 1000000007;
	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;
 
	void fast_fib(long n, long ans[]) {
		long a, b, c, d;
		if (n == 0) {
			ans[0] = 0;
			ans[1] = 1;
			return;
		}
		fast_fib((n / 2), ans);
		a = ans[0]; /* F(n) */
		b = ans[1]; /* F(n+1) */
		c = 2 * b - a;
		if (c < 0)
			c += MOD;
		c = (a * c) % MOD; /* F(2n) */
		d = (a * a + b * b) % MOD; /* F(2n + 1) */
		if (n % 2 == 0) {
			ans[0] = c;
			ans[1] = d;
		} else {
			ans[0] = d;
			ans[1] = c + d;
		}
	}
 
	void solve() throws Exception {
		int q = nextInt();
		while (q-- > 0) {
			long N = nextLong();
			long[] a = new long[2];
			fast_fib(N+2, a);
			out.println(a[0]);
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