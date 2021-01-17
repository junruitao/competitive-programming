	// index 1 based
	int p[];
	int size[];

	private void connect(int u, int v) {
		int u0 = find(u);
		int v0 = find(v);
		if (u0 == v0)
			return;
		p[u0] = v0;
		size[v0] += size[u0];
	}

	private int find(int x) {
		if (p[x] == 0)
			return x;
		else
			return p[x] = find(p[x]);
	}