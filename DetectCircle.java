//	Recurrent case:
//
//	    color[currentNode] = 1;
//	    check all its neighbor nodes:
//	    if color[neighborNode] == 1, return true (cycle found)
//	    if color[neighborNode] == 0 and we find a circle, return false
//	    nothing wrong happends, color[currentNode] = 2;
//	    return false;

	boolean dfs(List<Integer>[] g, int cur, int[] colors) {
		if (g[cur] == null || g[cur].size() == 0) {
			return false;
		}

		colors[cur] = 1;
		for (int next : g[cur]) {
			if (colors[next] == 1)
				return true;
			if (colors[next] == 0 && dfs(g, next, colors))
				return true;
		}

		colors[cur] = 2;
		return false;
	}