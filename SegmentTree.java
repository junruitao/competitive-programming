public class RMQ {
	int st[];
	int N;

	// construct
	RMQ(int d[]) {
		int p = (int) (Math.ceil(Math.log(d.length) / Math.log(2)));
		N = (int) Math.pow(2, p);
		st = new int[2 * N];
		for (int i = 0; i < d.length; i++)
			set(i, d[i]);
	}

	void set(int pos, int x) {
		for (int i = pos + N; i > 0; i >>= 1)
			st[i] = Math.max(st[i], x);
	}

	// [L, R) i.e. L <= i < R
	int que(int L, int R) 
	{
		int res = 0;
		for (L += N, R += N; L < R; L >>= 1, R >>= 1) {
			if ((L & 1) != 0) {
				res = Math.max(res, st[L]);
				L++;
			}
			if ((R & 1) != 0) {
				R--;
				res = Math.max(res, st[R]);
			}
		}
		return res;
	}
};
