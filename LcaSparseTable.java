    static class LCA_SPARSH_TABLE {
        int[] parent;
        int[][] dp;
        private List<Integer>[] nodes;
        int[] level;

        private void dfs(int node, int p, int l) {
            level[node] = l;
            for (int i = 0; i < nodes[node].size(); i++) {
                if (nodes[node].get(i) == p) continue;
                parent[nodes[node].get(i)] = node;
                dfs(nodes[node].get(i), node, l + 1);
            }
        }

        public void solve(int testNumber, ScanReader in, PrintWriter out) {
            int n = in.scanInt();
            parent = new int[n + 1];
            nodes = new ArrayList[n + 1];
            level = new int[n + 1];
            for (int i = 0; i <= n; i++) nodes[i] = new ArrayList<>();
            parent[1] = 0;
            int max_k = (int) Math.ceil(Math.log(n) / Math.log(2)) + 1;
            dp = new int[n + 2][max_k + 1];
            for (int i = 2; i <= n; i++) {
                int u = in.scanInt();
                int v = in.scanInt();
                nodes[u].add(v);
                nodes[v].add(u);
            }
            dfs(1, -1, 1);
            for (int i = 0; i <= n; i++) dp[i][0] = parent[i];
            for (int j = 1; j <= max_k; j++)
                for (int i = 0; i <= n; i++)
                    dp[i][j] = dp[dp[i][j - 1]][j - 1];

            int q = in.scanInt();
            for (int i = 0; i < q; i++) {
                int r = in.scanInt();
                int u = in.scanInt();
                int v = in.scanInt();
                int q1 = findLCA(r, u, max_k);
                int q2 = findLCA(r, v, max_k);
                int q3 = findLCA(v, u, max_k);
                if (q1 == q2) out.println(q3);
                else if (q2 == q3) out.println(q1);
                else if (q1 == q3) out.println(q2);
                else throw new RuntimeException();
            }

        }

        private int findLCA(int u, int v, int max_k) {
            if (level[u] < level[v]) u = u + v - (v = u);
            for (int i = max_k; i >= 0; i--) {
                if (level[u] - (1 << i) >= level[v]) {
                    u = dp[u][i];
                }
            }
            if (u == v) return u;
            for (int i = max_k; i >= 0; i--) {
                if (dp[u][i] != 0 && dp[u][i] != dp[v][i]) {
                    u = dp[u][i];
                    v = dp[v][i];
                }
            }
            if (parent[u] == 0 || parent[u] != parent[v]) throw new RuntimeException();
            return parent[u];
        }

    }
