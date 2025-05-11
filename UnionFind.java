	// index 0 based
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
	        if (p[x] == x)
	            return x;
	        else
	            return p[x] = find(p[x]);
	    }
	
	    void init() {
	        p = new int[n];
	        size = new int[n];
	        Arrays.setAll(p, i -> p[i] = i);
	        Arrays.setAll(size, i -> 1);
	    }
