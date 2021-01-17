import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
 
public class MyFirstJavaProgram {
 
	static BufferedReader in;
	static PrintWriter out;
	static StringTokenizer tok;
 
	long BIT[];
	
 
	void update(int ind, int vl) {
		for (int i = ind; i < BIT.length; i += (i & -i))
			BIT[i] += vl;
	}
 
	long query(int ind) {
		long ans = 0;
		for (int i = ind; i >= 1; i -= (i & -i)) {
			ans += BIT[i];
		}
		return ans;
	}
	// return ind where query(ind) = prefSum
 	int search(long prefSum) {

		int num = 0;
		long sum = 0;
		for (int i = 21; i >= 0; --i) {
			if ((num + (1 << i) < BIT.length) && (sum + BIT[num + (1 << i)] <= prefSum)) {
				num += (1 << i);
				sum += BIT[num];
			}
		}
		return num;
	}
	
	void solve() throws Exception {
		int[] a;
		 
		TreeMap<Integer, Integer> m;
		int n, k;
 
		int q = nextInt();
		while (q-- > 0) {
			n = nextInt();
			k = nextInt();
			a = new int[n];
			m = new TreeMap<Integer, Integer>();
 
			int i = 0;
			for (i = 0; i < n; i++) {
				a[i] = nextInt();
				m.put(a[i], null);
			}
			i = 0;
			for (Entry<Integer, Integer> e : m.entrySet()) {
				e.setValue(i++);
			}
			BIT = new long[m.size()+1];
			long ans = 0;
			long count = 0;
			for (i = 0; i < n; i++) {
				Entry<Integer, Integer> e = m.ceilingEntry(a[i] + k);
				long count1 =0, count2=0;
				int ind = 0;
//				ans += ans;
//				out.println(""+(a[i])+" ");
				if (e != null) {
					ind = e.getValue();
					count1 = query(m.size()) - query(ind);
					if (count1>0)
						count += (count1);
				}
//				out.print(">="+(a[i] + k)+": "+count1);
				
				e = m.floorEntry(a[i] - k);
				if (e != null) {
					ind = e.getValue();
					count2 = query(ind+1);
					if (count2>0)
						count += (count2);
				}
//				out.println("  <="+(a[i] - k)+": "+count2);
				ans += count;
//				out.println(count);
				update(m.get(a[i])+1,i+1);
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