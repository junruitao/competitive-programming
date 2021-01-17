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
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

public class MyFirstJavaProgram {

	private static final long MOD = 1000000007;
	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;

	public Set<Integer> primeFactors(int n) {
		Set<Integer> ret = new HashSet<Integer>();
		if (n % 2 == 0) {
			ret.add(2);
			while (n % 2 == 0)
				n /= 2;
		}

		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			if (n % i == 0) {
				ret.add(i);
				while (n % i == 0)
					n /= i;
			}
		}

		if (n > 2)
			ret.add(n);
		return ret;
	}

	private Map<Integer, Integer> toMap(Set<Integer> s) {
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		for (Integer i : s) {
			ret.put(i, 0);
		}
		return ret;

	}

	void solve() throws Exception {
		int n = nextInt();
		int m = nextInt();
		Map<Integer, Integer> copy = toMap(primeFactors(m));
		long ans = 0;
		String str = next();
		char[] a = str.toCharArray();
		for (int i = 0; i <= a.length - 1; i++) {
			Map<Integer, Integer> remains = new HashMap<Integer, Integer>();
			remains.putAll(copy);
			for (int j = i; j < a.length; j++) {
				boolean coprime = true;
				int inc = a[j] - '0';
				for (Entry<Integer, Integer> e : remains.entrySet()) {
					int v = (e.getValue()*10 + inc) % e.getKey();
					if (v == 0)
						coprime = false;
					e.setValue(v);
				}
				if (coprime){
					ans++;
					out.println(i+","+j+" "+str.substring(i,j+1));
				}
			}
		}

		out.println(ans);
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